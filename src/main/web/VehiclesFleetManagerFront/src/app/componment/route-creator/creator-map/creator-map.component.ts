import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Control, icon, Icon, latLng, LeafletEvent, Map, MapOptions, Marker, tileLayer} from "leaflet";
import * as L from "leaflet";
import {RouteCreatorService} from "../service/route-creator.service";
import {Coordinates} from "../../../model/coordinates";

@Component({
  selector: 'app-creator-map',
  styleUrls: ['./creator-map.component.css'],
  template: `<div class="map-container" leaflet
     [leafletOptions]="options"
     (leafletMapReady)="onMapReady($event)"
     (leafletMapZoomEnd)="onMapZoomEnd($event)"
    ></div>`,
})
export class CreatorMapComponent implements OnInit,OnDestroy {

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
  public control!: Control;

  constructor(private routeCreatorService:RouteCreatorService) { }

  private defaultIcon: Icon = icon({
    iconUrl: "assets/marker-icon.png",
    shadowUrl: "assets/marker-shadow.png"
  });
  ngOnInit(): void {
    if (this.map instanceof Map) {
      L.control.mousePosition().addTo(this.map);
    }
    Marker.prototype.options.icon = this.defaultIcon;

    if(this.routeCreatorService.subscription==undefined)
    {
      this.routeCreatorService.subscription=this.routeCreatorService.routeCreatorMap.subscribe(
        (coords:Coordinates[])=>
        {
          this.addNewRoute(coords);
        }
      )
      this.routeCreatorService.subscriptionRemove=this.routeCreatorService.routeCreatorMapRemove.subscribe(
        ()=>{
          this.remove();
        }
      )
    }
  }

  remove()
  {
    this.map?.removeControl(this.control);
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

    L.control.mousePosition().addTo(this.map);
  }
  onMapZoomEnd(e: LeafletEvent) {
    this.zoom = e.target.getZoom();
    this.zoom$.emit(this.zoom);
  }
  addNewRoute(coords: Coordinates[]) {
    if(this.map!=undefined) {

      if (coords != undefined) {

        //this.map?.removeControl(this.control);
        this.control= L.Routing.control({

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
          fitSelectedRoutes: true,

        }).addTo(this.map);

      }
    }
  }
}
