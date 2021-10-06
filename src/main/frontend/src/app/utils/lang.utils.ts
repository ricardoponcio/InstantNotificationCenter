import { Injectable } from "@angular/core";
import { TranslateService } from "@ngx-translate/core";

@Injectable({
    providedIn: 'root'
})
export class LangUtils {

    constructor(private translateService: TranslateService) { }

    transformLang = (): string => {
        const browserLang = this.pureLang();
        if (browserLang) {
            if (browserLang == 'en')
                return 'en-US';
            else if (browserLang == 'pt')
                return 'pt-BR';
        }
        return browserLang;
    }

    pureLang = () => {
        return this.translateService.currentLang || this.translateService.defaultLang;
    }

    initialize = () => {
        this.translateService.addLangs(['en', 'es', 'pt']);
        this.translateService.setDefaultLang('en');
        const browserLang = this.translateService.getBrowserLang();
        this.translateService.use(browserLang.match(/en|es|pt/) ? browserLang : 'en');
    }

    langs = (): string[] => {
        return this.translateService.langs;
    }

}