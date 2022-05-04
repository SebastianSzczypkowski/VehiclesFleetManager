import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Vehicle} from "../../../model/vehicle";
import {Observable} from "rxjs";


const AUTH_API = 'http://localhost:8080/api/vehicle/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class VehicleService {



  constructor(private http:HttpClient) { }

  getAllVehilce()
  {
    return this.http.get<Vehicle[]>(AUTH_API+'get-all',{

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

  // add(vehicle:any):Observable<any>
  // {
  //   return this.http.post<any>(AUTH_API+'save',{
  //     name:vehicle.name,
  //     vin:vehicle.vin,
  //     registration:vehicle.registration,
  //     mileage:vehicle.mileage,
  //     loadCapacity:vehicle.loadCapacity,
  //     engineCpacity:vehicle.engineCpacity
  //
  //   },httpOptions)
  // }
  add(vehicle:any):Observable<any>
  {
    return this.http.post(AUTH_API+'save',vehicle,{

    })
  }

}
