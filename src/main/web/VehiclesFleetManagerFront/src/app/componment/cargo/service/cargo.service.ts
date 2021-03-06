import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Driver} from "../../../model/driver";
import {Observable} from "rxjs";
import {Cargo} from "../../../model/cargo";

const AUTH_API = 'http://localhost:8080/api/cargo/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class CargoService {

  constructor(private http:HttpClient) { }

  getAll()
  {
    return this.http.get<Cargo[]>(AUTH_API+'get-all',{

    })
  }

  getAllPageSearch(search:string ,page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'search',{
      params: {search,page,size}

    })
  }
  getAllPage(page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'get-all-page',{
      params: {page,size}

    })
  }
  add(cargo:any):Observable<any>
  {
    return this.http.post(AUTH_API+'save',cargo,{

    })
  }
}
