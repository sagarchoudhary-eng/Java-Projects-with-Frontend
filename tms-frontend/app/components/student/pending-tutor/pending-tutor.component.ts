import { Component, OnInit } from '@angular/core';
import { Tutor } from 'src/app/interfaces/tutor.model';
import { DialogService } from 'src/app/services/dialog.service';
import { StudentService } from 'src/app/services/student.service';

@Component({
  selector: 'app-pending-tutor',
  templateUrl: './pending-tutor.component.html',
  styleUrls: ['./pending-tutor.component.scss'],
})
export class PendingTutorComponent implements OnInit {
  constructor(
    private studentService: StudentService,
    private dialogService: DialogService
  ) {}

  pendingTutor: Tutor[] = Array();

  ngOnInit(): void {
    this.studentService.getPendingTutors().subscribe((resp) => {
      this.pendingTutor = resp.pendingTutors;
    });
  }

  withdrawSubscription(tutor: Tutor) {
    this.dialogService
      .openConfirmDialog(
        'Are you sure you want to withdraw subscription request to ' +
          tutor.firstName +
          ' ' +
          tutor.lastName +
          '?'
      )
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this.studentService
            .withdrawSubscription(tutor.id)
            .subscribe((resp) => {
              window.location.reload();
            });
        }
      });
  }
}
