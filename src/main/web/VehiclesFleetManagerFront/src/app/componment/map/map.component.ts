import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import * as L from 'leaflet';
import {
  Map,
  latLng,
  Control,
  Layer,
  MapOptions,
  tileLayer,
  ZoomAnimEvent,
  LeafletEvent,
  Icon,
  Marker,
  icon
} from "leaflet";
import "leaflet/dist/leaflet.css";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";
import "leaflet-routing-machine";
import "leaflet-mouse-position";
import {style} from "@angular/animations";
import {SetRouteComponent} from "../set-route/set-route.component";
import {EventEmitterService} from "../../service/event-emitter.service";
import {Coordinates} from "../../model/coordinates";

@Component({
  selector: 'app-map',
   //templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
  template: `<div class="map-container" leaflet
     [leafletOptions]="options"
     (leafletMapReady)="onMapReady($event)"
     (leafletMapZoomEnd)="onMapZoomEnd($event)"
    ></div>`,

})
export class MapComponent implements OnInit,OnDestroy {



  @Output() map$: EventEmitter<Map> = new EventEmitter;
  @Output() zoom$: EventEmitter<number> = new EventEmitter;
  @Input() options: MapOptions= {
    layers:[tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      opacity: 0.7,
      maxZoom: 19,
      detectRetina: true,
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    })],
    zoom:12,
   // center:latLng(0,0)
    center: latLng(57.74, 11.94)
  };
  public map?: Map;
  public zoom?: number;
  @Input() setRoute: SetRouteComponent | undefined;
  public controls: Control[] = [];



  constructor(private eventEmitterService:EventEmitterService) {
  }

  private defaultIcon: Icon = icon({
    iconUrl: "assets/marker-icon.png",
    shadowUrl: "assets/marker-shadow.png"
  });

  ngOnInit() {
    if (this.map instanceof Map) {
      L.control.mousePosition().addTo(this.map);
    }
     Marker.prototype.options.icon = this.defaultIcon;

    if(this.eventEmitterService.subsVar==undefined){
      this.eventEmitterService.subsVar=this.eventEmitterService.addRouteToMap.subscribe(
        (coords: Coordinates[])=>
        {
          this.addNewRoute(coords);
        }

      )
      this.eventEmitterService.subsVar2=this.eventEmitterService.removeRouteFromMap.subscribe(
        ()=>{
          this.removeAll();
        }
      )
    }

  }



  ngOnDestroy() {
    this.map?.clearAllEventListeners;
    this.map?.remove();
  };

  onMapReady(map: Map) {
    this.map = map;
    this.map$.emit(map);
    this.zoom = map.getZoom();
    this.zoom$.emit(this.zoom);
    console.log("Retrieved DATA: " + JSON.stringify(this.setRoute?.coordinates1));
    // if(this.setRoute!=null) {
    //  this.controls.push(L.Routing.control({
    //
    //     waypoints: [L.latLng(<number>this.setRoute.coordinates1._lat, <number>this.setRoute.coordinates1._lon), L.latLng(57.6792, 11.949)],
    //     routeWhileDragging: true,
    //     showAlternatives: true,
    //     altLineOptions: {
    //       styles: [
    //         {color: '#646464', opacity: 0.9, weight: 5},
    //         {color: '#CECECE', opacity: 1, weight: 3},
    //       ], extendToWaypoints: false,
    //       missingRouteTolerance: 0
    //     },
    //     fitSelectedRoutes: true
    //
    //   }).addTo(map));
    //
    // }
    // L.Routing.control({
    //   waypoints: [L.latLng(57.50, 11.94), L.latLng(57.40, 11.949)],
    //   routeWhileDragging: true,
    //   lineOptions: {styles: [
    //       {color: 'white', opacity: 0.9, weight: 9},
    //       {color: '#FC8428', opacity: 1, weight: 3},
    //     ],extendToWaypoints:false,
    //     missingRouteTolerance:0},
    //   fitSelectedRoutes:true
    //
    // }).addTo(map);

    L.control.mousePosition().addTo(this.map);
  }

  addNewRoute(coords: Coordinates[]) {
    if(this.map!=undefined) {
      console.log("Lon: " + JSON.stringify(coords[0]._lon));
      if (coords != undefined) {
        this.controls.push(L.Routing.control({

          waypoints: [L.latLng(<number>coords[0].lat, <number>coords[0].lon),
            L.latLng(<number>coords[1].lat, <number>coords[1].lon)],
          routeWhileDragging: true,
          showAlternatives: true,
          altLineOptions: {
            styles: [
              {color: '#646464', opacity: 0.9, weight: 5},
              {color: '#CECECE', opacity: 1, weight: 3},
            ], extendToWaypoints: false,
            missingRouteTolerance: 0
          },
          fitSelectedRoutes: true

        }).addTo(this.map));
      }
    }
  }

   removeAll

   ()
   {
     this.controls.forEach(e=>{
       this.map?.removeControl(e);
     }
   )
   }

  onMapZoomEnd(e: LeafletEvent) {
    this.zoom = e.target.getZoom();
    this.zoom$.emit(this.zoom);
  }
}
