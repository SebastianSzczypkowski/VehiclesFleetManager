import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {VehicleEmmiterService} from "../service/vehicle-emmiter.service";
import {VehicleService} from "../service/vehicle.service";
import {Vehicle} from "../../../model/vehicle";

@Component({
  selector: 'app-vehicle-info',
  templateUrl: './vehicle-info.component.html',
  styleUrls: ['./vehicle-info.component.css']
})
export class VehicleInfoComponent implements OnInit {


  vehicleDeatils!:FormGroup;
  vehicleData!:Vehicle;
  constructor(private _formBuilder: FormBuilder,public dialogRef:MatDialogRef<VehicleInfoComponent>,
              private vehicleEmmiter:VehicleEmmiterService,private vehicleService:VehicleService) { }


  ngOnInit(): void {
    this.vehicleDeatils=this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      vin:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      registrationNumber:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      carMileage:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      id:new FormControl(''),
      carLoadCapacity:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      lorrySemitrailer:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      numberOfSeats:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      engineCapacity:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      averageFuelConsumption:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      // roadworthy:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      // occupied:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),


    })
    this.vehicleData=this.vehicleEmmiter.getvehilce();
    console.log(this.vehicleData);
    this.vehicleDeatils.patchValue({name:this.vehicleEmmiter.getvehilce().name});
    this.vehicleDeatils.patchValue({vin:this.vehicleEmmiter.getvehilce().vin});
    this.vehicleDeatils.patchValue({registrationNumber:this.vehicleEmmiter.getvehilce().registrationNumber});
    this.vehicleDeatils.patchValue({carMileage:this.vehicleEmmiter.getvehilce().carMileage});
    this.vehicleDeatils.patchValue({id:this.vehicleEmmiter.getvehilce().id});
    this.vehicleDeatils.patchValue({carLoadCapacity:this.vehicleEmmiter.getvehilce().carLoadCapacity});
    this.vehicleDeatils.patchValue({lorrySemitrailer:this.vehicleEmmiter.getvehilce().lorrySemitrailer});
    this.vehicleDeatils.patchValue({numberOfSeats:this.vehicleEmmiter.getvehilce().numberOfSeats});
    this.vehicleDeatils.patchValue({engineCapacity:this.vehicleEmmiter.getvehilce().engineCapacity});
    this.vehicleDeatils.patchValue({averageFuelConsumption:this.vehicleEmmiter.getvehilce().averageFuelConsumption});

  }

  closeModal() {
    this.dialogRef.close();
  }

  onSubmit() {
    this.vehicleService.add(this.vehicleDeatils.getRawValue()).subscribe();
  }


}
