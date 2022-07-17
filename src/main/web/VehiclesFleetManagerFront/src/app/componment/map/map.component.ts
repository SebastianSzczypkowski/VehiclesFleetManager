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
import {MapService} from "./map-service/map.service";
import {RouteCreatorService} from "../route-creator/service/route-creator.service";
import {Road} from "../../model/road";
import {ToastrService} from "ngx-toastr";
import {startExpression} from "@igniteui/material-icons-extended";

@Component({
  selector: 'app-map',
   //templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
  template: `
    <button mat-raised-button style="background-color: var(--right-color) ;margin-left: 10vh;margin-top: 2vh" aria-label="add" (click)="getLocation()" >
      Zlokalizuj mnie
    </button>
<p></p>
    <mat-form-field appearance="fill" style="margin-left: 10vh">
      <mat-label>Szukaj</mat-label>
      <input matInput placeholder="Szukaj"    >
    </mat-form-field>
    <p></p>
    <mat-form-field appearance="fill" style="margin-left: 10vh">
      <mat-label>Wyszukaj wiele</mat-label>
      <input matInput placeholder="Wyszukaj wiele"   >
    </mat-form-field>
    <div class="map-container" leaflet
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
  public map!: Map;
  public zoom?: number;
  @Input() setRoute: SetRouteComponent | undefined;
  public controls: Control[] = [];
  roads:Road[]=[];
  coordsDB: Coordinates[]=[];


// <button mat-raised-button style="background-color: var(--right-color) ;margin-left: 10vh;margin-top: 2vh" aria-label="add" (click)="getLocation()" >
//     Zlokalizuj mnie
//   </button>

  constructor(private eventEmitterService:EventEmitterService,private mapService:MapService,
              private routeService:RouteCreatorService,private toaster:ToastrService) {
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
    this.routeService.getAll().subscribe(
      data=>
      {
        console.log(data)
        this.roads=data;
      },
      error => {
        this.toaster.error("Nie udało się pobrać tras")
      }
    )
    this.roads.forEach(e=>{
      console.log(e);

      this.controls.push(L.Routing.control({
        waypoints: [L.latLng(<number>e.start._lat, <number>e.start._lon),
          L.latLng(<number>e.end._lat, <number>e.end._lon)],
        routeWhileDragging: true,
        showAlternatives: true,
        lineOptions: {styles: [
            {color: 'white', opacity: 0.9, weight: 9},
            {color: 'orange', opacity: 1, weight: 3},
          ], extendToWaypoints: false,
          missingRouteTolerance: 0},
        altLineOptions: {
          styles: [
            {color: '#646464', opacity: 0.9, weight: 5},
            {color: '#CECECE', opacity: 1, weight: 3},
          ], extendToWaypoints: false,
          missingRouteTolerance: 0
        },
        fitSelectedRoutes: true

      }).addTo(this.map));
    })


  }

  getLocation()
  {
    if(!navigator.geolocation){
      console.log('location is not supported');
    }
    navigator.geolocation.getCurrentPosition((position) => {
      const coords = position.coords;
      const latLong = [coords.latitude, coords.longitude];
      console.log(
        `lat: ${position.coords.latitude}, lon: ${position.coords.longitude}`
      );
      this.mapService.setlat(position.coords.latitude);
      this.mapService.setlon(position.coords.longitude);

      if(this.map!=undefined) {

        var marker =L.marker(([coords.latitude,coords.longitude])).addTo(this.map)
        marker.bindPopup('<b>Twoja pozycja</b>').openPopup();
      }
    })
  }
  watchPosition() {
    let desLat = 0;
    let desLon = 0;
    let id = navigator.geolocation.watchPosition(
      (position) => {
        console.log(
          `lat: ${position.coords.latitude}, lon: ${position.coords.longitude}`
        );
        if (position.coords.latitude === desLat) {
          navigator.geolocation.clearWatch(id);
        }
      },
      (err) => {
        console.log(err);
      },
      {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0,
      }
    );
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


    if(this.setRoute!=null) {
     this.controls.push(L.Routing.control({

        waypoints: [L.latLng(50.2137321, 19.00588775660632), L.latLng(52.2319581, 21.0067249)],
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

      }).addTo(map));

    }
    L.Routing.control({
      waypoints: [L.latLng(50.2137321, 19.00588775660632), L.latLng(52.2319581, 21.0067249)],
      routeWhileDragging: true,
      lineOptions: {styles: [
          {color: 'white', opacity: 0.9, weight: 9},
          {color: '#FC8428', opacity: 1, weight: 3},
        ],extendToWaypoints:false,
        missingRouteTolerance:0},
      fitSelectedRoutes:true

    }).addTo(map);
    L.Routing.control({
      waypoints: [L.latLng(50.4445194, 18.8554757), L.latLng(52.5186925, 13.3996024)],
      routeWhileDragging: true,
      lineOptions: {styles: [
          {color: 'white', opacity: 0.9, weight: 9},
          {color: 'green', opacity: 1, weight: 3},
        ],extendToWaypoints:false,
        missingRouteTolerance:0},
      fitSelectedRoutes:true

    }).addTo(map);

    L.control.mousePosition().addTo(this.map);
  }

  addNewRoute(coords: Coordinates[]) {

    if(this.map!=undefined) {


      if (coords != undefined) {

        this.controls.push(L.Routing.control({

          waypoints: [L.latLng(<number>coords[0].lat, <number>coords[0].lon),
            L.latLng(<number>coords[1].lat, <number>coords[1].lon)],
          routeWhileDragging: true,
          showAlternatives: true,
          lineOptions: {styles: [
                    {color: 'white', opacity: 0.9, weight: 9},
                    {color: coords[0].color, opacity: 1, weight: 3},
                  ], extendToWaypoints: false,
            missingRouteTolerance: 0},
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

   removeAll()
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
