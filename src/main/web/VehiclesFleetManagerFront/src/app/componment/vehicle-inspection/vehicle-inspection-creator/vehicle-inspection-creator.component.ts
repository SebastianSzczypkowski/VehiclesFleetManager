import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {VehicleService} from "../../vehicle/service/vehicle.service";
import {VehicleInspectionService} from "../service/vehicle-inspection.service";
import {Vehicle} from "../../../model/vehicle";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {MatStepper} from "@angular/material/stepper";
import {ToastrService} from "ngx-toastr";

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
  searchKeyWord!:string;
  vehicleSelected!:Vehicle;
  dataSource = new MatTableDataSource<Vehicle>();
  vehicleColumns: string[] = ['id', 'nazwa', 'vin', 'rejestracja'];
  clickedRow = new Set<Vehicle>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatTable) table!: MatTable<any>;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  constructor(private _formBuilder: FormBuilder,private vehicleService:VehicleService,
              private vehicleinspectionService:VehicleInspectionService,private toaster:ToastrService) { }

  ngOnInit(): void {
    this.vehicleInspectionForm=this._formBuilder.group({
      carRepairShopName:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      date:[new Date()],
      validityOfTheVehicleInspection:[new Date()],
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
    this.toaster.success("Wybrano pojazd");
    // this.vehicles.forEach(e=>{
    //   if(element==e)
    //     this.addClass=true;
    // })
  }
  onSubmit() {
    this.vehicleinspectionService.add(this.vehicleInspectionForm.getRawValue()).subscribe(
      data=>{
        this.toaster.success("Dodano wpis o inspekcji pojazdu");
      },
      err=>{
        this.toaster.error("Nie udało się dodać naprawy");
      }
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
      },
      err=>{
        this.toaster.error("Nie udało się poprać danych");
      }
    )
    else
      this.vehicleService.getAllPage(0,this.pageSize)
    return event;
  }

  search(event: any) {

    this.vehicleService.getAllPageSearch(event.target.value,this.pageIndex,this.pageSize).subscribe(
      data=>{
        this.vehicles=data.content;
        this.pageIndex=data.number;
        this.pageSize=data.size;
        this.length=data.totalElements;
        this.table.renderRows();
      },err=>{
        this.toaster.error("Nie udało się poprać danych");
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

}
