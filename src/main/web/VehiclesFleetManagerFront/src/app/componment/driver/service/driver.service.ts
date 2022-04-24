import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Vehicle} from "../../../model/vehicle";
import {Observable} from "rxjs";
import {Driver} from "../../../model/driver";

const AUTH_API = 'http://localhost:8080/api/driver/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class DriverService {

  constructor(private http:HttpClient) { }
  getAll()
  {
    return this.http.get<Driver[]>(AUTH_API+'get-all',{

    })
  }

  getAllPage(page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'get-all-page',{
      params: {page,size}

    })
  }
  add(driver:any):Observable<any>
  {
    return this.http.post(AUTH_API+'save',driver,{

    })
  }
}
