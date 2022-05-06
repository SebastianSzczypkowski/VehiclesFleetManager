import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Vehicle} from "../../model/vehicle";
import {VehicleInspectionService} from "./service/vehicle-inspection.service";
import {Vehicleinspection} from "../../model/vehicleinspection";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {Driver} from "../../model/driver";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DriverInfoComponent} from "../driver/driver-info/driver-info.component";
import {VehicleEmmiterService} from "../vehicle/service/vehicle-emmiter.service";
import {VehilceInspectionEmmiterService} from "./service/vehilce-inspection-emmiter.service";
import {VehicleInspectionInfoComponent} from "./vehicle-inspection-info/vehicle-inspection-info.component";

@Component({
  selector: 'app-vehicle-inspection',
  templateUrl: './vehicle-inspection.component.html',
  styleUrls: ['./vehicle-inspection.component.css']
})
export class VehicleInspectionComponent implements OnInit,AfterViewInit {


  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  vehicleinspections:Vehicleinspection[]=[];
  dataSource = new MatTableDataSource<Vehicleinspection>();
  displayedColumns: string[] = ['position', 'nazwa','description','performedBy'];//, 'date', 'validityDate'
  @ViewChild(MatPaginator) paginator!: MatPaginator
  @ViewChild(MatTable) table!: MatTable<any>;


  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private _formBuilder: FormBuilder,private vehicleInspectionService:VehicleInspectionService,
              public matDialog:MatDialog,private vehicleInspectionEmmiter:VehilceInspectionEmmiterService) { }

  ngOnInit(): void {

    // this.vehicleInspectionService.getAll().subscribe(
    //   data=>{
    //     this.vehicleinspections=data;
    //   })
    this.vehicleInspectionService.getAllPage(this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.vehicleinspections=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;


      })
  }

  onSubmit() {

  }
  getServerData(event: PageEvent) {
    if(event?.pageIndex!=null)
      this.vehicleInspectionService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.vehicleinspections=response.content;
          this.pageIndex=response.number;
          this.pageSize=response.size;
          this.length=response.totalElements;
          this.pageIndex=event.pageIndex;
          this.pageSize=event.pageSize;
        }
      )
    else
      this.vehicleInspectionService.getAllPage(0,this.pageSize)
    return event;
  }
  getVehicleInspectionDeatils(vI:Vehicleinspection)
  {
    const dialogConfig = new MatDialogConfig();
    // The user can't close the dialog by clicking outside its body
    dialogConfig.disableClose = true;
    dialogConfig.id = "driver-info-component";
    dialogConfig.height = "450px";
    dialogConfig.width = "650px";
    const modalDialog = this.matDialog.open(VehicleInspectionInfoComponent, dialogConfig);

    this.vehicleInspectionEmmiter.setvehicleInspection(vI);
    //this.driverEmmiter.driverDetails.emit();
  }
  search(event: any) {

    this.vehicleInspectionService.getAllPageSearch(event.target.value,this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.vehicleinspections=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();
  }
}
