import {EventEmitter, Injectable } from '@angular/core';

import {Subscription} from "rxjs";
import {User} from "../../../model/user";

@Injectable({
  providedIn: 'root'
})
export class UserEmmiterService {

  userDetails = new EventEmitter();
  sub!:Subscription;
  private _userID!:number;
  private _user!:User;
  constructor() { }


  getuserID(): number {
    return this._userID;
  }

  setuserID(value: number) {
    this._userID = value;
  }

  getuser(): User {
    return this._user;
  }

  setuser(value: User) {
    this._user = value;
  }

  onUserDetailsEdit(userID:number)
  {
    this.userDetails.emit(userID);
  }

}
