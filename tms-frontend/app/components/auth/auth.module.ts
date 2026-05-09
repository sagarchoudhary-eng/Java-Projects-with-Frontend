import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';
import { ValidateEqualModule } from 'ng-validate-equal';
import { AngularMaterialModule } from 'src/app/angular-material.module';
import { AuthCardComponent } from './auth-card/auth-card.component';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';

@NgModule({
  declarations: [AuthCardComponent, LoginComponent, SignupComponent, ForgotPasswordComponent, UpdatePasswordComponent],
  imports: [
    AngularMaterialModule,
    FormsModule,
    CommonModule,
    FlexLayoutModule,
    AuthRoutingModule,
    ValidateEqualModule,
  ],
})
export class AuthModule {}
