import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Tutor } from 'src/app/interfaces/tutor.model';
import { DialogService } from 'src/app/services/dialog.service';
import { StudentService } from 'src/app/services/student.service';

@Component({
  selector: 'app-subscribed-tutor',
  templateUrl: './subscribed-tutor.component.html',
  styleUrls: ['./subscribed-tutor.component.scss'],
})
export class SubscribedTutorComponent implements OnInit {
  constructor(
    private studentService: StudentService,
    private _router: Router,
    private dialogService: DialogService
  ) {}
  subscribedTutor = Array();

  ngOnInit(): void {
    this.studentService.getSubscribedTutors().subscribe((resp) => {
      this.subscribedTutor = resp.subscribedTutors;
    });
  }

  unsubscribeTutor(tutor: any) {
    this.dialogService
      .openConfirmDialog(
        'Are you sure you want to unsubscribe to ' +
          tutor.firstName +
          ' ' +
          tutor.lastName +
          "'s " +
          tutor.extraDetails.technology +
          ' course ?'
      )
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this.studentService.unsubscribeTutor(tutor.id).subscribe((resp) => {
            window.location.reload();
          });
        }
      });
  }

  giveFeedback(tutor: Tutor) {
    this.studentService.tutor = tutor;
    this._router.navigate(['/feedback']);
  }
}
