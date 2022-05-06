import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Road} from "../../../model/road";
import {MatDialogRef} from "@angular/material/dialog";
import {RoadEmmiterService} from "../service/road-emmiter.service";
import {RouteCreatorService} from "../service/route-creator.service";
import {Driver} from "../../../model/driver";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {DriverService} from "../../driver/service/driver.service";
import {MatSort} from "@angular/material/sort";
import {Vehicle} from "../../../model/vehicle";
import {VehicleService} from "../../vehicle/service/vehicle.service";
import {Cargo} from "../../../model/cargo";
import {CargoService} from "../../cargo/service/cargo.service";

@Component({
  selector: 'app-road-deatils',
  templateUrl: './road-deatils.component.html',
  styleUrls: ['./road-deatils.component.css']
})
export class RoadDeatilsComponent implements OnInit {


  roadDeatils!:FormGroup;
  route!:FormGroup;
  roadData!:Road;
  //Kierowca
  drivers:Driver[]=[];
  driversEvent: PageEvent = new PageEvent;
  driversIndex=0;
  driversSize=5;
  driversLength!:number;
  driversSource = new MatTableDataSource<Driver>();
  clickedDriver = new Set<Driver>();
  driverSelected!:Driver;
  driversColumns: string[] = ['id', 'name', 'surname', 'pesel','address'];
  //Pojazd
  vehicles:Vehicle[]=[];
  vehiclesEvent: PageEvent = new PageEvent;
  vehiclesIndex=0;
  vehiclesSize=5;
  vehiclesLength!:number;
  vehiclesColumns: string[] = ['id', 'name', 'vin', 'registrationNumber','engineCapacity','averageFuelConsumptio'];
  clickedVehilce = new Set<Vehicle>();
  vehicleSelected!:Vehicle;
  //ładunek
  cargos:Cargo[]=[];
  cargosEvent: PageEvent = new PageEvent;
  cargosIndex=0;
  cargosSize=5;
  cargosLength!:number;
  cargosColumns: string[] = ['id', 'name', 'description', 'type','sensitivity','specialRemarks'];
  clickedCargo = new Set<Cargo>();
  cargoSelected!:Cargo;

  @ViewChild(MatTable) table!: MatTable<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(private formBuilder: FormBuilder,public dialogRef:MatDialogRef<RoadDeatilsComponent>,
              private roadEmmiter:RoadEmmiterService,private roadService:RouteCreatorService,
              private driverService:DriverService,private vehicleService:VehicleService,
              private cargoService:CargoService) { }

  ngOnInit(): void {

    this.route=this.formBuilder.group({
      start:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      end:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      color:new FormControl(''),
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    })

    this.driverService.getAllPage(this.driversIndex,this.driversSize).subscribe(
      data=>{
        this.drivers=data.content;
        this.driversIndex=data.number;
        this.driversSize=data.size;
        this.driversLength=data.totalElements;
      }
    )
    this.vehicleService.getAllPage(this.vehiclesIndex,this.vehiclesSize).subscribe(
      data=>{
        this.vehicles=data.content;
        this.vehiclesIndex=data.number;
        this.vehiclesSize=data.size;
        this.vehiclesLength=data.totalElements;
      }
    )

    this.cargoService.getAllPage(this.vehiclesIndex,this.vehiclesSize).subscribe(
      data=>{
        this.cargos=data.content;
        this.cargosIndex=data.number;
        this.cargosSize=data.size;
        this.cargosLength=data.totalElements;
      }
    )

    this.roadDeatils=this.formBuilder.group({
      start:[],
      end:[],
      driver:[],
      cargo:[],
    })
  }


  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    //this.dataSource.sort = this.sort; TODO posortować tabele
    this.driversSource.paginator = this.paginator;
  }

  closeModal() {
    this.dialogRef.close();
  }

  getServerDataDrivers(event: PageEvent) {
    if(event?.pageIndex!=null)
      this.driverService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.drivers=response.content;
          this.driversIndex=response.number;
          this.driversSize=response.size;
          this.driversLength=response.totalElements;
          this.driversIndex=event.pageIndex;
          this.driversSize=event.pageSize;
        }
      )
    else
      this.driverService.getAllPage(0,this.driversSize)
    return event;
  }
  onDriverClick(element:Driver) {
    this.driverSelected=element;
  }
  search(event: any) {
    this.driverService.getAllPageSearch(event.target.value,this.driversIndex,this.driversSize).subscribe(
      data=>{
        this.drivers=data.content;
        this.driversIndex=data.number;
        this.driversSize=data.size;
        this.driversLength=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();

  }
  getServerDataVehicles(event: any) {
    if(event?.pageIndex!=null)
      this.vehicleService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.vehicles=response.content;
          this.vehiclesIndex=response.number;
          this.vehiclesSize=response.size;
          this.vehiclesLength=response.totalElements;
          this.vehiclesIndex=event.pageIndex;
          this.vehiclesSize=event.pageSize;
        }
      )
    else
      this.driverService.getAllPage(0,this.driversSize)
    return event;
  }
  searchVehicles(event: any) {
    this.vehicleService.getAllPageSearch(event.target.value,this.vehiclesIndex,this.vehiclesSize).subscribe(
      data=>{
        this.vehicles=data.content;
        this.vehiclesIndex=data.number;
        this.vehiclesSize=data.size;
        this.vehiclesLength=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();

  }
  onVehicleClick(element:Vehicle) {
    this.vehicleSelected=element;

  }

  getServerDataCargos(event: any) {
    if(event?.pageIndex!=null)
      this.cargoService.getAllPage(event?.pageIndex,event.pageSize).subscribe(
        response=>
        {
          this.cargos=response.content;
          this.cargosIndex=response.number;
          this.cargosSize=response.size;
          this.cargosLength=response.totalElements;
          this.cargosIndex=event.pageIndex;
          this.cargosSize=event.pageSize;
        }
      )
    else
      this.cargoService.getAllPage(0,this.cargosSize)
    return event;
  }
  searchCargos(event: any) {
    this.cargoService.getAllPageSearch(event.target.value,this.cargosIndex,this.cargosSize).subscribe(
      data=>{
        this.cargos=data.content;
        this.cargosIndex=data.number;
        this.cargosSize=data.size;
        this.cargosLength=data.totalElements;
        this.table.renderRows();
      }
    );
    this.table.renderRows();

  }
  onCargoClick(element:Cargo) {
    this.cargoSelected=element;
  }
}
