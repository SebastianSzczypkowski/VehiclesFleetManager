import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NavbarService} from "./service/navbar.service";
import {TokenService} from "./service/token.service";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import {HomeComponent} from "./componment/home/home.component";
import {User} from "./model/user";



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'VehiclesFleetManagerFront';

  user!:User;
  constructor(public nav:NavbarService,
              private cdr:ChangeDetectorRef,private tokenService :TokenService,
              private toaster:ToastrService,private router: Router) {
  }
  ngOnInit(): void {
    this.cdr.detectChanges();
    this.user=this.tokenService.getUser();
  }

  refreshUser()
  {
    this.user=this.tokenService.getUser();
  }
  logout()
  {
    this.tokenService.signOut();

    //TODO przeładuj na strone logowania
    this.toaster.success("Wylogowano");
    this.delay(1000);
    this.nav.hide();

    this.router.navigate(['']);
    //window.location.reload();

  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }
}
