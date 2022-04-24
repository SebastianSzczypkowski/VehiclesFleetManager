import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {VehicleService} from "../../vehicle/service/vehicle.service";
import {VehicleInspectionService} from "../service/vehicle-inspection.service";
import {Vehicle} from "../../../model/vehicle";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {PeriodicElement} from "../../route-creator/route-creator.component";

@Component({
  selector: 'app-vehicle-inspection-creator',
  templateUrl: './vehicle-inspection-creator.component.html',
  styleUrls: ['./vehicle-inspection-creator.component.css']
})
export class VehicleInspectionCreatorComponent implements OnInit,AfterViewInit {


  vehicleInspectionForm!:FormGroup;
  vehicles:Vehicle[]=[];
  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  vehicleSelected!:Vehicle;
  dataSource = new MatTableDataSource<PeriodicElement>();
  vehicleColumns: string[] = ['id', 'nazwa', 'vin', 'rejestracja'];
  clickedRow = new Set<PeriodicElement>();
  @ViewChild(MatPaginator) paginator!: MatPaginator

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private _formBuilder: FormBuilder,private vehicleService:VehicleService,
              private vehicleinspectionService:VehicleInspectionService) { }

  ngOnInit(): void {
    this.vehicleInspectionForm=this._formBuilder.group({
      carRepairShopName:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      dataWykonania:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      description:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      performedBy:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      idVehicle:new FormControl(''),
    })
    this.vehicleService.getAllPage(this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.vehicles=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;


      })
  }

  onRawClick(element: Vehicle)
  {

    this.vehicleSelected=element;
    this.vehicleInspectionForm.patchValue({idVehicle:element.id});
    // this.vehicles.forEach(e=>{
    //   if(element==e)
    //     this.addClass=true;
    // })
  }
  onSubmit() {
    this.vehicleinspectionService.add(this.vehicleInspectionForm.getRawValue()).subscribe(
    )
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
}
