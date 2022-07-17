import {Component, OnInit, ViewChild} from '@angular/core';
import {Vehicle} from "../../model/vehicle";
import {DriverService} from "./service/driver.service";
import {Driver} from "../../model/driver";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";

import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {RoadInfoComponent} from "../set-route/road-info/road-info.component";
import {DriverInfoComponent} from "./driver-info/driver-info.component";
import {DriverEmmiterService} from "./service/driver-emmiter.service";

@Component({
  selector: 'app-driver',
  templateUrl: './driver.component.html',
  styleUrls: ['./driver.component.css']
})
export class DriverComponent implements OnInit {

  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  drivers:Driver[]=[];
  dataSource = new MatTableDataSource<Driver>();
  displayedColumns: string[] = ['position', 'name', 'surname', 'pesel','address'];
  @ViewChild(MatPaginator) paginator!: MatPaginator
  @ViewChild(MatTable) table!: MatTable<any>;
  private id!: number;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private driverService:DriverService,public matDialog:MatDialog,
              private driverEmmiter:DriverEmmiterService) { }

  ngOnInit(): void {
    // this.driverService.getAll().subscribe(
    //   data=>{
    //     this.drivers=data;
    //   })
    this.driverService.getAllPage(this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.drivers=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;


      })
  }
  getServerData(event: PageEvent) {
    if(event?.pageIndex!=null)
      this.driverService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.drivers=response.content;
          this.pageIndex=response.number;
          this.pageSize=response.size;
          this.length=response.totalElements;
          this.pageIndex=event.pageIndex;
          this.pageSize=event.pageSize;
        }
      )
    else
      this.driverService.getAllPage(0,this.pageSize)
    return event;
  }

  getDriverDeatils(d:Driver)
  {

    const dialogConfig = new MatDialogConfig();
    // The user can't close the dialog by clicking outside its body
    dialogConfig.disableClose = true;
    dialogConfig.id = "driver-info-component";
    dialogConfig.height = "500px";
    dialogConfig.width = "620px";

    const modalDialog = this.matDialog.open(DriverInfoComponent, dialogConfig);

    this.drivers.forEach(e=>{
      if(e.id ==d.id)
        this.id=e.id;
    });
    this.driverEmmiter.setdriverId(this.id);
    this.driverEmmiter.setdriver(d);
    //this.driverEmmiter.driverDetails.emit();
  }

  search(event: any) {
    this.driverService.getAllPageSearch(event.target.value,this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.drivers=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();
  }
}
