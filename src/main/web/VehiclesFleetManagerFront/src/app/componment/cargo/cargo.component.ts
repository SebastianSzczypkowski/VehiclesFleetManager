import { Component, OnInit } from '@angular/core';
import {Driver} from "../../model/driver";
import {CargoService} from "./service/cargo.service";
import {Cargo} from "../../model/cargo";

@Component({
  selector: 'app-cargo',
  templateUrl: './cargo.component.html',
  styleUrls: ['./cargo.component.css']
})
export class CargoComponent implements OnInit {

  dataSource:Cargo[]=[];
  displayedColumns: string[] = ['position', 'name', 'description', 'type','specialRemarks','delivered','assigned'];
  constructor(private cargoService:CargoService) { }

  ngOnInit(): void {
    this.cargoService.getAll().subscribe(
      data=>{
        this.dataSource=data;
      })
  }

}
