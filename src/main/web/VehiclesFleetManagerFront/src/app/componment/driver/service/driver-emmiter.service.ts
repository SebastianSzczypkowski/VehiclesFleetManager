import { EventEmitter,Injectable } from '@angular/core';
import {Subscription} from "rxjs";
import {Driver} from "../../../model/driver";


@Injectable({
  providedIn: 'root'
})
export class DriverEmmiterService {

  driverDetails = new EventEmitter();
  sub!: Subscription;

  private driverId!:number;
  private driver!:Driver;

  getdriver(): Driver {
    return this.driver;
  }

  setdriver(value: Driver) {
    this.driver = value;
  }

  constructor() { }


  getdriverId(): number {
    return this.driverId;
  }

  setdriverId(value: number) {
    this.driverId = value;
  }

  onDriverDetailsEdit(driverId:number)
  {

    this.driverDetails.emit(driverId);
  }

}
