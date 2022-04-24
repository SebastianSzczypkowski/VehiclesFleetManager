import {Component, OnInit, ViewChild} from '@angular/core';
import {Driver} from "../../model/driver";
import {CargoService} from "./service/cargo.service";
import {Cargo} from "../../model/cargo";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../route-creator/route-creator.component";

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

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private cargoService:CargoService) { }

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
}
