import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {VehicleService} from "./service/vehicle.service";
import {Vehicle} from "../../model/vehicle";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../route-creator/route-creator.component";
import {Driver} from "../../model/driver";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DriverInfoComponent} from "../driver/driver-info/driver-info.component";
import {VehicleInfoComponent} from "./vehicle-info/vehicle-info.component";
import {VehicleEmmiterService} from "./service/vehicle-emmiter.service";

@Component({
  selector: 'app-vehicle',
  templateUrl: './vehicle.component.html',
  styleUrls: ['./vehicle.component.css']
})
export class VehicleComponent implements OnInit,AfterViewInit {

  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  vehicles:Vehicle[]=[];
  displayedColumns: string[] = ['position', 'nazwa', 'vin', 'rejestracja'];
  dataSource = new MatTableDataSource<PeriodicElement>();
  @ViewChild(MatPaginator) paginator!: MatPaginator
  private id!: number;
  @ViewChild(MatTable) table!: MatTable<any>;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  constructor(private vehicleService:VehicleService,public matDialog:MatDialog,
              private vehicleEmmiter:VehicleEmmiterService) { }


  ngOnInit(): void {

    // this.vehicleService.getAllVehilce().subscribe(
    //     data=>{
    //       this.vehicles=data;
    // }
    //
    // )
    this.vehicleService.getAllPage(this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.vehicles=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;


      })
  }
  getServerData(event: PageEvent) {
    if(event?.pageIndex!=null)
      this.vehicleService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.vehicles=response.content;
          this.pageIndex=response.number;
          this.pageSize=response.size;
          this.length=response.totalElements;
          this.pageIndex=event.pageIndex;
          this.pageSize=event.pageSize;
        }
      )
    else
      this.vehicleService.getAllPage(0,this.pageSize)
    return event;
  }
  getVehicleDeatils(d:Vehicle)
  {

    console.log(d);
    const dialogConfig = new MatDialogConfig();
    // The user can't close the dialog by clicking outside its body
    dialogConfig.disableClose = true;
    dialogConfig.id = "driver-info-component";
    dialogConfig.height = "700px";
    dialogConfig.width = "600px";
    const modalDialog = this.matDialog.open(VehicleInfoComponent, dialogConfig);

    this.vehicles.forEach(e=>{
      if(e.id ==d.id)
        this.id=e.id;
    });
    this.vehicleEmmiter.setvehilce(d);

    //this.driverEmmiter.driverDetails.emit();
  }
  search(event: any) {

    this.vehicleService.getAllPageSearch(event.target.value,this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.vehicles=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();
  }
}
