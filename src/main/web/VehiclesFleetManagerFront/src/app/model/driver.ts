import {Entitlementstotransport} from "./entitlementstotransport";

export class Driver {

  private _id!: number;
  private _name!:string;
  private _surname!:string;
  private _pesel!:string;
  private _address!:string;
  private _dateOfBirth!:string;



  get dateOfBirth(): string {
    return this._dateOfBirth;
  }

  set dateOfBirth(value: string) {
    this._dateOfBirth = value;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get surname(): string {
    return this._surname;
  }

  set surname(value: string) {
    this._surname = value;
  }

  get pesel(): string {
    return this._pesel;
  }

  set pesel(value: string) {
    this._pesel = value;
  }

  get address(): string {
    return this._address;
  }

  set address(value: string) {
    this._address = value;
  }
}
