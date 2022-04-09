import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {icon, Icon, latLng, LeafletEvent, Map, MapOptions, Marker, tileLayer} from "leaflet";
import * as L from "leaflet";

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

  constructor() { }

  private defaultIcon: Icon = icon({
    iconUrl: "assets/marker-icon.png",
    shadowUrl: "assets/marker-shadow.png"
  });
  ngOnInit(): void {
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

    L.control.mousePosition().addTo(this.map);
  }
  onMapZoomEnd(e: LeafletEvent) {
    this.zoom = e.target.getZoom();
    this.zoom$.emit(this.zoom);
  }
}
