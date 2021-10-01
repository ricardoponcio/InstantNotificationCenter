import { Component, Input, OnInit } from '@angular/core';

import { Job } from '../dto/Job';

@Component({
  selector: 'app-job-view',
  templateUrl: './job-view.component.html',
  styleUrls: ['./job-view.component.sass']
})
export class JobViewComponent implements OnInit {

  @Input() job !: Job;

  constructor() { }

  ngOnInit(): void {
  }

}
