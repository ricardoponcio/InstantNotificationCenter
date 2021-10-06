import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ResponseEntity } from '../dto';

import { environment } from './../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private httpClient: HttpClient) { }

  post = (path: string, payload ?: any | undefined): Promise<any> => {
    return new Promise((resolve, reject) => {
      this.httpClient.post<any>(environment.url + path, payload)
        .toPromise().then((data: ResponseEntity<any>) => {
          if (data && data.status) {
            if(data.attach) {
              resolve(data.attach);
            } else {
              resolve(data.message);
            }
          }
          reject(data.message);
        }).catch((error) => {
          reject(error);
        });
    });
  }

}
