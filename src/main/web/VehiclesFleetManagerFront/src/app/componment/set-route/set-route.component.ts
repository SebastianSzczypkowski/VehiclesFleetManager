import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MapService} from "../../service/map.service";
import {Coordinates} from "../../model/coordinates";
import {newArray} from "@angular/compiler/src/util";
import {MapComponent} from "../map/map.component";
import {EventEmitterService} from "../../service/event-emitter.service";

@Component({
  selector: 'app-set-route',
  templateUrl: './set-route.component.html',
  styleUrls: ['./set-route.component.css']
})
export class SetRouteComponent implements OnInit {

  coordinates1: Coordinates = new Coordinates;
  coordinates2: Coordinates = new Coordinates;
  coordinates: Coordinates[] = [];

  roadSaveAnswer: string | undefined;
  form:any={};
  setRouteForm!: FormGroup;
  // startCordX: string;
  // startCordY: string;
  // endCordX: string;
  // endCordY: string;


  constructor(private formBuilder:FormBuilder,private router:Router,private mapService:MapService,private eventEmitterService: EventEmitterService) { }

  ngOnInit(): void {
    this.setRouteForm=this.formBuilder.group(
      {

            start:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
            end:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),

      }
    )
  }


  onSubmit() {


    this.mapService.getCoordinates(this.setRouteForm.get('start')?.value,this.setRouteForm.get('end')?.value).subscribe(

      data=>{
        this.coordinates=data;
        this.coordinates1=this.coordinates[0];
        this.coordinates2=this.coordinates[1];
        // // @ts-ignore
        // this.map.addNewRoute();
        this.eventEmitterService.onAddRouteClick(this.coordinates);
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


  }

  remove() {
    this.eventEmitterService.onRemoveRouteClick();
  }
}
