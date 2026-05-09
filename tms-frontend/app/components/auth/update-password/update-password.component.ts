import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { AppState } from 'src/app/store/app.state';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.scss'],
})
export class UpdatePasswordComponent implements OnInit {
  hide = true;
  hideConfirm = true;
  email!: string;

  @Select(AppState.getEmail)
  fpEmail$!: Observable<string>;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.fpEmail$.subscribe((email) => {
      this.email = email;
    });
  }

  updatePassword(form: NgForm) {
    this.authService.updatePassword(this.email, form.value.password);
  }
}
