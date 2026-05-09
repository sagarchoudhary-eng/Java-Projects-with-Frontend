import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Tutor } from 'src/app/interfaces/tutor.model';
import { DialogService } from 'src/app/services/dialog.service';
import { StudentService } from 'src/app/services/student.service';

@Component({
  selector: 'app-search-tutor',
  templateUrl: './search-tutor.component.html',
  styleUrls: ['./search-tutor.component.scss'],
})
export class SearchTutorComponent implements OnInit {
  constructor(
    private studentService: StudentService,
    private _router: Router,
    private dialogService: DialogService
  ) {}
  subscribedTutor = Array();
  pendingTutor: Tutor[] = Array();
  otherTutor: Tutor[] = Array();
  tutors: any;
  search = '';
  static searchflag = false;
  ngOnInit(): void {
    this.studentService.getAllTutors().subscribe((resp) => {
      this.subscribedTutor = resp.subscribedTutors;
      this.pendingTutor = resp.pendingTutors;
      this.otherTutor = resp.otherTutors;

      // this.studentService.subscribedTutors = resp.subscribedTutors;
      // this.studentService.pendingTutors = resp.pendingTutors;
      // this.studentService.otherTutors = resp.otherTutors;
    });
  }

  static flag(flag: boolean) {
    this.searchflag = flag;
  }

  getFlag() {
    return SearchTutorComponent.searchflag;
  }

  subscribeTutor(id: number) {
    this.studentService.subscribeTutor(id).subscribe((resp) => {
      this.ngOnInit();
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
            this.ngOnInit();
          });
        }
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
              this.ngOnInit();
            });
        }
      });
  }

  giveFeedback(tutor: Tutor) {
    this.studentService.tutor = tutor;
    this._router.navigate(['/feedback']);
  }
}
