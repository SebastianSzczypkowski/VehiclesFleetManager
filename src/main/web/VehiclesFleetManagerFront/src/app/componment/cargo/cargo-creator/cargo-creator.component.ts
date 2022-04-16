import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-cargo-creator',
  templateUrl: './cargo-creator.component.html',
  styleUrls: ['./cargo-creator.component.css']
})
export class CargoCreatorComponent implements OnInit {

  cargoForm!:FormGroup;
  constructor(private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.cargoForm=this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      description:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      sensitivity:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      specialRemarks:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      height:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      width:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      depth:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      weight:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    })
  }

    onSubmit() {

    }
}
