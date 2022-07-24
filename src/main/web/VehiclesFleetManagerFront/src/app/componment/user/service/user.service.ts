import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const AUTH_API = 'http://localhost:8080/api/user/';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  getAllPage(page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'get-all-page',{
      params: {page,size}

    })
  }
  add(user:any):Observable<any>
  {
    return this.http.post(AUTH_API+'create-update-user',user,{

    })
  }
  delete(user:any):Observable<any>
  {
    return this.http.delete(AUTH_API+'deleted-user/'+user,{

    })
  }
  getAllPageSearch(search:string ,page:number,size:number)
  {
    return this.http.get<any>(AUTH_API+'search',{
      params: {search,page,size}

    })
  }
}
