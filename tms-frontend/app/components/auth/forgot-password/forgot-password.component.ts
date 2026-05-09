import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss'],
})
export class ForgotPasswordComponent implements OnInit {
  questionList: { questionId: number; question: string }[] = [
    { questionId: 1, question: "What is your mother's name?" },
    { questionId: 2, question: 'What is your favourite sport ?' },
    { questionId: 3, question: 'What is your favourite dish ?' },
  ];
  constructor(private _authServ: AuthService) {}

  ngOnInit(): void {}
  onSubmit(form: NgForm) {
    if (form.invalid) return;
    this._authServ.forgotPassword(
      form.value.email,
      +form.value.questionId,
      form.value.answer
    );
  }
}
