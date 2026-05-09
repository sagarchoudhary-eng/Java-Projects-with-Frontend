import { Component, OnInit } from '@angular/core';
import { tap } from 'rxjs/operators';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { FileDetails } from 'src/app/interfaces/file.model';
import { FileService } from 'src/app/services/file.service';
import { StudentService } from 'src/app/services/student.service';
import { Store } from '@ngxs/store';
import { OpenSnackbar } from 'src/app/store/app.actions';

@Component({
  selector: 'app-multi-file-upload',
  templateUrl: './multi-file-upload.component.html',
  styleUrls: ['./multi-file-upload.component.scss'],
})
export class MultiFileUploadComponent implements OnInit {
  loaded = 0;
  selectedFiles!: FileList;
  uploadedFiles: FileDetails[] = [];
  showProgress = false;

  constructor(
    public _studentService: StudentService,
    private fileService: FileService,
    private store: Store
  ) {}

  ngOnInit(): void {}

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
  }

  upload(fileInput: HTMLInputElement) {
    this.showProgress = true;
    this.uploadedFiles = [];
    Array.from(this.selectedFiles).forEach((file) => {
      const fileDetails = new FileDetails();
      fileDetails.name = file.name;
      this.uploadedFiles.push(fileDetails);
      this.fileService
        .uploadSingleFile(file)
        .pipe(
          tap((event) => {
            if (event.type === HttpEventType.UploadProgress) {
              if (event.total) {
                const total: number = event.total;
                this.loaded = Math.round((100 * event.loaded) / total);
              }
              fileDetails.progress = this.loaded;
            }
          })
        )
        .subscribe(
          (event) => {
            if (event instanceof HttpResponse) {
              if (
                this.selectedFiles.item(this.selectedFiles.length - 1) === file
              ) {
                this.fileService.fetchFileNames(this._studentService.tutor.id);
              }
            }
          },
          (error) => {},
          () => {
            fileInput.value = '';
            this.store.dispatch(
              new OpenSnackbar('File uploaded Successfully!')
            );
          }
        );
    });
  }
}
