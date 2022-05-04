import { Injectable } from '@angular/core';
import {HttpHeaders} from "@angular/common/http";

const AUTH_API = 'http://localhost:8080/map/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class EntitlementsServiceService {

  constructor() { }
}
