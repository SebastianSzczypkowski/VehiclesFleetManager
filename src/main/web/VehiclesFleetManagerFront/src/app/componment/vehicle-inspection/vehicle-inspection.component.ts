import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Vehicle} from "../../model/vehicle";
import {VehicleInspectionService} from "./service/vehicle-inspection.service";
import {Vehicleinspection} from "../../model/vehicleinspection";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../route-creator/route-creator.component";

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
  dataSource = new MatTableDataSource<PeriodicElement>();
  displayedColumns: string[] = ['position', 'nazwa','description','performedBy'];//, 'date', 'validityDate'
  @ViewChild(MatPaginator) paginator!: MatPaginator

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private _formBuilder: FormBuilder,private vehicleInspectionService:VehicleInspectionService) { }

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
}
