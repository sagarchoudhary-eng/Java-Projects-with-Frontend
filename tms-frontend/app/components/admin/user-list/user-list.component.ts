import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { UserList } from 'src/app/interfaces/tables.model';
import { AdminService } from 'src/app/services/admin.service';
import { DialogService } from 'src/app/services/dialog.service';
import { AdminState } from 'src/app/store/admin.state';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
})
export class UserListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'fullName', 'status', 'role', 'actions'];
  dataSource: MatTableDataSource<UserList> = new MatTableDataSource();
  @Select(AdminState.getUsers)
  usersList$!: Observable<UserList[]>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private adminService: AdminService,
    private dialogService: DialogService
  ) {
    this.adminService.getAllUsers();
    this.usersList$.subscribe((users) => {
      this.dataSource.data = users;
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
  ngOnInit(): void {}

  onAccept(id: number) {
    this.dialogService
      .openConfirmDialog('Are you sure you want to Accept this user?')
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this.adminService.acceptUser(id);
        }
      });
  }

  onReject(id: number) {
    this.dialogService
      .openConfirmDialog('Are you sure you want to Reject this user?')
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this.adminService.rejectUser(id);
        }
      });
  }

  onDelete(id: number) {
    this.dialogService
      .openConfirmDialog('Are you sure you want to Delete this user?')
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this.adminService.deleteUser(id);
        }
      });
  }
}
