import { Injectable } from '@angular/core';
import { Job } from '../dto';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  constructor(private httpService: HttpService) { }

  listJobs = (): Promise<Job[]> => {
    return this.httpService.post('/job/list');
  }

}
