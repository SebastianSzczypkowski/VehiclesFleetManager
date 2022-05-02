import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DriverService} from "../service/driver.service";
import {Entitlementstotransport} from "../../../model/entitlementstotransport";

@Component({
  selector: 'app-driver-creator',
  templateUrl: './driver-creator.component.html',
  styleUrls: ['./driver-creator.component.css']
})
export class DriverCreatorComponent implements OnInit {

  entitlements:Entitlementstotransport[]=[];
  entitle:Entitlementstotransport =new Entitlementstotransport() ;
  driverForm!:FormGroup;
  permissionForm!:FormGroup;
  entitlementsColumns: string[] = [ 'documentTyp', 'typeofpermission'];
  constructor(private _formBuilder: FormBuilder,private driverService:DriverService) { }

  ngOnInit(): void {
    this.driverForm = this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      surname:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      pesel:new FormControl(''),
      dateofbirth:[new Date()],
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
    console.log(this.driverForm.getRawValue());
    this.driverService.add(this.driverForm.getRawValue()).subscribe();
  }

  reset()
  {
    this.driverForm.reset();
  }

  onSubmitPermission() {

    this.entitle.typeofpermission=this.permissionForm.getRawValue().name;
    this.entitlements.push(this.entitle);
  }
}
