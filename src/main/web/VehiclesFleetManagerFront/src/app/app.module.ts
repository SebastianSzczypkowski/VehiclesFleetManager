import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './componment/map/map.component';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatCardModule } from "@angular/material/card";
import { MatSelectModule } from "@angular/material/select";
import { MatIconModule } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatButtonModule } from "@angular/material/button";
import { MatInputModule } from "@angular/material/input";
import { LeafletModule } from "@asymmetrik/ngx-leaflet";
import { SetRouteComponent } from './componment/set-route/set-route.component';
import { HttpClientModule } from "@angular/common/http";
import { EventEmitterService } from "./service/event-emitter.service";
import {MatListModule} from "@angular/material/list";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {NgxColorsModule} from "ngx-colors";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import { RoadInfoComponent } from './componment/set-route/road-info/road-info.component';
import { RouteCreatorComponent } from './componment/route-creator/route-creator.component';
import {MatStepperModule} from "@angular/material/stepper";
import { CreatorMapComponent } from './componment/route-creator/creator-map/creator-map.component';
import {MaterialElevationDirective} from "./componment/route-creator/MaterialElevationDirective";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatTableModule} from "@angular/material/table";
import { HomeComponent } from './componment/home/home.component';
import { LoginComponent } from './componment/login/login.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {FontAwesomeTestingModule} from "@fortawesome/angular-fontawesome/testing";

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    SetRouteComponent,
    RoadInfoComponent,
    RouteCreatorComponent,
    CreatorMapComponent,
    MaterialElevationDirective,
    HomeComponent,
    LoginComponent,

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
    HttpClientModule,
    MatListModule,
    MatSlideToggleModule,
    NgxColorsModule,
    MatDialogModule,
    MatStepperModule,
    MatTooltipModule,
    MatTableModule,
    FontAwesomeModule,
    FontAwesomeTestingModule



  ],
  providers: [EventEmitterService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
