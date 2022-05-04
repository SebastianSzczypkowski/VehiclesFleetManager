import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {VehicleInspectionService} from "../service/vehicle-inspection.service";
import {VehilceInspectionEmmiterService} from "../service/vehilce-inspection-emmiter.service";
import {Driver} from "../../../model/driver";
import {Vehicleinspection} from "../../../model/vehicleinspection";
import {MatTable} from "@angular/material/table";

@Component({
  selector: 'app-vehicle-inspection-info',
  templateUrl: './vehicle-inspection-info.component.html',
  styleUrls: ['./vehicle-inspection-info.component.css']
})
export class VehicleInspectionInfoComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,public dialogRef:MatDialogRef<VehicleInspectionInfoComponent>,
              private vehicleInspectionService:VehicleInspectionService,private vehilceInspectionEmmiyer:VehilceInspectionEmmiterService) { }

  vehicleInspectionData!: Vehicleinspection;
  vehicleInspectionDeatils!:FormGroup;

  ngOnInit(): void {
    this.vehicleInspectionDeatils=this.formBuilder.group({
      carRepairShopName:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      description:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      performedBy:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      idVehicle:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      id:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),

    })

    this.vehicleInspectionData=this.vehilceInspectionEmmiyer.getvehicleInspection();
    this.vehicleInspectionDeatils.patchValue({carRepairShopName:this.vehilceInspectionEmmiyer.getvehicleInspection().carRepairShopName});
    this.vehicleInspectionDeatils.patchValue({description:this.vehilceInspectionEmmiyer.getvehicleInspection().description});
    this.vehicleInspectionDeatils.patchValue({performedBy:this.vehilceInspectionEmmiyer.getvehicleInspection().performedBy});
    this.vehicleInspectionDeatils.patchValue({idVehicle:this.vehilceInspectionEmmiyer.getvehicleInspection().idVehicle});
    this.vehicleInspectionDeatils.patchValue({id:this.vehilceInspectionEmmiyer.getvehicleInspection().id});

  }

  closeModal() {
    this.dialogRef.close();
  }

  onSubmit() {

    this.vehicleInspectionService.add(this.vehicleInspectionDeatils.getRawValue()).subscribe();
  }
}
