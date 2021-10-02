import { Component, OnInit } from '@angular/core';
import { ProgressbarConfig } from 'ngx-bootstrap/progressbar';

import { Job } from '../dto/Job';
import { JobService } from '../services/job.service';

export function getProgressbarConfig(): ProgressbarConfig {
  return Object.assign(new ProgressbarConfig(), { animate: true, striped: true });
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass'],
  providers: [{ provide: ProgressbarConfig, useFactory: getProgressbarConfig }]
})
export class HomeComponent implements OnInit {

  jobs: Job[] = [];
  loading: boolean = false;

  constructor(private jobService: JobService) { }

  ngOnInit(): void {
    this.loading = true;
    this.jobService.listJobs()
      .then(jobs => {
        this.jobs = jobs;
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.loading = false;
      });
  }

  filterJobs = (finished: boolean): Job[] => {
    return this.jobs
      // fazr direto na api
      // .sort((a, b) => {
      //   if (!a || !a.startDate || !b || !b.startDate) return 0;
      //   return b.startDate.getTime() - a.startDate.getTime()
      // })
      .filter(job => job.finished == finished || (!finished && !job.finished));
  }

}
