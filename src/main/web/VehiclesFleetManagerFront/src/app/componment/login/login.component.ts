import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {Subject} from "rxjs";
import {AuthService} from "../../service/auth.service";
import {TokenService} from "../../service/token.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {AppComponent} from "../../app.component";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginValid = true;
  public username = '';
  public password = '';

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  loginForm!: FormGroup;
  private _destroySub$ = new Subject<void>();
  private readonly returnUrl: string;
  hide: boolean =true;
  constructor(private _route: ActivatedRoute,
              private _router: Router,private authService:AuthService,private token:TokenService,
              private nav:NavbarService,private formBuilder:FormBuilder,private toaster:ToastrService,
              )
  {
    this.returnUrl = this._route.snapshot.queryParams['returnUrl'] || '/home';
  }

  ngOnInit(): void {
    this.nav.hide();
    this.loginForm=this.formBuilder.group(
      {
        username: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]),
        password: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(45),
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')]),
      }

    );
    if(this.token.getToken()){
      this.isLoggedIn=true;
    }
  }
  public ngOnDestroy(): void {
    this._destroySub$.next();
  }

  public onSubmit(): void {
    this.loginValid = true;
    this.authService.login(this.loginForm.getRawValue()).subscribe(
      data => {
        this.token.saveToken(data.accessToken);
        this.token.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;


        //this.reloadPage();
        this.toaster.success("Zalogowano");
        this.nav.show();


      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
        this.toaster.error("Wystąpił błąd podczas logowania");
      }
    );
  }
  reloadPage(): void {

    window.location.reload();
  }


}
