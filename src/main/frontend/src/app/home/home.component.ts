import { Component, OnInit } from '@angular/core';
import { ProgressbarConfig } from 'ngx-bootstrap/progressbar';

import { Job } from '../dto/Job';

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

  constructor() { }

  ngOnInit(): void {
    let job = new Job();
    job.name = 'Minecraft Backup';
    job.description = 'Minecraft daily backup 4am.';
    job.group = 'Backups';
    job.percent = 72;
    job.logs = [
      'Volume exists!',
      'Doing the backup...'
    ];
    this.jobs.push(job);

    job = new Job();
    job.name = 'Team Speak 3 Backup';
    job.description = 'Team Speak daily backup 4am.';
    job.group = 'Backups';
    job.finished = true;
    job.status = 'ERROR';
    this.jobs.push(job);

    job = new Job();
    job.name = 'Team Speak 4 Backup';
    job.description = 'Team Speak daily backup 4am.';
    job.group = 'Backups';
    job.finished = true;
    job.status = 'EXPIRED';
    this.jobs.push(job);

    job = new Job();
    job.name = 'Team Speak 5 Backup';
    job.description = 'Team Speak daily backup 4am.';
    job.group = 'Backups';
    job.finished = true;
    job.status = 'SUCCESS';
    this.jobs.push(job);
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
