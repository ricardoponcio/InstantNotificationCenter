import { StoreUtils } from './store.utils';
import { Injectable } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})
export class LangUtils {

  constructor(private translateService: TranslateService,
    private storeUtils: StoreUtils) { }

  transformBrowserLang = (): string => {
    const browserLang = this.pureLang();
    return this.transformLang(browserLang);
  }

  transformLang = (lang: string): string => {
    if (lang == 'en')
      return 'en-US';
    else if (lang == 'pt')
      return 'pt-BR';
    return lang;
  }

  pureLang = () => {
    return this.translateService.currentLang || this.translateService.defaultLang;
  }

  initialize = () => {
    this.translateService.addLangs(['en', 'es', 'pt']);
    this.translateService.setDefaultLang('en');

    const browserLang = this.translateService.getBrowserLang();
    const storageLang = this.storeUtils.getLocal('current-lang');
    this.useLanguage(storageLang ? storageLang : browserLang);
  }

  private useLanguage = (language: string) => {
    this.translateService.use(language.match(/en|es|pt/) ? language : 'en');
  }

  langs = (): string[] => {
    return this.translateService.langs;
  }

}
