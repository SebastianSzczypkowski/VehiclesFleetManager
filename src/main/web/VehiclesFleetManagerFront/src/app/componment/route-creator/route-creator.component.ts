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

export interface PeriodicElement {
  name: string;
  position: number;
  driverLicenc: string;
  additionalPermissions: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Jan Nowak', driverLicenc: "B,C", additionalPermissions: 'H'},
  {position: 2, name: 'Tadeusz Tarka', driverLicenc: "B+E", additionalPermissions: 'He'},
  {position: 3, name: 'Mirosław Cichy', driverLicenc: "B,C1", additionalPermissions: 'Li'},
  {position: 4, name: 'Jakub K', driverLicenc: "B,C+E", additionalPermissions: 'Be'},
  {position: 5, name: 'Adam Z', driverLicenc: "B,C1+E", additionalPermissions: 'B'},
  {position: 6, name: 'Jan R', driverLicenc: "B+E,C+E,D", additionalPermissions: 'C'},
  {position: 7, name: 'Janusz J', driverLicenc: "A,B,C1", additionalPermissions: 'N'},
  {position: 8, name: 'Maciej K', driverLicenc: "B,C", additionalPermissions: 'O'},
  {position: 9, name: 'Filip P', driverLicenc: "B,C1", additionalPermissions: 'F'},

];


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
  secondFormGroup!: FormGroup ;
  defaultElevation = 2;
  raisedElevation = 8;
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);
  drivers:Driver[]=[];
  driversEvent: PageEvent = new PageEvent;
  driversIndex=0;
  driversSize=10;
  driversLength!:number;
  driversSource = new MatTableDataSource<PeriodicElement>();
  vehicles:Vehicle[]=[];
  vehiclesEvent: PageEvent = new PageEvent;
  vehiclesIndex=0;
  vehiclesSize=10;
  vehiclesLength!:number;

  coordinates:Coordinates[]=[];
  @ViewChild(MatTable) table!: MatTable<any>;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  constructor(private _formBuilder: FormBuilder,private _liveAnnouncer: LiveAnnouncer
              ,private routeCreatorService:RouteCreatorService,
              private mapService:MapService,private driverService:DriverService,
              private vehicleService:VehicleService) {

  }


  ngOnInit(): void {

    this.firstFormGroup = this._formBuilder.group({
      start:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      end:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      color:new FormControl(''),
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required],
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

  }

  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.driversSource.paginator = this.paginator;
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

  showRoute()
  {
    this.mapService.getCoordinates(this.firstFormGroup.get('start')?.value,this.firstFormGroup.get('end')?.value,
      this.firstFormGroup.get('color')?.value,this.firstFormGroup.get('name')?.value).subscribe(

      data=>{
        this.coordinates=data;
        // // @ts-ignore
        // this.map.addNewRoute();
        this.routeCreatorService.routeCreatorShow(this.coordinates);
        // console.log("Retrieved DATA: " + JSON.stringify(data));
        // console.log("Retrieved DATA: " + JSON.stringify(this.coordinates));
        // console.log("Retrieved DATA: " + JSON.stringify(this.coordinates[1]));

      }
    )
  }


  remove() {
    this.routeCreatorService.routeCreatorRemove();
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
}
