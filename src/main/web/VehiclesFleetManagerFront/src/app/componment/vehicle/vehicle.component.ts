import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {VehicleService} from "./service/vehicle.service";
import {Vehicle} from "../../model/vehicle";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../route-creator/route-creator.component";

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

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  constructor(private vehicleService:VehicleService) { }


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

}
