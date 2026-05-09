import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { finalize } from 'rxjs/operators';
import { StartLoading, StopLoading } from 'src/app/store/app.actions';

@Injectable()
export class NetworkInterceptor implements HttpInterceptor {
  constructor(private store: Store) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    this.store.dispatch(new StartLoading());
    return next
      .handle(request)
      .pipe(finalize(() => this.store.dispatch(new StopLoading())));
  }
}
