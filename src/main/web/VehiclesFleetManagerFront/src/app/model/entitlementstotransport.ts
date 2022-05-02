import {Typeofpermission} from "./typeofpermission";

export class Entitlementstotransport {
  private _id!:number;
  private _documentTyp!:string;
  private _typeofpermission!: string ;
  private _dateExpiry!:string;


  constructor() {
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get documentTyp(): string {
    return this._documentTyp;
  }

  set documentTyp(value: string) {
    this._documentTyp = value;
  }

  get typeofpermission(): string {
    return this._typeofpermission;
  }

  set typeofpermission(value: string) {
    this._typeofpermission = value;
  }

  get dateExpiry(): string {
    return this._dateExpiry;
  }

  set dateExpiry(value: string) {
    this._dateExpiry = value;
  }
}
