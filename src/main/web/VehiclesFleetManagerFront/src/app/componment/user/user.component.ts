import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Vehicle} from "../../model/vehicle";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {UserDetailsComponent} from "./user-details/user-details.component";
import {User} from "../../model/user";
import {UserService} from "./service/user.service";
import {DriverEmmiterService} from "../driver/service/driver-emmiter.service";
import {UserEmmiterService} from "./service/user-emmiter.service";
@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit,AfterViewInit  {

  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  newUser:User = new User();
  users:User[]=[];
  displayedColumns: string[] = ['id', 'email', 'login', 'password'];
  dataSource = new MatTableDataSource<User>();
  @ViewChild(MatPaginator) paginator!: MatPaginator
  private id!: number;
  @ViewChild(MatTable) table!: MatTable<any>;
  ngAfterViewInit() {
   this.dataSource.paginator = this.paginator;
   // this.dataSource.sort = this.sort;
  }


  constructor(private userService:UserService,public matDialog:MatDialog,
              private userEmmiter:UserEmmiterService) { }


  ngOnInit(): void {

    this.userService.getAllPage(this.pageIndex,this.pageSize).subscribe(
      data=> {

        this.users = data.content;
        this.pageIndex = data.number;
        this.pageSize = data.size;
        this.length = data.totalElements;
      }
    )

  }


  getServerData(event: PageEvent) {
    if(event?.pageIndex!=null)
      this.userService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.users=response.content;
          this.pageIndex=response.number;
          this.pageSize=response.size;
          this.length=response.totalElements;
          this.pageIndex=event.pageIndex;
          this.pageSize=event.pageSize;
        }
      )
    else
      this.userService.getAllPage(0,this.pageSize)
    return event;
  }
  getuserDeatils(d:any)
  {

    console.log(d);
    const dialogConfig = new MatDialogConfig();
    // The user can't close the dialog by clicking outside its body
    dialogConfig.disableClose = true;
    dialogConfig.id = "user-info-component";
    dialogConfig.height = "400px";
    dialogConfig.width = "850px";
    const modalDialog = this.matDialog.open(UserDetailsComponent, dialogConfig);

    this.users.forEach(e=>{
      if(e.id ==d.id)
        this.id=e.id;
    });
    this.userEmmiter.setuserID(this.id);
    this.userEmmiter.setuser(d);
    //this.driverEmmiter.driverDetails.emit();
  }
  search(event: any) {

    this.userService.getAllPageSearch(event.target.value,this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.users=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();
  }

}
