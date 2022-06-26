import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class StoreUtils {

  constructor() { }

  // Local

  setLocal = (key: string, value: any) : void => {
    localStorage.setItem(key, JSON.stringify(value));
  }

  getLocal = (key: string) : any => {
    const value = localStorage.getItem(key);
    if (value) return JSON.parse(value);
    return null;
  }

}
