import { Injectable } from '@angular/core';
import {Road} from "../../../model/road";

@Injectable({
  providedIn: 'root'
})
export class RoadEmmiterService {

  private _road!:Road;
  constructor() { }


  getroad(): Road {
    return this._road;
  }

  setroad(value: Road) {
    this._road = value;
  }
}
