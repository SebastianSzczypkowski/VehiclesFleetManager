import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MapService} from "../../service/map.service";
import {Coordinates} from "../../model/coordinates";
import {newArray} from "@angular/compiler/src/util";
import {MapComponent} from "../map/map.component";
import {EventEmitterService} from "../../service/event-emitter.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {RoadInfoComponent} from "./road-info/road-info.component";

@Component({
  selector: 'app-set-route',
  templateUrl: './set-route.component.html',
  styleUrls: ['./set-route.component.css']
})
export class SetRouteComponent implements OnInit {


  color: string | undefined;
  coordinates: Coordinates[] = [];
  allCoordinates: Coordinates[] = [];
  roadNames: string[]=[];


  roadSaveAnswer: string | undefined;
  form:any={};
  setRouteForm!: FormGroup;
  public road: string[]=[];




  constructor(private formBuilder:FormBuilder,private router:Router,private mapService:MapService,
              private eventEmitterService: EventEmitterService,public matDialog:MatDialog) { }

  ngOnInit(): void {
    this.setRouteForm=this.formBuilder.group(
      {

            start:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
            end:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
            color:new FormControl(''),
            name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),

      }
    )

  }


  onSubmit() {

    this.mapService.getCoordinates(this.setRouteForm.get('start')?.value,this.setRouteForm.get('end')?.value,
      this.setRouteForm.get('color')?.value,this.setRouteForm.get('name')?.value).subscribe(

      data=>{
        this.coordinates=data;
        // // @ts-ignore
        // this.map.addNewRoute();
        this.eventEmitterService.onAddRouteClick(this.coordinates);
        this.roadNames.push(<string>this.coordinates[0].name);
        // console.log("Retrieved DATA: " + JSON.stringify(data));
        // console.log("Retrieved DATA: " + JSON.stringify(this.coordinates));
        // console.log("Retrieved DATA: " + JSON.stringify(this.coordinates[1]));

      }
    )

    this.mapService.saveRoad(this.coordinates).subscribe(
      data=>
      {
        this.roadSaveAnswer=data;
      }
    )

    this.coordinates.forEach(obj=>{
      this.allCoordinates.push(obj);
    })



  }


  getRoadDetails(name:string)
  {
    const dialogConfig = new MatDialogConfig();
    // The user can't close the dialog by clicking outside its body
    dialogConfig.disableClose = true;
    dialogConfig.id = "road-info-component";
    dialogConfig.height = "350px";
    dialogConfig.width = "600px";
    // https://material.angular.io/components/dialog/overview
    const modalDialog = this.matDialog.open(RoadInfoComponent, dialogConfig);
    this.eventEmitterService.onInfoRouteClick(this.allCoordinates.filter(x=>x.name==name)[0]);
    this.eventEmitterService.infoRoute.emit();
    //onInfoRouteClick
  }

  removeRoad(name:string)
  {

  this.roadNames.forEach((value,index)=>
  {
    if(value==name)
      this.roadNames.slice(index,1);
  })
  }

  remove() {
    this.eventEmitterService.onRemoveRouteClick();
  }

}
