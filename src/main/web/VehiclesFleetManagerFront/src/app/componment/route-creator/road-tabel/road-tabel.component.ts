import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Vehicle} from "../../../model/vehicle";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../route-creator.component";
import {Road} from "../../../model/road";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {RouteCreatorService} from "../service/route-creator.service";
import {ToastrService} from "ngx-toastr";
import {VehicleInfoComponent} from "../../vehicle/vehicle-info/vehicle-info.component";
import {RoadEmmiterService} from "../service/road-emmiter.service";
import {RoadDeatilsComponent} from "../road-deatils/road-deatils.component";

@Component({
  selector: 'app-road-tabel',
  templateUrl: './road-tabel.component.html',
  styleUrls: ['./road-tabel.component.css']
})
export class RoadTabelComponent implements OnInit,AfterViewInit  {

  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  roads:Road[]=[];
  displayedColumns: string[] = ['id', 'start', 'end',"driver","cargo"];
  dataSource = new MatTableDataSource<PeriodicElement>();
  @ViewChild(MatPaginator) paginator!: MatPaginator
  private id!: number;
  @ViewChild(MatTable) table!: MatTable<any>;

  constructor(public matDialog:MatDialog,private roadService:RouteCreatorService,private toastr:ToastrService,
              private roadEmmiter:RoadEmmiterService) { }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  ngOnInit(): void {
    this.roadService.getAllPage(this.pageIndex,this.pageSize).subscribe(
      data=>{

        this.roads=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        console.log(data);
        console.log(this.roads);
      },
      err=>{
        this.toastr.error("Nie udało się załadować danych");
      }
    )
  }
  getServerData(event: PageEvent) {
    if(event?.pageIndex!=null)
      this.roadService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.roads=response.content;
          this.pageIndex=response.number;
          this.pageSize=response.size;
          this.length=response.totalElements;
          this.pageIndex=event.pageIndex;
          this.pageSize=event.pageSize;

        }
      )
    else
      this.roadService.getAllPage(0,this.pageSize)
    return event;
  }
  getRoadDeatils(d:Road)
  {

    console.log(d);
    const dialogConfig = new MatDialogConfig();
    // The user can't close the dialog by clicking outside its body
    dialogConfig.disableClose = true;
    dialogConfig.id = "driver-info-component";
    dialogConfig.height = "700px";
    dialogConfig.width = "700px";
    const modalDialog = this.matDialog.open(RoadDeatilsComponent, dialogConfig);

    this.roads.forEach(e=>{
      if(e.id ==d.id)
        this.id=e.id;
    });
    this.roadEmmiter.setroad(d);

    //this.driverEmmiter.driverDetails.emit();
  }

  search(event: any) {

    this.roadService.getAllPageSearch(event.target.value,this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.roadService=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();
  }
}
