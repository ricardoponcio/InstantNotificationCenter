import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import localePt from '@angular/common/locales/pt';
import localeEn from '@angular/common/locales/en';
import localeEs from '@angular/common/locales/es';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

import { TranslateModule, TranslateLoader, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateCacheModule, TranslateCacheService, TranslateCacheSettings } from 'ngx-translate-cache';
import { HttpClient } from '@angular/common/http';

import { CollapseModule } from 'ngx-bootstrap/collapse';
import { ProgressbarModule } from 'ngx-bootstrap/progressbar';
import { JobViewComponent } from './job-view/job-view.component';
import { NgxBootstrapIconsModule, allIcons } from 'ngx-bootstrap-icons';
import { LoadingComponent } from './utils/loading/loading.component';
import { AuthGuardService } from './auth/auth-guard.service';
import { AuthInterceptor } from './auth/auth-interceptor';
import { LogoutComponent } from './logout/logout.component';
import { LoginComponent } from './login/login.component';
import { MomentPipe } from 'src/core/MomentPipe';
import { ReactiveFormsModule } from '@angular/forms';
import { ConvertDatePipe } from './utils/pipe/convert-date.pipe';
import { registerLocaleData } from '@angular/common';
import { LangUtils } from './utils/lang.utils';

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
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    TranslateCacheModule.forRoot({
      cacheService: {
        provide: TranslateCacheService,
        useFactory: TranslateCacheFactory,
        deps: [TranslateService, TranslateCacheSettings]
      }
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

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

export function TranslateCacheFactory(translateService: TranslateService, translateCacheSettings: TranslateCacheSettings) {
  return new TranslateCacheService(translateService, translateCacheSettings);
}