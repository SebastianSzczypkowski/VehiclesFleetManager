import { Injectable } from '@angular/core';
import {Vehicleinspection} from "../../../model/vehicleinspection";

@Injectable({
  providedIn: 'root'
})
export class VehilceInspectionEmmiterService {

  private _vehicleInspection!:Vehicleinspection;
  constructor() { }


  getvehicleInspection(): Vehicleinspection {
    return this._vehicleInspection;
  }

  setvehicleInspection(value: Vehicleinspection) {
    this._vehicleInspection = value;
  }
}
