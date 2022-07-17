import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MapComponent} from "./componment/map/map.component";
import {SetRouteComponent} from "./componment/set-route/set-route.component";
import {RouteCreatorComponent} from "./componment/route-creator/route-creator.component";
import {HomeComponent} from "./componment/home/home.component";
import {LoginComponent} from "./componment/login/login.component";
import {VehicleComponent} from "./componment/vehicle/vehicle.component";
import {CreateVehicleComponent} from "./componment/vehicle/create-vehicle/create-vehicle.component";
import {DriverCreatorComponent} from "./componment/driver/driver-creator/driver-creator.component";
import {DriverComponent} from "./componment/driver/driver.component";
import {VehicleInspectionComponent} from "./componment/vehicle-inspection/vehicle-inspection.component";
import {VehicleInspectionCreatorComponent} from "./componment/vehicle-inspection/vehicle-inspection-creator/vehicle-inspection-creator.component";
import {CargoComponent} from "./componment/cargo/cargo.component";
import {CargoCreatorComponent} from "./componment/cargo/cargo-creator/cargo-creator.component";
import {RoadTabelComponent} from "./componment/route-creator/road-tabel/road-tabel.component";
import {DriverEvaluationComponent} from "./componment/driver-evaluation/driver-evaluation.component";
import {MapOptionsComponent} from "./componment/map/map-options/map-options.component";

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'login',component:LoginComponent},
  {path:'map',component:MapOptionsComponent},
  {path:'setRoute',component:SetRouteComponent},
  {path:'routeCreator',component:RouteCreatorComponent},
  {path:'vehicle',component:VehicleComponent},
  {path:'vehicleCreator',component:CreateVehicleComponent},
  {path:'driverCreator',component:DriverCreatorComponent},
  {path:'driver',component:DriverComponent},
  {path:'vehicleInspection',component:VehicleInspectionComponent},
  {path:'vehicleInspectionCreator',component:VehicleInspectionCreatorComponent},
  {path:'cargo',component:CargoComponent},
  {path:'cargoCreator',component:CargoCreatorComponent},
  {path:'roadTabel',component:RoadTabelComponent},
  {path:'driverEvaluation',component:DriverEvaluationComponent},
  {path:'user',component:UserComponent},
  {path:'register',component:RegisterComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
