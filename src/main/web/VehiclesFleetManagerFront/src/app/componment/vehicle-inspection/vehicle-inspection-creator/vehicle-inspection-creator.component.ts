import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-vehicle-inspection-creator',
  templateUrl: './vehicle-inspection-creator.component.html',
  styleUrls: ['./vehicle-inspection-creator.component.css']
})
export class VehicleInspectionCreatorComponent implements OnInit {

  vehicleInspectionForm!:FormGroup;
  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.vehicleInspectionForm=this._formBuilder.group({
      carRepairShopName:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      dataWykonania:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      description:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    })
  }

  onSubmit() {

  }
}
