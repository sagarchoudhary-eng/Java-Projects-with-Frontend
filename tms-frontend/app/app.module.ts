import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularMaterialModule } from './angular-material.module';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './components/navigation/header/header.component';
import { SidenavListComponent } from './components/navigation/sidenav-list/sidenav-list.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NotFoundComponent } from './components/shared/not-found/not-found.component';
import { ConfirmDialogComponent } from './components/shared/confirm-dialog/confirm-dialog.component';
import { ErrorComponent } from './components/shared/error-page/error.component';
import { ChartComponent } from './components/admin/chart/chart.component';
import { ChartsModule } from 'ng2-charts';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { UserListComponent } from './components/admin/user-list/user-list.component';
import { TutorDashboardComponent } from './components/tutor/tutor-dashboard/tutor-dashboard.component';
import { StudentListComponent } from './components/tutor/student-list/student-list.component';
import { FeedbackComponent } from './components/student/feedback/feedback.component';
import { FeedbackReportComponent } from './components/admin/feedback-report/feedback-report.component';
import { AuthInterceptor } from './components/shared/auth-interceptor';
import { StudentDashboardComponent } from './components/student/student-dashboard/student-dashboard.component';
import { MultiFileUploadComponent } from './components/tutor/multi-file-upload/multi-file-upload.component';
import { DownloadComponent } from './components/shared/download/download.component';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { ErrorInterceptor } from './components/shared/error-interceptor';
import { NetworkInterceptor } from './components/shared/network.interceptor';
import { CardComponent } from './components/shared/card/card.component';
import { HomePageComponent } from './components/shared/home-page/home-page.component';
import { SubscribedStudentsComponent } from './components/tutor/subscribed-students/subscribed-students.component';
import { GetFeedbackComponent } from './components/tutor/get-feedback/get-feedback.component';
import { NoOfTutorStudentReportComponent } from './components/admin/no-of-tutor-student-report/no-of-tutor-student-report.component';
import { TutorSearchPipe } from './pipes/tutor-search.pipe';
import { SearchTutorComponent } from './components/student/search-tutor/search-tutor.component';
import { PendingTutorComponent } from './components/student/pending-tutor/pending-tutor.component';
import { SubscribedTutorComponent } from './components/student/subscribed-tutor/subscribed-tutor.component';
import { AllTutorComponent } from './components/student/all-tutor/all-tutor.component';
import { NgxsReduxDevtoolsPluginModule } from '@ngxs/devtools-plugin';
import { NgxsModule } from '@ngxs/store';
import { environment } from 'src/environments/environment';
import { AppState } from './store/app.state';
import { StudentReportComponent } from './components/tutor/student-report/student-report.component';
import { AdminState } from './store/admin.state';
import { TutorState } from './store/tutor.state';
import { StarRatingComponent } from './components/student/star-rating/star-rating.component';
import { NgxStarRatingModule } from 'ngx-star-rating';
import { MiniCardComponent } from './components/shared/mini-card/mini-card.component';
import { AvgRatingReportComponent } from './components/admin/avg-rating-report/avg-rating-report.component';
import { FooterComponent } from './components/navigation/footer/footer.component';
import { SubTutorSearchPipe } from './pipes/sub-tutor-search.pipe';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidenavListComponent,
    ConfirmDialogComponent,
    NotFoundComponent,
    ChartComponent,
    AdminDashboardComponent,
    UserListComponent,
    TutorDashboardComponent,
    StudentListComponent,
    FeedbackComponent,
    FeedbackReportComponent,
    StudentDashboardComponent,
    MultiFileUploadComponent,
    DownloadComponent,
    ErrorComponent,
    CardComponent,
    HomePageComponent,
    SubscribedStudentsComponent,
    GetFeedbackComponent,
    NoOfTutorStudentReportComponent,
    TutorSearchPipe,
    SearchTutorComponent,
    PendingTutorComponent,
    SubscribedTutorComponent,
    AllTutorComponent,
    StudentReportComponent,
    StarRatingComponent,
    MiniCardComponent,
    AvgRatingReportComponent,
    FooterComponent,
    SubTutorSearchPipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FlexLayoutModule,
    AngularMaterialModule,
    ReactiveFormsModule,
    CommonModule,
    ChartsModule,
    MatCardModule,
    MatGridListModule,
    MatButtonModule,
    MatDialogModule,
    NgxStarRatingModule,
    NgxsModule.forRoot([AppState, AdminState, TutorState], {
      developmentMode: !environment.production,
    }),
    NgxsReduxDevtoolsPluginModule.forRoot({ disabled: environment.production }),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: NetworkInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
  entryComponents: [ErrorComponent, ConfirmDialogComponent],
})
export class AppModule {}
