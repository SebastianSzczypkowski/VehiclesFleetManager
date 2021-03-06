import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {VehicleService} from "../service/vehicle.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-create-vehicle',
  templateUrl: './create-vehicle.component.html',
  styleUrls: ['./create-vehicle.component.css']
})
export class CreateVehicleComponent implements OnInit {

  driverForm!:FormGroup;
  constructor(private _formBuilder: FormBuilder,private vehicleService:VehicleService,
              private toaster:ToastrService) { }

  ngOnInit(): void {
    this.driverForm = this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      vin:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      registration:new FormControl(''),
      mileage:new FormControl('',[Validators.minLength(2),Validators.maxLength(45)]),
      loadCapacity:new FormControl('',[Validators.minLength(2),Validators.maxLength(45)]),
      engineCapacity:new FormControl('',[Validators.minLength(2),Validators.maxLength(45)]),
    });
  }

  onSubmit() {

    this.vehicleService.add(this.driverForm.getRawValue()).subscribe(
      data=>{
        this.toaster.success("Dodano pojazd");
      },
      err=>{
        this.toaster.error("Nie udało się dodać pojazdu");
      }

    )
  }

  reset() {

    this.driverForm.reset();
  }
}
