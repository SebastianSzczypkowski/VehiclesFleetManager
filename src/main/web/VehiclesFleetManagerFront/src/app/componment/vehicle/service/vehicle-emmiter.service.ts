import { Injectable } from '@angular/core';
import {Vehicle} from "../../../model/vehicle";

@Injectable({
  providedIn: 'root'
})
export class VehicleEmmiterService {

  private _vehilce!:Vehicle;
  constructor() { }

  getvehilce(): Vehicle {
    return this._vehilce;
  }

  setvehilce(value: Vehicle) {
    this._vehilce = value;
  }
}
