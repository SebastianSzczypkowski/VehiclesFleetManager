import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {NavbarService} from "./service/navbar.service";



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'VehiclesFleetManagerFront';

  constructor(public nav:NavbarService,
              private cdr:ChangeDetectorRef) {
  }
  ngOnInit(): void {
    this.cdr.detectChanges();
  }

}
