import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private httpClient: HttpClient) { }

  post = (path: string, data ?: any): Promise<any> => {
    return this.httpClient.post<any>(environment.url + path, data).toPromise();
  }

}
