import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MapComponent} from "./componment/map/map.component";
import {SetRouteComponent} from "./componment/set-route/set-route.component";

const routes: Routes = [
  {path:'map',component:MapComponent},
  {path:'setRoute',component:SetRouteComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
