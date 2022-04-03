import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Coordinates} from "../model/coordinates";
import {Observable} from "rxjs";

const AUTH_API = 'http://localhost:8080/map/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})

export class MapService {

  constructor(private http:HttpClient) { }

  getCoordinates(start:any,end:any,color:any,name:any)
  {
    return this.http.get<Coordinates[]>(AUTH_API+'cord',{
      params: {start,end,color,name}
    })

  }

  save(coordinates:any):Observable<any>
  {
    return this.http.post<any>(AUTH_API+'save',{
      lot:coordinates.lot,
      lat:coordinates.lat,
      details:coordinates.details,
    },httpOptions)
  }

  saveRoad(coordinates:any):Observable<any>
  {
    return this.http.post(AUTH_API+'save-road',coordinates, {

    })
  }

}
