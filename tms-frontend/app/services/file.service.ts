import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BACKEND_URL } from '../interfaces/constants';
import { Store } from '@ngxs/store';
import { DeleteFile, OpenSnackbar, SetFiles } from '../store/app.actions';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  constructor(private http: HttpClient, private store: Store) {}

  uploadSingleFile(file: File): Observable<HttpEvent<{}>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    return this.http.post<any>(BACKEND_URL + '/uploadFile', formData, {
      reportProgress: true,
      observe: 'events',
    });
  }

  // Fetches the names of files to be displayed in the downloads list.
  fetchFileNames(id: number) {
    this.http
      .get<string[]>(BACKEND_URL + '/getFiles/' + id)
      .subscribe((filenames) => {
        this.store.dispatch(new SetFiles(filenames));
      });
  }

  downloadFile(fileName: string, tutorId: string | number): any {
    return this.http.get(
      BACKEND_URL + '/downloadFile/' + tutorId + '/' + fileName,
      {
        responseType: 'blob',
      }
    );
    // .subscribe((data) => {
    //   return data;
    // });
  }

  deleteFile(fileName: string) {
    this.http
      .post(BACKEND_URL + '/delete' + '/' + fileName, {})
      .subscribe((data) => {
        if (data) {
          this.store.dispatch(new DeleteFile(fileName));
          this.store.dispatch(new OpenSnackbar('File Deleted Successfully!'));
        }
      });
  }
}
