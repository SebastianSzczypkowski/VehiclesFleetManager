import { Injectable } from '@angular/core';
import {Cargo} from "../../../model/cargo";

@Injectable({
  providedIn: 'root'
})
export class CargoEmmiterService {

  private _cargo !:Cargo;
  constructor() { }


  getcargo(): Cargo {
    return this._cargo;
  }

  setcargo(value: Cargo) {
    this._cargo = value;
  }
}
