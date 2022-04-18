import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-driver-creator',
  templateUrl: './driver-creator.component.html',
  styleUrls: ['./driver-creator.component.css']
})
export class DriverCreatorComponent implements OnInit {

  driverForm!:FormGroup;
  permissionForm!:FormGroup;
  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.driverForm = this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      surname:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      pesel:new FormControl(''),
      dateofbirth:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      address:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    });
    this.permissionForm=this._formBuilder.group(
      {
        name: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(45)]),
        expiryDate:new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(45)]),
      }
    )
  }

  onSubmit() {

  }
}
