import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Feedback } from 'src/app/interfaces/feedback.model';
import { TutorService } from 'src/app/services/tutor.service';
import { TutorState } from 'src/app/store/tutor.state';

@Component({
  selector: 'app-get-feedback',
  templateUrl: './get-feedback.component.html',
  styleUrls: ['./get-feedback.component.scss'],
})
export class GetFeedbackComponent implements OnInit {
  displayedColumns: string[] = [
    'feedbackData',
    'studentId',
    'feedbackDate',
    'rating',
  ];
  dataSource: MatTableDataSource<Feedback> = new MatTableDataSource();

  @Select(TutorState.getAllFeedback) feedbackList$!: Observable<Feedback[]>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private _tutorService: TutorService) {
    this._tutorService.getAllFeedbacks();
    this.feedbackList$.subscribe((feedbacks) => {
      this.dataSource.data = feedbacks;
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  counter(i: number) {
    return new Array(i);
  }

  ngOnInit(): void {}
}
