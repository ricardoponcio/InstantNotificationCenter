import { StoreUtils } from './utils/store.utils';
import { Component, OnInit } from '@angular/core';
import { NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router } from '@angular/router';
import { AuthService } from './auth/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { LangUtils } from './utils/lang.utils';
import { registerLocaleData } from '@angular/common';
import { locale } from 'moment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  selectedLanguage !: string;

  public showOverlay = true;

  constructor(private router: Router,
    public translate: TranslateService,
    private langUtils: LangUtils,
    private storeUtils: StoreUtils,
    public authService: AuthService) {
    langUtils.initialize();

    this.selectedLanguage = this.translate.currentLang;

    router.events.forEach((event: any) => {
      if (event instanceof NavigationStart) {
        this.showOverlay = true;
      }
      if (event instanceof NavigationEnd) {
        this.showOverlay = false;
      }

      // Set loading state to false in both of the below events to hide the spinner in case a request fails
      if (event instanceof NavigationCancel) {
        this.showOverlay = false;
      }
      if (event instanceof NavigationError) {
        this.showOverlay = false;
      }
    });
  }
  ngOnInit(): void {
    this.langUtils.langs().map(lang => this.langUtils.transformLang(lang)).forEach(lang => this.registerCulture(lang));
  }

  langFullName = (lang: string) => {
    if (lang == 'es')
      return 'Español';
    else if (lang == 'en')
      return 'English';
    else if (lang == 'pt')
      return 'Português (Brasil)'
    return lang;
  }

  isLoggedIn = () => {
    return this.authService.isLoggedIn();
  }

  private registerCulture(culture: string) {
    if (!culture) {
      return;
    }

    // Eg.: 'en-US', try full name first
    if (culture.length > 2)
      this.localeInitializer(culture);

    // Eg.: 'en-US', now try just with en
    const localeId = culture.substring(0, 2);
    this.localeInitializer(localeId);
  }

  private localeInitializer(localeId: string): Promise<any> {
    return import(`../../node_modules/@angular/common/locales/${localeId}.js`)
    .then(module => registerLocaleData(module.default)).catch(() => { /* ignore... */ });
  }

  changeLang = (lang: string) => {
    this.storeUtils.setLocal('current-lang', lang);
    this.translate.use(lang);
  }

}
