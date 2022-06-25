import { registerLocaleData } from '@angular/common';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LOCALE_ID, NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { allIcons, NgxBootstrapIconsModule } from 'ngx-bootstrap-icons';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { MomentPipe } from 'src/core/MomentPipe';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthGuardService } from './auth/auth-guard.service';
import { AuthInterceptor } from './auth/auth-interceptor';
import { HomeComponent } from './home/home.component';
import { JobViewComponent } from './job-view/job-view.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { LangUtils } from './utils/lang.utils';
import { LoadingComponent } from './utils/loading/loading.component';
import { ConvertDatePipe } from './utils/pipe/convert-date.pipe';



// registerLocaleData(localePt);
// registerLocaleData(localeEn);
// registerLocaleData(localeEs);

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    JobViewComponent,
    LoadingComponent,
    LogoutComponent,
    LoginComponent,
    MomentPipe,
    ConvertDatePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CollapseModule.forRoot(),
    ProgressbarModule.forRoot(),
    NgxBootstrapIconsModule.pick(allIcons),
    HttpClientModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: (createTranslateLoader),
          deps: [HttpClient]
      },
      defaultLanguage: 'en'
    })
  ],
  providers: [
    AuthGuardService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }, {
      provide: LOCALE_ID,
      deps: [ LangUtils ],
      useFactory: (langUtils: LangUtils) => langUtils.pureLang()
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(private langUtils: LangUtils) {
    langUtils.langs()
      .forEach(lang => this.registerCulture(lang));
  }

  private registerCulture(culture: string) {
    if (!culture) {
      return;
    }
    const localeId = culture.substring(0, 2);
    this.localeInitializer(localeId);
  }

  localeInitializer(localeId: string): Promise<any> {
    return import(
      /* webpackInclude: /(nb|sv)\.js$/ */
      `@angular/common/locales/${localeId}.js`
      ).then(module => registerLocaleData(module.default));
  }

}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
