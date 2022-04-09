import {Component, Input, OnInit} from '@angular/core';
import {EventEmitterService} from "../../../service/event-emitter.service";
import {SetRouteComponent} from "../set-route.component";
import {MatDialogRef} from "@angular/material/dialog";
import {Coordinates} from "../../../model/coordinates";

@Component({
  selector: 'app-road-info',
  templateUrl: './road-info.component.html',
  styleUrls: ['./road-info.component.css']
})
export class RoadInfoComponent implements OnInit {

  @Input() setRoute: SetRouteComponent | undefined;

  constructor(public dialogRef: MatDialogRef<RoadInfoComponent>,
  private eventEmitterService: EventEmitterService) { }

  coordinates: Coordinates =new Coordinates();

  ngOnInit(): void {

    if(this.eventEmitterService.subsRoadInfo==undefined){
      this.eventEmitterService.subsRoadInfo=this.eventEmitterService.infoRoute.subscribe(
        (coordinates:Coordinates)=>
      {
        this.coordinates=coordinates;
      }
      )
    }
  }


    closeModal() {
      this.dialogRef.close();
    }

}
