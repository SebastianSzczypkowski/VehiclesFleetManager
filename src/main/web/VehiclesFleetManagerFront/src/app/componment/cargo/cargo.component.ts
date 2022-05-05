import {Component, OnInit, ViewChild} from '@angular/core';
import {Driver} from "../../model/driver";
import {CargoService} from "./service/cargo.service";
import {Cargo} from "../../model/cargo";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../route-creator/route-creator.component";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {CargoEmmiterService} from "./service/cargo-emmiter.service";
import {DriverInfoComponent} from "../driver/driver-info/driver-info.component";
import {CargoInfoComponent} from "./cargo-info/cargo-info.component";

@Component({
  selector: 'app-cargo',
  templateUrl: './cargo.component.html',
  styleUrls: ['./cargo.component.css']
})
export class CargoComponent implements OnInit {

  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  cargos:Cargo[]=[];
  dataSource = new MatTableDataSource<PeriodicElement>();
  displayedColumns: string[] = ['position', 'name', 'description', 'type','specialRemarks','delivered','assigned'];
  @ViewChild(MatPaginator) paginator!: MatPaginator
  private id!: number;
  @ViewChild(MatTable) table!: MatTable<any>;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private cargoService:CargoService,public matDialog:MatDialog,
              private cargoEmmiter:CargoEmmiterService) { }

  ngOnInit(): void {
    // this.cargoService.getAll().subscribe(
    //   data=>{
    //     this.cargos=data;
    //   })
    this.cargoService.getAllPage(this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.cargos=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;


      })
  }
  getServerData(event: PageEvent) {
    if(event?.pageIndex!=null)
      this.cargoService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.cargos=response.content;
          this.pageIndex=response.number;
          this.pageSize=response.size;
          this.length=response.totalElements;
          this.pageIndex=event.pageIndex;
          this.pageSize=event.pageSize;
        }
      )
    else
      this.cargoService.getAllPage(0,this.pageSize)
    return event;
  }
  getCargoDeatils(c:Cargo)
  {

    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "driver-info-component";
    dialogConfig.height = "650px";
    dialogConfig.width = "650px";
    const modalDialog = this.matDialog.open(CargoInfoComponent, dialogConfig);

    this.cargos.forEach(e=>{
      if(e.id ==c.id)
        this.id=e.id;
    });
    this.cargoEmmiter.setcargo(c);

    //this.driverEmmiter.driverDetails.emit();
  }

  search(event: any) {

    this.cargoService.getAllPageSearch(event.target.value,this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.cargos=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();
  }
}
