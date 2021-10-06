import { Component } from '@angular/core';
import { NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router } from '@angular/router';
import { AuthService } from './auth/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { LangUtils } from './utils/lang.utils';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'frontend';

  public showOverlay = true;

  constructor(private router: Router,
    public translate: TranslateService,
    private langUtils: LangUtils,
    public authService: AuthService) {
    langUtils.initialize();

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

}
