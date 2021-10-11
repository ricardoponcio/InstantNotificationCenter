import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

import { AuthService } from './auth.service';
import * as moment from 'moment';
import { LangUtils } from '../utils/lang.utils';

@Injectable({
  providedIn: 'any'
})
export class AuthInterceptor implements HttpInterceptor {

  private iso8601 = /^\d{4}-\d\d-\d\dT\d\d:\d\d:\d\d(\.\d+)?(([+-]\d\d:\d\d)|Z)?$/;

  constructor(public lang: LangUtils,
    private authService: AuthService,
    private router: Router) { }

  intercept(req: HttpRequest<any>,
    next: HttpHandler): Observable<HttpEvent<any>> {
    var cloned = req.clone();

    cloned = cloned.clone({
      headers: req.headers.set("Accept-Language", this.lang.transformLang())
    });

    const idToken = this.authService.getToken();
    if (idToken) {
      cloned = cloned.clone({
        headers: req.headers.set("Authorization",
          "Bearer " + idToken)
      });
    }

    cloned = cloned.clone({
      body: this.convertFromDate(req.body)
    });

    return next.handle(cloned ? cloned : req).pipe(catchError((err) => this.handleError(err)));
  }

  private handleError(err: HttpErrorResponse): Observable<HttpEvent<any>> {
    let errorMsg;
    if (err.error instanceof Error) {
      errorMsg = `An error occurred: ${err.error.message}`;
    } else {
      errorMsg = `Backend returned code ${err.status}, body was: ` + JSON.stringify(err.error);
    }
    if (err.status === 401) {
      this.router.navigate(['/logout']);
    }
    return throwError(errorMsg);
  }

  convertFromDate(body: any) {
    if (body === null || body === undefined) {
      return body;
    }

    if (typeof body !== 'object') {
      return body;
    }

    for (const key of Object.keys(body)) {
      const value = body[key];
      if (value instanceof moment) {
        body[key] = moment(value).format('YYYY-MM-DD HH:mm:ss');
      } else if (typeof value === 'object') {
        this.convertToDate(value);
      }
    }
  }

  convertToDate(body: any) {
    if (body === null || body === undefined) {
      return body;
    }

    if (typeof body !== 'object') {
      return body;
    }

    for (const key of Object.keys(body)) {
      const value = body[key];
      if (this.isIso8601(value)) {
        body[key] = moment(value);
      } else if (typeof value === 'object') {
        this.convertToDate(value);
      }
    }
  }

  isIso8601(value: any) {
    if (value === null || value === undefined) {
      return false;
    }
    return this.iso8601.test(value);
  }

}
