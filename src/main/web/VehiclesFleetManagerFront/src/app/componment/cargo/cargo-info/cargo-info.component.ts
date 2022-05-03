import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {DriverInfoComponent} from "../../driver/driver-info/driver-info.component";
import {CargoEmmiterService} from "../service/cargo-emmiter.service";
import {CargoService} from "../service/cargo.service";
import {Driver} from "../../../model/driver";
import {Cargo} from "../../../model/cargo";

@Component({
  selector: 'app-cargo-info',
  templateUrl: './cargo-info.component.html',
  styleUrls: ['./cargo-info.component.css']
})
export class CargoInfoComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,public dialogRef:MatDialogRef<DriverInfoComponent>,
              private cargoEmmiter:CargoEmmiterService,private cargoService:CargoService) { }

  cargoData!: Cargo;
  cargoDeatils!:FormGroup;
  ngOnInit(): void {
    this.cargoDeatils=this.formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      description:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      type:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      sensitivity:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      id:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      specialRemarks:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      delivered:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      assigned:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      weight:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      width:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      height:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      depth:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),

    })

    this.cargoData=this.cargoEmmiter.getcargo();
    this.cargoDeatils.patchValue({name:this.cargoEmmiter.getcargo().name});
    this.cargoDeatils.patchValue({description:this.cargoEmmiter.getcargo().description});
    this.cargoDeatils.patchValue({type:this.cargoEmmiter.getcargo().type});
    this.cargoDeatils.patchValue({sensitivity:this.cargoEmmiter.getcargo().sensitivity});
    this.cargoDeatils.patchValue({id:this.cargoEmmiter.getcargo().id});
    this.cargoDeatils.patchValue({specialRemarks:this.cargoEmmiter.getcargo().specialRemarks});
    this.cargoDeatils.patchValue({delivered:this.cargoEmmiter.getcargo().delivered});
    this.cargoDeatils.patchValue({assigned:this.cargoEmmiter.getcargo().assigned});
    this.cargoDeatils.patchValue({weight:this.cargoEmmiter.getcargo().weight});
    this.cargoDeatils.patchValue({width:this.cargoEmmiter.getcargo().width});
    this.cargoDeatils.patchValue({height:this.cargoEmmiter.getcargo().height});
    this.cargoDeatils.patchValue({depth:this.cargoEmmiter.getcargo().depth});
  }
  closeModal() {
    this.dialogRef.close();
  }

  onSubmit() {

    this.cargoService.add(this.cargoDeatils.getRawValue()).subscribe();
  }
}
