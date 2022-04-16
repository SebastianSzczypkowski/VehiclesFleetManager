import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-vehicle',
  templateUrl: './create-vehicle.component.html',
  styleUrls: ['./create-vehicle.component.css']
})
export class CreateVehicleComponent implements OnInit {

  driverForm!:FormGroup;
  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.driverForm = this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      surname:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      pesel:new FormControl(''),
      dateofbirth:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      adress:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    });
  }

  onSubmit() {

  }
}
