import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";


const AUTH_API = 'http://localhost:8080/api/email';
const httpOptions={
  headers:new HttpHeaders({'Content-Type':'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'})
};
@Injectable({
  providedIn: 'root'
})
export class EmailSenderService {

  constructor(private http:HttpClient) { }

  send(email:any):Observable<any>
  {
    return this.http.post(AUTH_API+'save',email,{

    })
  }

  //TODO wysyłanie pliku
  sendWithAttachment(email:any,file:any):Observable<any>
  {
    return this.http.post(AUTH_API+'save',email,{

    })
  }
}
