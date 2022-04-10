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
import {MatTableDataSource} from "@angular/material/table";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {MatSort, Sort} from "@angular/material/sort";

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

export class RouteCreatorComponent implements OnInit,AfterViewInit {


  firstFormGroup!: FormGroup;
  secondFormGroup!: FormGroup ;
  defaultElevation = 2;
  raisedElevation = 8;
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  constructor(private _formBuilder: FormBuilder,private _liveAnnouncer: LiveAnnouncer) {

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
  }

  @ViewChild(MatSort) sort!: MatSort;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
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


}
