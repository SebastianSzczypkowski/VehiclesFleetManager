import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MapComponent} from "./componment/map/map.component";
import {SetRouteComponent} from "./componment/set-route/set-route.component";
import {RouteCreatorComponent} from "./componment/route-creator/route-creator.component";
import {HomeComponent} from "./componment/home/home.component";
import {LoginComponent} from "./componment/login/login.component";

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'login',component:LoginComponent},
  {path:'map',component:MapComponent},
  {path:'setRoute',component:SetRouteComponent},
  {path:'routeCreator',component:RouteCreatorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
