import {EventEmitter, Injectable } from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {Coordinates} from "../../../model/coordinates";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Driver} from "../../../model/driver";
import {Road} from "../../../model/road";

const AUTH_API = 'http://localhost:8080/api/road/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class RouteCreatorService {

  routeCreatorMap = new EventEmitter();
  routeCreatorMapRemove = new EventEmitter();
  subscription!: Subscription;
  subscriptionRemove!:Subscription;
  constructor(private http:HttpClient) { }

  routeCreatorShow(coordinates: Coordinates[])
  {
    this.routeCreatorMap.emit(coordinates);
  }

  routeCreatorRemove()
  {
    this.routeCreatorMapRemove.emit();
  }

  getAll()
  {
    return this.http.get<Road[]>(AUTH_API+'get-all',{

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

  add(road:any):Observable<any>
  {
    return this.http.post<any>(AUTH_API+'save',road,{

    })
  }


}
