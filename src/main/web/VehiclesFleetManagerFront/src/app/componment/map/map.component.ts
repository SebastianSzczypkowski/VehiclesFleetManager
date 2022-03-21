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

@Component({
  selector: 'app-map',
  // templateUrl: './map.component.html',
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



  constructor() {
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
    L.Routing.control({
      waypoints: [L.latLng(57.74, 11.94), L.latLng(57.6792, 11.949)],
      routeWhileDragging: true
    }).addTo(map);
    L.control.mousePosition().addTo(this.map);
  }

  onMapZoomEnd(e: LeafletEvent) {
    this.zoom = e.target.getZoom();
    this.zoom$.emit(this.zoom);
  }
}
