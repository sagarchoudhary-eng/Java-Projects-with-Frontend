import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngxs/store';
import { BACKEND_URL } from '../interfaces/constants';
import { information } from '../interfaces/information';
import { MiniCardData } from '../interfaces/minicard-data.model';
import { UserList } from '../interfaces/tables.model';
import { ChangeUserStatus, DeleteUser, LoadUsers } from '../store/admin.action';
import { OpenSnackbar } from '../store/app.actions';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient, private store: Store) {}

  getAllUsers() {
    this.http
      .get<UserList[]>(BACKEND_URL + '/admin/users')
      .subscribe((users) => {
        this.store.dispatch(new LoadUsers(users));
      });
  }

  // Methods for all the reports
  chartData() {
    return this.http.get<information[]>(BACKEND_URL + '/admin/reports');
  }

  feedbackReport() {
    return this.http.get<{ date: string; size: number }[]>(
      BACKEND_URL + '/admin/feedbackReport'
    );
  }

  totalTutor() {
    return this.http.get<{ tutor: number }>(BACKEND_URL + '/admin/tutorCount');
  }

  totalStudent() {
    return this.http.get<{ student: number }>(
      BACKEND_URL + '/admin/studentCount'
    );
  }

  getAllTutorAvgRating() {
    return this.http.get<{ rating: number; tutorName: string }[]>(
      BACKEND_URL + '/admin/avgRatingTutor'
    );
  }

  // methods for admin actions
  acceptUser(userId: number) {
    this.http
      .post<UserList>(BACKEND_URL + '/admin/accept/' + userId, {})
      .subscribe((user) => {
        this.store.dispatch(
          new OpenSnackbar('Accepted ' + user.fullName + ' into our system!')
        );
        this.store.dispatch(new ChangeUserStatus(user));
      });
  }

  rejectUser(userId: number) {
    this.http
      .post<UserList>(BACKEND_URL + '/admin/reject/' + userId, {})
      .subscribe((user) => {
        this.store.dispatch(
          new OpenSnackbar(
            'Rejected ' + user.fullName + ' access to our system!'
          )
        );
        this.store.dispatch(new ChangeUserStatus(user));
      });
  }

  deleteUser(userId: number) {
    this.http
      .post(BACKEND_URL + '/admin/delete/' + userId, {})
      .subscribe(() => {
        this.store.dispatch(new DeleteUser(userId));
      });
  }

  // methods for all the mini-card related data
  uploadCount(): MiniCardData {
    let uploadData: MiniCardData = {
      color: 'lightpink',
      icon: 'cloud_upload',
      title: 'Uploads',
      valueText: '',
    };
    this.http
      .get<number>(BACKEND_URL + '/admin/uploadReport')
      .subscribe((response) => {
        uploadData.valueText = response.toString();
      });
    return uploadData;
  }

  downloadCount(): MiniCardData {
    let downloadData: MiniCardData = {
      color: 'lightblue',
      icon: 'cloud_download',
      title: 'Downloads',
      valueText: '',
    };
    this.http
      .get<number>(BACKEND_URL + '/admin/downloadReport')
      .subscribe((response) => {
        downloadData.valueText = response.toString();
      });
    return downloadData;
  }

  getMaxRatedTutor(): MiniCardData {
    let maxRatingData: MiniCardData = {
      color: 'gold',
      icon: 'star',
      title: 'Highest Rated Tutor',
      valueText: '',
    };

    let maxRating: { tutorName: string; rating: number } = {
      tutorName: '',
      rating: 0,
    };

    this.getAllTutorAvgRating().subscribe((data) => {
      for (let i of data) {
        if (maxRating.rating < i.rating) {
          maxRating = i;
        }
      }
      maxRatingData.valueText =
        maxRating.tutorName + ': ' + maxRating.rating.toFixed(1);
    });
    return maxRatingData;
  }
  getActiveUser(): MiniCardData {
    let activeUserData: MiniCardData = {
      color: 'purple',
      icon: 'people',
      title: 'Active Users',
      valueText: '',
    };
    this.http
      .get<number>(BACKEND_URL + '/admin/numberOfUsers')
      .subscribe((response) => {
        activeUserData.valueText = response.toString();
      });
    return activeUserData;
  }
  getAllMiniCardData(): MiniCardData[] {
    let miniCardData: MiniCardData[] = [];
    miniCardData.push(this.uploadCount());
    miniCardData.push(this.downloadCount());
    miniCardData.push(this.getMaxRatedTutor());
    miniCardData.push(this.getActiveUser());
    return miniCardData;
  }
}
