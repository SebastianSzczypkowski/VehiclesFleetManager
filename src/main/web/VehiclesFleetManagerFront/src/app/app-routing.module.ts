import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MapComponent} from "./componment/map/map.component";
import {SetRouteComponent} from "./componment/set-route/set-route.component";
import {RouteCreatorComponent} from "./componment/route-creator/route-creator.component";

const routes: Routes = [
  {path:'map',component:MapComponent},
  {path:'setRoute',component:SetRouteComponent},
  {path:'routeCreator',component:RouteCreatorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
