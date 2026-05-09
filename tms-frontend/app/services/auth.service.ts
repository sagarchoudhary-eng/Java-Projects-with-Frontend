import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import {
  AuthLoginData,
  AuthLoginResponseData,
} from 'src/app/interfaces/auth-login-data.model';
import {
  BACKEND_URL,
  ROLE_ADMIN,
  ROLE_STUDENT,
  ROLE_TUTOR,
} from 'src/app/interfaces/constants';
import {
  StudentRegistration,
  TutorRegistration,
} from '../interfaces/auth-registration-data.model';
import { ClearAdminData } from '../store/admin.action';
import {
  OpenSnackbar,
  SetAuthenticated,
  SetEmail,
  SetUnauthenticated,
} from '../store/app.actions';
import { ClearTutorData, SetTutorId } from '../store/tutor.action';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private token!: string | null;
  private tokenTimer: any;

  constructor(
    private http: HttpClient,
    private router: Router,
    private store: Store
  ) {}

  getToken() {
    return this.token;
  }

  createStudent(authData: StudentRegistration) {
    this.http.post(BACKEND_URL + '/auth/register', authData).subscribe(
      () => {
        this.store.dispatch(
          new OpenSnackbar(
            'Sign up successfully, wait for administrator to accept!'
          )
        );
        this.router.navigate(['/']);
      },
      () => {}
    );
  }

  createTutor(authData: TutorRegistration) {
    this.http.post(BACKEND_URL + '/auth/register/tutor', authData).subscribe(
      () => {
        this.store.dispatch(
          new OpenSnackbar(
            'Sign up successfully, wait for administrator to accept!'
          )
        );
        this.router.navigate(['/']);
      },
      () => {}
    );
  }

  login(email: string, password: string) {
    const authData: AuthLoginData = {
      email: email,
      password: password,
    };

    this.http
      .post<AuthLoginResponseData>(BACKEND_URL + '/auth/login', authData)
      .subscribe((response) => {
        const token = response.token;
        this.token = token;
        if (this.token) {
          const expiresInDuration = response.expiresIn;
          this.setAuthTimer(expiresInDuration);
          const roleCheck = response.role;
          this.store.dispatch(new SetAuthenticated(roleCheck));
          if (roleCheck == ROLE_TUTOR) {
            localStorage.setItem('tutorId', response.userId.toString());
            this.store.dispatch(new SetTutorId(response.userId));
          }
          this.store.dispatch(new OpenSnackbar('Logged in successfully!'));
          const now = new Date();
          const expirationDate = new Date(
            now.getTime() + expiresInDuration * 1000
          );
          this.saveAuthData(token, expirationDate, roleCheck);
          this.navigate(roleCheck);
        }
      });
  }

  autoAuthUser() {
    const authInfo = this.getAuthData();
    if (!authInfo) {
      return;
    }
    const now = new Date();
    const expiresIn = authInfo.expirationDate.getTime() - now.getTime();
    if (expiresIn > 0) {
      this.token = authInfo.token;
      let roleCheck = authInfo.role;
      this.setAuthTimer(expiresIn / 1000);
      this.store.dispatch(new SetAuthenticated(roleCheck));
      if (roleCheck == ROLE_TUTOR) {
        let tutorId = localStorage.getItem('tutorId');
        if (tutorId) {
          this.store.dispatch(new SetTutorId(tutorId));
        }
      }
      this.navigate(roleCheck);
    }
  }

  logout() {
    this.token = null;
    this.store.dispatch(new SetUnauthenticated());
    this.store.dispatch(new OpenSnackbar('Logged out successfully!'));
    let role = this.getRole();
    if (role == 'admin') {
      this.store.dispatch(new ClearAdminData());
    } else if (role == 'tutor') {
      this.store.dispatch(new ClearTutorData());
    }
    this.router.navigate(['/']);
    this.clearAuthData();
    clearTimeout(this.tokenTimer);
  }

  getRole() {
    const role = localStorage.getItem('role');
    return role;
  }

  forgotPassword(email: string, questionId: number, answer: string) {
    this.http
      .post<boolean>(BACKEND_URL + '/auth/forgotPassword', {
        email: email,
        questionId: questionId,
        answer: answer,
      })
      .subscribe((data) => {
        if (data) {
          this.store.dispatch(new SetEmail(email));
          this.router.navigate(['/auth/updatePassword']);
        } else {
          this.store.dispatch(
            new OpenSnackbar('Your security question & answer are incorrect!')
          );
        }
      });
  }
  updatePassword(email: string, password: string) {
    const authData: AuthLoginData = {
      email: email,
      password: password,
    };

    this.http
      .post<boolean>(BACKEND_URL + '/auth/updatePassword', authData)
      .subscribe((data) => {
        if (data) {
          this.logout();
          this.store.dispatch(
            new OpenSnackbar('Password updated successfully!')
          );
          this.store.dispatch(new SetEmail(''));
          this.router.navigate(['/auth']);
        }
      });
  }

  private navigate(roleCheck: string) {
    if (roleCheck == ROLE_ADMIN) {
      this.router.navigate(['/admin-dashboard']);
    } else if (roleCheck == ROLE_TUTOR) {
      this.router.navigate(['/tutor-dashboard']);
    } else if (roleCheck == ROLE_STUDENT) {
      this.router.navigate(['/student-dashboard']);
    }
  }

  private setAuthTimer(duration: number) {
    this.tokenTimer = setTimeout(() => {
      this.logout();
    }, duration * 1000);
  }

  private saveAuthData(token: string, expirationDate: Date, role: string) {
    localStorage.setItem('token', token);
    localStorage.setItem('expDate', expirationDate.toISOString());
    localStorage.setItem('role', role);
  }

  private clearAuthData() {
    localStorage.removeItem('token');
    localStorage.removeItem('expDate');
    localStorage.removeItem('role');
    localStorage.removeItem('tutorId');
  }

  private getAuthData() {
    const token = localStorage.getItem('token');
    const expirationDate = localStorage.getItem('expDate');
    const role = localStorage.getItem('role');
    if (!token || !expirationDate || !role) {
      return;
    }
    return {
      token: token,
      expirationDate: new Date(expirationDate),
      role: role,
    };
  }
}
