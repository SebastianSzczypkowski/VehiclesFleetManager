import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Vehicle} from "../../model/vehicle";
import {VehicleInspectionService} from "./service/vehicle-inspection.service";
import {Vehicleinspection} from "../../model/vehicleinspection";

@Component({
  selector: 'app-vehicle-inspection',
  templateUrl: './vehicle-inspection.component.html',
  styleUrls: ['./vehicle-inspection.component.css']
})
export class VehicleInspectionComponent implements OnInit {

  dataSource:Vehicleinspection[]=[];
  displayedColumns: string[] = ['position', 'nazwa','description','performedBy'];//, 'date', 'validityDate'
  constructor(private _formBuilder: FormBuilder,private vehicleInspectionService:VehicleInspectionService) { }

  ngOnInit(): void {

    this.vehicleInspectionService.getAll().subscribe(
      data=>{
        this.dataSource=data;
      })
  }

  onSubmit() {

  }
}
