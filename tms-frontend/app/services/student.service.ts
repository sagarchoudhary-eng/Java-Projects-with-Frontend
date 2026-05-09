import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { BACKEND_URL } from '../interfaces/constants';
import { Feedback } from '../interfaces/feedback.model';
import { OpenSnackbar } from '../store/app.actions';
import { AddFeedback } from '../store/tutor.action';

@Injectable({
  providedIn: 'root',
})
export class StudentService {
  subscribedTutors: any;
  pendingTutors: any;
  otherTutors: any;
  tutor: any;

  constructor(
    private _http: HttpClient,
    private router: Router,
    private store: Store
  ) {}
  getSubscribedTutors() {
    return this._http.get<any>(BACKEND_URL + '/student/subscribed-tutors');
  }
  getPendingTutors() {
    return this._http.get<any>(BACKEND_URL + '/student/pending-subscription');
  }
  getAllTutors() {
    return this._http.get<any>(BACKEND_URL + '/student/all-tutors');
  }
  subscribeTutor(id: number) {
    return this._http.post(BACKEND_URL + '/student/subscribe/' + id, {});
  }

  unsubscribeTutor(id: number) {
    return this._http.post(BACKEND_URL + '/student/unsubscribe/' + id, {});
  }

  withdrawSubscription(tutorId: number) {
    return this._http.post(BACKEND_URL + '/student/withdraw/' + tutorId, {});
  }

  createFeedback(
    feedback: { feedbackData: string; rating: number },
    tutorId: number
  ) {
    console.log(feedback);
    this._http
      .post<Feedback>(
        BACKEND_URL + '/student/saveFeedback/' + tutorId,
        feedback
      )
      .subscribe(
        (feedback) => {
          this.store.dispatch(new AddFeedback(feedback));
          this.store.dispatch(
            new OpenSnackbar('Feedback submitted successfully!')
          );
          this.router.navigate(['/student-dashboard']);
        },
        () => {}
      );
  }
}
