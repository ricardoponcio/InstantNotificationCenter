import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

import { CollapseModule } from 'ngx-bootstrap/collapse';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { JobViewComponent } from './job-view/job-view.component';
import { NgxBootstrapIconsModule, allIcons } from 'ngx-bootstrap-icons';
import { LoadingComponent } from './utils/loading/loading.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    JobViewComponent,
    LoadingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CollapseModule.forRoot(),
    ProgressbarModule.forRoot(),
    NgxBootstrapIconsModule.pick(allIcons),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
