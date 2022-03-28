import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './componment/map/map.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatCardModule} from "@angular/material/card";
import {MatSelectModule} from "@angular/material/select";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import { SetRouteComponent } from './componment/set-route/set-route.component';
import {HttpClientModule} from "@angular/common/http";
import {EventEmitterService} from "./service/event-emitter.service";


@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    SetRouteComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatToolbarModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatInputModule,
    LeafletModule,
    HttpClientModule


  ],
  providers: [EventEmitterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
