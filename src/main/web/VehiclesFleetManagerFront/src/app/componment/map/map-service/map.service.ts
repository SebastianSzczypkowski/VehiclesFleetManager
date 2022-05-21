import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  private _lat!:number;
  private _lon!:number;
  constructor() { }


  getlat(): number {
    return this._lat;
  }

  setlat(value: number) {
    this._lat = value;
  }

  getlon(): number {
    return this._lon;
  }

  setlon(value: number) {
    this._lon = value;
  }
}
