import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IconName, IconNamesEnum } from 'ngx-bootstrap-icons';
import { ProgressbarType } from 'ngx-bootstrap/progressbar';
import { JobUpdate } from '../dto';
import { Job } from '../dto/Job';


@Component({
  selector: 'app-job-view',
  templateUrl: './job-view.component.html',
  styleUrls: ['./job-view.component.sass']
})
export class JobViewComponent implements OnInit {

  @Input() job !: Job;

  constructor(public translateService: TranslateService) { }

  ngOnInit(): void {
  }

  getSuffixState = () => {
    if (!this.job.finished) return 'secondary';
    if (this.job.status == 'SUCCESS') return 'success';
    if (this.job.status == 'ERROR') return 'danger';
    if (this.job.status == 'EXPIRED') return 'warning';
    return '';
  }

  getHex = (): string => {
    if (!this.job.finished) return 'inherit';
    if (this.job.status == 'SUCCESS') return '#198754';
    if (this.job.status == 'ERROR') return '#dc3545';
    if (this.job.status == 'EXPIRED') return '#ffc107';
    return '#6c757d';
  }

  getHexCard = (): string => {
    if (!this.job.finished) return 'inherit';
    if (this.job.status == 'SUCCESS') return '#d1e7dd';
    if (this.job.status == 'ERROR') return '#f8d7da';
    if (this.job.status == 'EXPIRED') return '#fff3cd';
    return '#e2e3e5';
  }

  getHexText = (): string => {
    if (!this.job.finished) return 'inherit';
    if (this.job.status == 'SUCCESS') return '#0f5132';
    if (this.job.status == 'ERROR') return '#842029';
    if (this.job.status == 'EXPIRED') return '#664d03';
    return '#41464b';
  }

  getInfoAlert = () => {
    if (!this.job.finished) return '';
    if (this.job.status == 'SUCCESS') return 'Job finished successfully!';
    if (this.job.status == 'ERROR') return 'Job finished with an error! ' + (this.job.resultMessage ? 'Detail: ' + this.job.resultMessage : '');
    if (this.job.status == 'EXPIRED') return 'Job has expired!';
    return 'Status not recognized!';
  }

  getIconState = (): IconName => {
    if (!this.job.finished) return IconNamesEnum.Clock;
    if (this.job.status == 'SUCCESS') return IconNamesEnum.CheckCircle;
    if (this.job.status == 'ERROR') return IconNamesEnum.XCircle;
    if (this.job.status == 'EXPIRED') return IconNamesEnum.ExclamationCircle;
    return IconNamesEnum.QuestionCircle;
  }

  getPercent = (): number => {
    return Math.max.apply(Math, this.job.updates.map(o => o.percent));
  }

  getUpdates = (maxUpdates?: number): JobUpdate[] => {
    return this.job.updates.filter(o => o.log)
      .sort((a, b) => {
        if (!a || !a.date || !b || !b.date) return 0;
        return new Date(b.date).getTime() - new Date(a.date).getTime()
      }).slice(0, maxUpdates);
  }

  hasUpdates = (): boolean => {
    // return this.job && this.job.updates && this.job.updates.filter(o => o.percent).length > 0;
    return this.job && this.job.updates && this.job.updates.length > 0;
  }

  progressBarType = (): ProgressbarType | undefined => {
    if (this.hasUpdates()) {
      return 'info';
    } else {
      return undefined;
    }
  }

}
