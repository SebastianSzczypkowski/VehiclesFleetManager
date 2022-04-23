import { Component, OnInit } from '@angular/core';
import {Vehicle} from "../../model/vehicle";
import {DriverService} from "./service/driver.service";
import {Driver} from "../../model/driver";

@Component({
  selector: 'app-driver',
  templateUrl: './driver.component.html',
  styleUrls: ['./driver.component.css']
})
export class DriverComponent implements OnInit {

  dataSource:Driver[]=[];
  displayedColumns: string[] = ['position', 'name', 'surname', 'pesel','address'];
  constructor(private driverService:DriverService) { }

  ngOnInit(): void {
    this.driverService.getAll().subscribe(
      data=>{
        this.dataSource=data;
      })
  }

}
