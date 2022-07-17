import { Component, OnInit } from '@angular/core';
import {MapService} from "../map-service/map.service";

declare const L: any;
@Component({
  selector: 'app-map-options',
  templateUrl: './map-options.component.html',
  styleUrls: ['./map-options.component.css']
})
export class MapOptionsComponent implements OnInit {

  constructor(private mapService:MapService) { }

  ngOnInit(): void {

  }




  // watchPosition() {
  //   let desLat = 0;
  //   let desLon = 0;
  //   let id = navigator.geolocation.watchPosition(
  //     (position) => {
  //       console.log(
  //         `lat: ${position.coords.latitude}, lon: ${position.coords.longitude}`
  //       );
  //       if (position.coords.latitude === desLat) {
  //         navigator.geolocation.clearWatch(id);
  //       }
  //     },
  //     (err) => {
  //       console.log(err);
  //     },
  //     {
  //       enableHighAccuracy: true,
  //       timeout: 5000,
  //       maximumAge: 0,
  //     }
  //   );
  // }
}
