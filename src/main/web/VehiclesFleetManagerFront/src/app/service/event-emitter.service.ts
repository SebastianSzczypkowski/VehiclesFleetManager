import {EventEmitter, Injectable } from '@angular/core';
import {Subscription} from "rxjs";
import {Coordinates} from "../model/coordinates";

@Injectable({
  providedIn: 'root'
})
export class EventEmitterService {

  addRouteToMap = new EventEmitter();
  removeRouteFromMap = new EventEmitter();
  infoRoute = new EventEmitter();
  subsVar!: Subscription;
  subsVar2! :Subscription;
  subsRoadInfo!:Subscription;
  constructor() { }

  onAddRouteClick(coordinates: Coordinates[]){
    this.addRouteToMap.emit(coordinates);
  }

  onRemoveRouteClick()
  {
    this.removeRouteFromMap.emit();
  }

  onInfoRouteClick(coordinates: Coordinates)
  {
    this.infoRoute.emit(coordinates);
  }

}
