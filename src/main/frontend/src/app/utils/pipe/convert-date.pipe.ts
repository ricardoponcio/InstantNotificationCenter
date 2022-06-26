import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';
import { LangUtils } from '../lang.utils';

@Pipe({ name: 'convertDate', pure: false })
export class ConvertDatePipe implements PipeTransform {

    constructor(public lang: LangUtils, private _translateService: TranslateService) { }

    transform(value: any, pattern: string = 'mediumDate'): any {
        let resultDate = this.transformDate(value, pattern, this.lang.transformBrowserLang());
        this._translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            resultDate = this.transformDate(value, pattern, this.lang.transformBrowserLang());
        });
        return resultDate;
    }

    transformDate = (date: Date, pattern: string, lang: string): string | null => {
        const datePipe: DatePipe = new DatePipe(lang);
        return datePipe.transform(date, pattern);
    }

}
