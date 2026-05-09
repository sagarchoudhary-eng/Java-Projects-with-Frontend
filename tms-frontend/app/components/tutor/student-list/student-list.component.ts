import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { StudentList } from 'src/app/interfaces/tables.model';
import { DialogService } from 'src/app/services/dialog.service';
import { TutorService } from 'src/app/services/tutor.service';
import { TutorState } from 'src/app/store/tutor.state';

@Component({
  selector: 'app-student-list',
  templateUrl: './student-list.component.html',
  styleUrls: ['./student-list.component.scss'],
})
export class StudentListComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'firstName',
    'lastName',
    'status',
    'actions',
  ];
  dataSource: MatTableDataSource<StudentList> = new MatTableDataSource();
  @Select(TutorState.pendingStudents)
  pendingStudents$!: Observable<StudentList[]>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private _tutorService: TutorService,
    private dialogService: DialogService
  ) {}

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

  rejectStudent(id: number) {
    this.dialogService
      .openConfirmDialog('Are you sure you want to reject this student?')
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this._tutorService.rejectStudent(id);
        }
      });
  }
  acceptStudent(id: number) {
    this.dialogService
      .openConfirmDialog('Are you sure you want to accept this student?')
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this._tutorService.acceptStudent(id);
        }
      });
  }
  ngOnInit(): void {
    this._tutorService.getAllPendingStudents();
    this.pendingStudents$.subscribe((students) => {
      this.dataSource.data = students;
    });
  }
}
