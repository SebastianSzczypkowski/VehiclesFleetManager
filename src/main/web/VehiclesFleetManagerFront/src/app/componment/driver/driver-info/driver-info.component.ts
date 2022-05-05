import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {Driver} from "../../../model/driver";
import {DriverEmmiterService} from "../service/driver-emmiter.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DriverService} from "../service/driver.service";
import {MatTable} from "@angular/material/table";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-driver-info',
  templateUrl: './driver-info.component.html',
  styleUrls: ['./driver-info.component.css']
})
export class DriverInfoComponent implements OnInit {

  constructor(private _formBuilder: FormBuilder,public dialogRef:MatDialogRef<DriverInfoComponent>,
              private driverEmmiter:DriverEmmiterService,private driverService:DriverService,
              private toaster:ToastrService) { }

  driverData!: Driver;
  driverDeatils!:FormGroup;
  name!:string;
  driverId!:number;

  ngOnInit(): void {

    this.driverDeatils=this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      surname:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      address:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      pesel:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      id:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      dateOfBirth:[new Date()],


    })

    this.driverId=this.driverEmmiter.getdriverId();
    this.driverData=this.driverEmmiter.getdriver();
    this.driverDeatils.patchValue({name:this.driverEmmiter.getdriver().name});
    this.driverDeatils.patchValue({surname:this.driverEmmiter.getdriver().surname});
    this.driverDeatils.patchValue({address:this.driverEmmiter.getdriver().address});
    this.driverDeatils.patchValue({pesel:this.driverEmmiter.getdriver().pesel});
    this.driverDeatils.patchValue({id:this.driverEmmiter.getdriver().id});
    this.driverDeatils.patchValue({dateOfBirth:this.driverEmmiter.getdriver().dateOfBirth});

  }

  closeModal() {

   this.dialogRef.close();

  }

  onSubmit() {

    this.driverService.add(this.driverDeatils.getRawValue()).subscribe(
      data=>{
        this.toaster.success("Zapisano zmiany");
      },
      err=>{
        this.toaster.error("Nie udało się zapisać zmian");
      }
    );
  }
}
