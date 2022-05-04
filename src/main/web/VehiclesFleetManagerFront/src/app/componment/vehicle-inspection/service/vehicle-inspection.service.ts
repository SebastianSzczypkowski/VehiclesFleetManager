import { Injectable } from '@angular/core';
import {Vehicle} from "../../../model/vehicle";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Vehicleinspection} from "../../../model/vehicleinspection";

const AUTH_API = 'http://localhost:8080/api/vehicle-inspection/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class VehicleInspectionService {

  constructor(private http:HttpClient) { }
  getAll()
  {
    return this.http.get<Vehicleinspection[]>(AUTH_API+'get-all',{

    })
  }
  getAllPage(page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'get-all-page',{
      params: {page,size}

    })
  }
  getAllPageSearch(search:string ,page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'search',{
      params: {search,page,size}

    })
  }


  add(vehicle:any):Observable<any>
  {
    return this.http.post(AUTH_API+'save',vehicle,{

    })
  }
}
