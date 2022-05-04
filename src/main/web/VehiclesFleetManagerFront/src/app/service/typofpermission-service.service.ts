import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Coordinates} from "../model/coordinates";
import {Typeofpermission} from "../model/typeofpermission";

const AUTH_API = 'http://localhost:8080/api/typ-of-permissions/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class TypofpermissionServiceService {

  constructor(private http:HttpClient) { }
  getAll()
  {
    return this.http.get<Typeofpermission[]>(AUTH_API+'get-all',{

    })

  }
  getAllPage(page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'get-all-page',{
      params: {page,size}

    })
  }
}
