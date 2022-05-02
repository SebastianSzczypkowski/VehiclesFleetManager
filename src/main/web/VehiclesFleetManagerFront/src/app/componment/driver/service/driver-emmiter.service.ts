import { EventEmitter,Injectable } from '@angular/core';
import {Subscription} from "rxjs";
import {Driver} from "../../../model/driver";


@Injectable({
  providedIn: 'root'
})
export class DriverEmmiterService {

  driverDetails = new EventEmitter();
  sub!: Subscription;

  constructor() { }

  onDriverDetailsEdit(driver:Driver)
  {
    console.log(driver);
    this.driverDetails.emit(driver);
  }

}
