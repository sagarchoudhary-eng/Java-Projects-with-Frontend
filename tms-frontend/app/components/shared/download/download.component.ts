import { Component, OnInit, ViewChild } from '@angular/core';

import { FileService } from 'src/app/services/file.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { DialogService } from 'src/app/services/dialog.service';
import { Select } from '@ngxs/store';
import { AppState } from 'src/app/store/app.state';
import { Observable } from 'rxjs';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-download',
  templateUrl: './download.component.html',
  styleUrls: ['./download.component.scss'],
})
export class DownloadComponent implements OnInit {
  constructor(
    private fileService: FileService,
    private route: ActivatedRoute,
    private dialogService: DialogService
  ) {
    this.tutorId = Number(this.route.snapshot.paramMap.get('tutorId'));
    this.fileService.fetchFileNames(this.tutorId);
    this.fileList$.subscribe((filenames) => {
      this.dataSource.data = filenames;
    });
  }

  tutorId!: number;
  @Select(AppState.getFiles)
  fileList$!: Observable<string[]>;

  @Select(AppState.getRole)
  role$!: Observable<string>;

  ngOnInit(): void {}

  fname!: any;
  ext!: string;
  downloadFile(fileName: string) {
    const parts: string[] = fileName.split('.');
    for (var i = 0; i < 1; i++) {
      this.fname = parts[i];
      this.ext = parts[i + 1];
    }
    this.fileService
      .downloadFile(fileName, this.tutorId)
      .subscribe((response: any) => {
        let blob: any = new Blob([response], {
          type: 'text/json; charset=utf-8',
        });
        fileSaver.saveAs(blob, this.fname + '.' + this.ext);
      });
  }

  deleteFile(fileName: string) {
    this.dialogService
      .openConfirmDialog('Are you sure you want to DELETE this file?')
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          this.fileService.deleteFile(fileName);
        }
      });
  }

  displayedColumns: string[] = ['fileName', 'actions'];
  dataSource: MatTableDataSource<string> = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
