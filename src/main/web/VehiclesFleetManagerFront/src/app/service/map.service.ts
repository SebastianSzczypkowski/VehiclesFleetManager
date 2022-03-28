import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Coordinates} from "../model/coordinates";

const AUTH_API = 'http://localhost:8080/map/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json'})
};
@Injectable({
  providedIn: 'root'
})

export class MapService {

  constructor(private http:HttpClient) { }

  getCoordinates(start:any,end:any)
  {
    return this.http.get<Coordinates[]>(AUTH_API+'cord',{
      params: {start,end}
    })

  }
}
