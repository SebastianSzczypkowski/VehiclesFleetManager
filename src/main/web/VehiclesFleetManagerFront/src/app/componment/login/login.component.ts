import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {Subject} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {TokenService} from "../../service/token.service";
import {ActivatedRoute, Router} from "@angular/router";


declare function login():any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginValid = true;
  public username = '';
  public password = '';
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  private _destroySub$ = new Subject<void>();
  private readonly returnUrl: string;
  hide: boolean =true;
  constructor(private _route: ActivatedRoute,
              private _router: Router,private authService:AuthService,private token:TokenService,private nav:NavbarService)
  {
    this.returnUrl = this._route.snapshot.queryParams['returnUrl'] || '/home';
  }

  ngOnInit(): void {
    //this.nav.show();
    if(this.token.getToken()){
      this.isLoggedIn=true;
    }
  }
  public ngOnDestroy(): void {
    this._destroySub$.next();
  }

  public onSubmit(): void {
    this.loginValid = true;
    this.authService.login(this.form).subscribe(
      data => {
        this.token.saveToken(data.accessToken);
        this.token.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }
  reloadPage(): void {
    window.location.reload();
  }

  switch() {
    login();
  }
}
