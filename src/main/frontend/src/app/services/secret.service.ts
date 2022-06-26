import { Secrets } from './../dto/Secrets';
import { Injectable } from '@angular/core';
import { Job } from '../dto';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class SecretService {

  constructor(private httpService: HttpService) { }

  createSecret = (name: string, description: string): Promise<Secrets> => {
    return this.httpService.post('/secrets/create', {
      name: name,
      description: description
    });
  }

  listSecrets = (): Promise<Secrets[]> => {
    return this.httpService.get('/secrets/list');
  }

  deleteSecrets = (secret: Secrets): Promise<any> => {
    return this.httpService.delete('/secrets/delete/' + secret.id);
  }

}
