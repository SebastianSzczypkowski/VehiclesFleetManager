import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DriverService} from "../service/driver.service";
import {Entitlementstotransport} from "../../../model/entitlementstotransport";
import {TypofpermissionServiceService} from "../../../service/typofpermission-service.service";
import {PageEvent} from "@angular/material/paginator";
import {Typeofpermission} from "../../../model/typeofpermission";
import {Observable, startWith,map} from "rxjs";
import {MatTable} from "@angular/material/table";
import {MatStepper} from "@angular/material/stepper";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-driver-creator',
  templateUrl: './driver-creator.component.html',
  styleUrls: ['./driver-creator.component.css']
})
export class DriverCreatorComponent implements OnInit {


  fileToUpload: File | null = null;
  srcResult!: File;
  permissions = new FormControl();
  pageEvent: PageEvent = new PageEvent;
  pageIndex=0;
  pageSize=10;
  length!:number;
  typeofpermissions:Typeofpermission[]=[];
  entitlements:Entitlementstotransport[]=[];
  entitle:Entitlementstotransport =new Entitlementstotransport() ;
  driverForm!:FormGroup;
  permissionForm!:FormGroup;
  filteredOptions!: Observable<Typeofpermission[]>;

  imagePath!:string;
  imgURL: any;
  message!: string;

  entitlementsColumns: string[] = [ 'documentTyp', 'expiryDate'];
  @ViewChild(MatTable) table!: MatTable<any>;
  constructor(private _formBuilder: FormBuilder,private driverService:DriverService,
              private typOfPermissionsService:TypofpermissionServiceService,
              private changeDetectorRefs: ChangeDetectorRef,private toaster:ToastrService) { }

  ngOnInit(): void {
    this.filteredOptions = this.permissions.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name => (name ? this._filter(name) : this.typeofpermissions.slice())),
    );

    this.driverForm = this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      surname:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      pesel:new FormControl(''),
      dateofbirth:[new Date()],
      address:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      file: [],
      entitlement: [],

    });
    this.permissionForm=this._formBuilder.group(
      {
        documentTyp: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(45)]),
        expiryDate:new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(45)]),
      }
    )

    this.typOfPermissionsService.getAll().subscribe(
      data=>{
        console.log(data);
        this.typeofpermissions=data;
        console.log(this.typeofpermissions);

      }
    )
    this.refresh();

  }
  displayFn(type: Typeofpermission): string {
    return type && type.name ? type.name : '';
  }
  private _filter(name: string): Typeofpermission[] {
    const filterValue = name.toLowerCase();

    return this.typeofpermissions.filter(typeofpermissions => typeofpermissions.name.toLowerCase().includes(filterValue));
  }

  onSubmit() {
    this.driverService.add(this.driverForm.getRawValue()).subscribe(
      data=>{
        this.toaster.success("Dodano kierowce");
      },
      err =>{
        this.toaster.error("Nie udało się dodać kierowcy");
      }
    );

  }

  reset()
  {
    this.driverForm.reset();
  }

  refresh()
  {
    this.changeDetectorRefs.detectChanges();

  }

  onSubmitPermission() {

    //TODO stworzyć obiek klasy entitletoTransport przypisać wartości z formularza
    //this.entitle.typeofpermission=this.permissionForm.getRawValue().name;
    this.driverForm.patchValue({entitlement:this.entitlements});
    this.entitlements.push(this.permissionForm.getRawValue());
    this.toaster.success("Dodano pozycje");
    this.table.renderRows();
    // this.permissionForm.reset();

  }

  remove(element:Entitlementstotransport) {
    this.entitlements.forEach((value,index)=>
    {
      if(value.id==element.id) {

        this.entitlements = this.entitlements.filter(item=>item!==element);
        this.toaster.info("Usunięto pozycje");
        this.table.renderRows();

      }
    })

  }

  goBack(stepper: MatStepper){
    stepper.previous();
  }

  goForward(stepper: MatStepper){
    stepper.next();
  }

  handleFileInput(files: any) {


    this.fileToUpload = files.item(0);
    if (files.length === 0)
      return;

    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = "Only images are supported.";
      return;
    }

    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURL = reader.result;
    }
    this.toaster.success("Dodano zdjęcie");
    this.driverForm.patchValue({file:this.fileToUpload});
  }




}
