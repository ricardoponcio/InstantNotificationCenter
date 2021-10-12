import { Component, OnInit } from '@angular/core';
import { ProgressbarConfig, ProgressbarType } from 'ngx-bootstrap/progressbar';
import { AuthService } from '../auth/auth.service';

import { Job } from '../dto/Job';
import { JobService } from '../services/job.service';
import { WebSocketUtils } from '../utils/websocket.utils';

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

  webSocketUtils !: WebSocketUtils;

  constructor(private jobService: JobService,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.loading = true;
    this.jobService.listJobs()
      .then(jobs => {
        this.jobs = jobs.map(job => {
          job.collapseDetail = true;
          return job;
        });
        console.log(this.jobs);
        this.loading = false;
      }).catch(err => {
        console.log(err);
        this.loading = false;
      });
    this.webSocketUtils = new WebSocketUtils(
      '/socket', '/job/update',
      this.onMessage.bind(this),
      {
        'userToken': this.authService.getToken(),
        'stompToken': '1'
      }
    );
  }

  filterJobs = (finished: boolean): Job[] => {
    return this.jobs
      .sort((a, b) => {
        if (!a || !a.startDate || !b || !b.startDate) return 0;
        return new Date(b.startDate).getTime() - new Date(a.startDate).getTime()
      })
      .filter(job => job.finished == finished || (!finished && !job.finished));
  }

  onMessage(message: any): void {
    const jobMessage: Job = message as Job;
    if (jobMessage && jobMessage.id) {
      jobMessage.collapseDetail = true;
      var index = this.jobs.map(job => job.id).indexOf(jobMessage.id);
      if (index != -1) {
        if (jobMessage.status != this.jobs[index].status) {
          jobMessage.collapseDetail = true;  
        } else {
          jobMessage.collapseDetail = this.jobs[index].collapseDetail;
        }
        this.jobs[index] = jobMessage;
      } else {
        this.jobs.push(jobMessage);
      }
    }
  }

}
