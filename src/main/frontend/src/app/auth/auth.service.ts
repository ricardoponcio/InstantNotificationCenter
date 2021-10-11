import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { AuthDTO } from '../dto';
import { HttpService } from '../services/http.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(public httpService: HttpService) { }

  login = (email: string, password: string) => {
    return new Promise<any>((resolve, reject) => {
        this.httpService.post('/user/login', 
            { 'username': email, 'password': password })
          .then((data: AuthDTO) => {
            this.#setSession(data);
            resolve(data);
          }).catch((error) => {
            reject(error);
          });
    });
  }

  logout = () => {
    localStorage.removeItem("id_token");
    localStorage.removeItem("expires_at");
  }

  isLoggedIn = () => {
    const expiration = localStorage.getItem("expires_at") || '';
    if (!expiration)
      return false;
    const expiresAt = JSON.parse(expiration);
    return moment().isBefore(moment(expiresAt));
  }

  isLoggedOut = () => {
    return !this.isLoggedIn();
  }

  #setSession = (authResult: AuthDTO) => {
    const expiresAt = moment().add(authResult.expires_in, 'second');
    localStorage.setItem('id_token', authResult.token);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()));
  }

  getToken = () => {
    return localStorage.getItem("id_token");
  }

}
