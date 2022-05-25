import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenService } from "./token.service";


const TOKEN_HEADER_KEY='Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor{
    constructor(private token:TokenService){}

    intercept(req:HttpRequest<any>, next: HttpHandler):Observable<HttpEvent<any>>{
        let authReq = req;
        const token = this.token.getToken();
        if(token!=null){
            authReq=req.clone({headers:req.headers.set(TOKEN_HEADER_KEY,'Bearer '+token)});

        }
        return next.handle(authReq);
    }
}

export const authInterceptorProviders=[
    {provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true}
];