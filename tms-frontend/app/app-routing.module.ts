import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { HomePageComponent } from './components/shared/home-page/home-page.component';
import { AuthGuard } from './components/shared/auth.guard';
import { NotFoundComponent } from './components/shared/not-found/not-found.component';
import { SearchTutorComponent } from './components/student/search-tutor/search-tutor.component';
import { StudentDashboardComponent } from './components/student/student-dashboard/student-dashboard.component';
import { DownloadComponent } from './components/shared/download/download.component';
import { FeedbackComponent } from './components/student/feedback/feedback.component';
import { TutorDashboardComponent } from './components/tutor/tutor-dashboard/tutor-dashboard.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'tutor-dashboard',
    component: TutorDashboardComponent,
  },
  {
    path: 'student-dashboard',
    component: StudentDashboardComponent,
    canActivate: [AuthGuard],
  },
  { path: 'download-files/:tutorId', component: DownloadComponent },
  { path: 'feedback', component: FeedbackComponent },
  { path: 'search-tutor', component: SearchTutorComponent },
  {
    path: 'auth',
    loadChildren: () =>
      import('./components/auth/auth.module').then((m) => m.AuthModule),
  },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '/404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [AuthGuard],
})
export class AppRoutingModule {}
