import {Component, OnInit, ViewChild} from '@angular/core';
import {Vehicle} from "../../model/vehicle";
import {DriverService} from "./service/driver.service";
import {Driver} from "../../model/driver";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../route-creator/route-creator.component";

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
  dataSource = new MatTableDataSource<PeriodicElement>();
  displayedColumns: string[] = ['position', 'name', 'surname', 'pesel','address'];
  @ViewChild(MatPaginator) paginator!: MatPaginator

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private driverService:DriverService) { }

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
}
