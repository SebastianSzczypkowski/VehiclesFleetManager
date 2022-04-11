import {EventEmitter, Injectable } from '@angular/core';
import {Subscription} from "rxjs";
import {Coordinates} from "../../../model/coordinates";

@Injectable({
  providedIn: 'root'
})
export class RouteCreatorService {

  routeCreatorMap = new EventEmitter();
  routeCreatorMapRemove = new EventEmitter();
  subscription!: Subscription;
  subscriptionRemove!:Subscription;
  constructor() { }

  routeCreatorShow(coordinates: Coordinates[])
  {
    this.routeCreatorMap.emit(coordinates);
  }

  routeCreatorRemove()
  {
    this.routeCreatorMapRemove.emit();
  }


}
