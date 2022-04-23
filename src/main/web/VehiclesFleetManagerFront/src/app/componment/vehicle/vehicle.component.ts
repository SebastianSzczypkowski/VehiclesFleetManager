import { Component, OnInit } from '@angular/core';
import {VehicleService} from "./service/vehicle.service";
import {Vehicle} from "../../model/vehicle";

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css']
})
export class VehicleComponent implements OnInit {

  dataSource:Vehicle[]=[];
  displayedColumns: string[] = ['position', 'nazwa', 'vin', 'rejestracja'];
  constructor(private vehicleService:VehicleService) { }


  ngOnInit(): void {

    this.vehicleService.getAllVehilce().subscribe(
        data=>{
          this.dataSource=data;
    }

    )
  }

}
