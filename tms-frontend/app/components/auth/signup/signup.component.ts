import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import {
  StudentRegistration,
  TutorRegistration,
} from 'src/app/interfaces/auth-registration-data.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-auth-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  questionList: { questionId: number; question: string }[] = [
    { questionId: 1, question: "What is your mother's name?" },
    { questionId: 2, question: 'What is your favourite sport ?' },
    { questionId: 3, question: 'What is your favourite dish ?' },
  ];
  constructor(public authService: AuthService, private router: Router) {}
  hide = true;
  hideConfirm = true;
  ngOnInit() {}

  onSignup(form: NgForm) {
    if (form.invalid) {
      return;
    }
    if (form.value.userRole == 'student') {
      const studentData: StudentRegistration = {
        firstName: form.value.firstName,
        lastName: form.value.lastName,
        email: form.value.email,
        mobileNo: form.value.mobileNo,
        password: form.value.password,
        questionId:form.value.questionId,
        answer:form.value.answer,
      };
      this.authService.createStudent(studentData);
    } else if (form.value.userRole == 'tutor') {
      const tutorData: TutorRegistration = {
        firstName: form.value.firstName,
        lastName: form.value.lastName,
        email: form.value.email,
        mobileNo: form.value.mobileNo,
        password: form.value.password,
        technology: form.value.technology,
        about: form.value.about,
        questionId:form.value.questionId,
        answer:form.value.answer,
      };
      this.authService.createTutor(tutorData);
    }
  }
}
