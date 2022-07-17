import {
  AfterViewInit,
  Component,
  Directive,
  ElementRef,
  HostListener,
  Input,
  OnChanges,
  OnInit,
  Renderer2,
  SimpleChanges, ViewChild
} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {STEPPER_GLOBAL_OPTIONS} from "@angular/cdk/stepper";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatSort, Sort} from "@angular/material/sort";
import {RouteCreatorService} from "./service/route-creator.service";
import {MapService} from "../../service/map.service";
import {Coordinates} from "../../model/coordinates";
import {Driver} from "../../model/driver";
import {DriverService} from "../driver/service/driver.service";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Vehicle} from "../../model/vehicle";
import {VehicleService} from "../vehicle/service/vehicle.service";
import {Cargo} from "../../model/cargo";
import {CargoService} from "../cargo/service/cargo.service";
import {MatStepper} from "@angular/material/stepper";
import {ToastrService} from "ngx-toastr";


@Component({
  selector: 'app-route-creator',
  templateUrl: './route-creator.component.html',
  styleUrls: ['./route-creator.component.css'],
  providers: [
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: {displayDefaultIndicatorType: false},
    },
  ],
})

export class RouteCreatorComponent implements OnInit,AfterViewInit{


  firstFormGroup!: FormGroup;
  roadFormGroup!: FormGroup ;
  defaultElevation = 2;
  raisedElevation = 8;
  driversColumns: string[] = ['id', 'name', 'surname', 'pesel','address'];
  vehiclesColumns: string[] = ['id', 'name', 'vin', 'registrationNumber','engineCapacity','averageFuelConsumption'];
  cargosColumns: string[] = ['id', 'name', 'description', 'type','sensitivity','specialRemarks'];
  drivers:Driver[]=[];
  driversEvent: PageEvent = new PageEvent;
  driversIndex=0;
  driversSize=10;
  driversLength!:number;
  driversSource = new MatTableDataSource<Driver>();
  vehicles:Vehicle[]=[];
  vehiclesEvent: PageEvent = new PageEvent;
  vehiclesIndex=0;
  vehiclesSize=10;
  vehiclesLength!:number;
  cargos:Cargo[]=[];
  cargosEvent: PageEvent = new PageEvent;
  cargosIndex=0;
  cargosSize=10;
  cargosLength!:number;

  clickedDriver = new Set<Driver>();
  driverSelected!:Driver;
  clickedVehilce = new Set<Vehicle>();
  vehicleSelected!:Vehicle;
  clickedCargo = new Set<Cargo>();
  cargoSelected!:Cargo;
  coordinates:Coordinates[]=[];
  @ViewChild(MatTable) table!: MatTable<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(private _formBuilder: FormBuilder,private _liveAnnouncer: LiveAnnouncer
              ,private routeCreatorService:RouteCreatorService,
              private mapService:MapService,private driverService:DriverService,
              private vehicleService:VehicleService,private cargoService:CargoService,
              private toaster:ToastrService) {

  }


  ngOnInit(): void {

    this.firstFormGroup = this._formBuilder.group({
      start:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      end:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      color:new FormControl(''),
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    });
    this.roadFormGroup = this._formBuilder.group({
      start:[],
      end:[],
      driver:[],
      cargo:[],
    });

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

  }

  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    //this.dataSource.sort = this.sort; TODO posortować tabele
    this.driversSource.paginator = this.paginator;
  }


  announceSortChange(sortState: Sort) {

    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  showRoute()
  {
    this.mapService.getCoordinates(this.firstFormGroup.get('start')?.value,this.firstFormGroup.get('end')?.value,
      this.firstFormGroup.get('color')?.value,this.firstFormGroup.get('name')?.value).subscribe(

      data=>{
        this.coordinates=data;
        // // @ts-ignore
        // this.map.addNewRoute();
        this.routeCreatorService.routeCreatorShow(this.coordinates);

          this.toaster.success("Pobrano trasę");

          // console.log("Retrieved DATA: " + JSON.stringify(data));
        // console.log("Retrieved DATA: " + JSON.stringify(this.coordinates));
        // console.log("Retrieved DATA: " + JSON.stringify(this.coordinates[1]));

      },err=>{
        this.toaster.error("Nie udało się pobrać trasy");
      }

    )
  }


  remove() {
    this.routeCreatorService.routeCreatorRemove();
      this.toaster.info("Usunięto trasę");

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
  goBack(stepper: MatStepper){
    stepper.previous();
  }

  goForward(stepper: MatStepper){
    stepper.next();
  }

  onDriverClick(element:Driver) {
    this.driverSelected=element;
    this.toaster.info("Wybrano kierowcę");
  }

  onVehicleClick(element:Vehicle) {
    this.vehicleSelected=element;
    this.toaster.info("Wybrano pojazd");

  }

  onCargoClick(element:Cargo) {
    this.cargoSelected=element;
    this.toaster.info("Wybrano ładunek");
  }

  save() {

    this.roadFormGroup.patchValue({start:this.coordinates[0],
    end:this.coordinates[1],driver:this.driverSelected,cargo:this.cargoSelected});
    this.routeCreatorService.add(this.roadFormGroup.getRawValue()).subscribe(
      data=>{
        this.toaster.success("Dodano trasę");
      },
      err=>{
        this.toaster.error("Nie udało się dodać trasy");
      }
    )
  }
}
