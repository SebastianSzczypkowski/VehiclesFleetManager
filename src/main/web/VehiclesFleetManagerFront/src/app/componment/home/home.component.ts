import { Component, OnInit } from '@angular/core';
import {NavbarService} from "../../service/navbar.service";
import {TokenService} from "../../service/token.service";
import {User} from "../../model/user";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(public nav : NavbarService,private tokenService :TokenService) { }

  user!:User;
  ngOnInit(): void {
    this.nav.hide();
    this.user= this.tokenService.getUser();

  }

  show() {
    this.nav.show();
  }
}
