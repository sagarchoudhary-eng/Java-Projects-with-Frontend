import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngxs/store';
import { BACKEND_URL } from '../interfaces/constants';
import { Feedback } from '../interfaces/feedback.model';
import { StudentList } from '../interfaces/tables.model';
import {
  AcceptStudent,
  LoadFeedback,
  LoadPendingStudents,
  LoadSubscribedStudents,
  RejectStudent,
} from '../store/tutor.action';

@Injectable({
  providedIn: 'root',
})
export class TutorService {
  constructor(private http: HttpClient, private store: Store) {}

  getAllPendingStudents() {
    this.http
      .get<StudentList[]>(BACKEND_URL + '/tutor/pendingStudents')
      .subscribe((students) => {
        this.store.dispatch(new LoadPendingStudents(students));
      });
  }

  getAllSubscribedStudents() {
    this.http
      .get<StudentList[]>(BACKEND_URL + '/tutor/subscribedStudents')
      .subscribe((students) => {
        this.store.dispatch(new LoadSubscribedStudents(students));
      });
  }

  rejectStudent(id: number) {
    this.http
      .post<StudentList>(BACKEND_URL + '/tutor/reject/' + id, {})
      .subscribe((student) => {
        this.store.dispatch(new RejectStudent(student));
      });
  }

  acceptStudent(id: number) {
    this.http
      .post<StudentList>(BACKEND_URL + '/tutor/accept/' + id, {})
      .subscribe((student) => {
        this.store.dispatch(new AcceptStudent(student));
      });
  }

  studentReport() {
    return this.http.get<any[]>(BACKEND_URL + '/tutor/studentReport');
  }

  getAllFeedbacks() {
    this.http
      .get<Feedback[]>(BACKEND_URL + '/tutor/getFeedback')
      .subscribe((feedbacks) => {
        this.store.dispatch(new LoadFeedback(feedbacks));
      });
  }

  downloadCount() {
    return this.http.get<number>(BACKEND_URL + '/tutor/downloadReport');
  }

  getAvgRating() {
    return this.http.get<{ body: number }>(BACKEND_URL + '/tutor/ratingReport');
  }
}
