import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Vehicle} from "../../model/vehicle";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {VehicleService} from "../vehicle/service/vehicle.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {VehicleEmmiterService} from "../vehicle/service/vehicle-emmiter.service";
import {VehicleInfoComponent} from "../vehicle/vehicle-info/vehicle-info.component";
import {MatSort, Sort} from "@angular/material/sort";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {UserDetailsComponent} from "./user-details/user-details.component";

export interface PeriodicElement {
  email: string;
  position: number;
  login: string;
  password: string;
  role: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, email: 'admin@admin.com', login: "admin", password: 'admin',role:'admin'},
  {position: 2, email: 'tadeusztarka@gmail.com', login: "tadeusztarka", password: '1234',role:'kierownik'},
  {position: 3, email: 'mirosławcichy@gmail.com', login: "mirosławcichy", password: '1234',role:'kierownik'},
  {position: 4, email: 'szsebastian146@gmail.com', login: "sebastianszczypkowski", password: 'sebastian98',role:'kierowca'},
  {position: 5, email: 'janusznowak12@gmail.com', login: "janusznowak", password: '1234',role:'kierowca'},
  {position: 6, email: 'damiandrzazga34354@gmail.com', login: "damiandrzazga", password: '1234',role:'kierowca'},
  {position: 7, email: 'robertkubica1234@gmail.com', login: "robertkubica", password: '1234',role:'kierowca'},
  {position: 8, email: 'włodzimierzkubek@gmail.com', login: "włodzimierzkubek", password: '1234',role:'kierowca'},
  {position: 9, email: 'krzysztofnowak77@gmail.com', login: "krzysztofnowak", password: '1234',role:'kierowca'},

];

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  vehicles:Vehicle[]=[];
  displayedColumns: string[] = ['position', 'email', 'login', 'password','role'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator!: MatPaginator
  private id!: number;
  @ViewChild(MatTable) table!: MatTable<any>;
  @ViewChild(MatSort) sort!: MatSort;
  ngAfterViewInit() {
   // this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  constructor(private vehicleService:VehicleService,public matDialog:MatDialog,
              private vehicleEmmiter:VehicleEmmiterService,private _liveAnnouncer: LiveAnnouncer) { }


  ngOnInit(): void {

    // this.vehicleService.getAllVehilce().subscribe(
    //     data=>{
    //       this.vehicles=data;
    // }
    //
    // )

  }
  announceSortChange(sortState: Sort) {
    // This example uses English messages. If your application supports
    // multiple language, you would internationalize these strings.
    // Furthermore, you can customize the message to add additional
    // details about the values being sorted.
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
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
    dialogConfig.height = "400px";
    dialogConfig.width = "800px";
    const modalDialog = this.matDialog.open(UserDetailsComponent, dialogConfig);

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
