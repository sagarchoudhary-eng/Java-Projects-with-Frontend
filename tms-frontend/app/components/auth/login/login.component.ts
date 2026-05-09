import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Store } from '@ngxs/store';
import { OpenSnackbar } from 'src/app/store/app.actions';
import { AuthService } from '../../../services/auth.service';

declare const generateCaptcha: any;
declare const CheckValidCaptcha: any;

@Component({
  selector: 'app-auth-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  constructor(public authService: AuthService, private store: Store) {}
  hide = true;
  ngAfterViewInit(): void {
    generateCaptcha();
  }
  ngOnInit() {}

  onLogin(form: NgForm) {
    if (form.invalid) {
      return;
    }
    if (CheckValidCaptcha()) {
      this.authService.login(form.value.email, form.value.password);
    } else {
      this.store.dispatch(
        new OpenSnackbar('Please enter correct captcha code')
      );
    }
  }
}
