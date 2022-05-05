import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Road} from "../../../model/road";
import {MatDialogRef} from "@angular/material/dialog";
import {RoadEmmiterService} from "../service/road-emmiter.service";
import {RouteCreatorService} from "../service/route-creator.service";

@Component({
  selector: 'app-road-deatils',
  templateUrl: './road-deatils.component.html',
  styleUrls: ['./road-deatils.component.css']
})
export class RoadDeatilsComponent implements OnInit {

  roadDeatils!:FormGroup;
  route!:FormGroup;
  roadData!:Road;
  constructor(private formBuilder: FormBuilder,public dialogRef:MatDialogRef<RoadDeatilsComponent>,
              private roadEmmiter:RoadEmmiterService,private roadService:RouteCreatorService) { }

  ngOnInit(): void {

    this.route=this.formBuilder.group({
      start:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      end:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      color:new FormControl(''),
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
    })


    this.roadDeatils=this.formBuilder.group({
      start:[],
      end:[],
      driver:[],
      cargo:[],
    })
  }

  closeModal() {
    this.dialogRef.close();
  }

}
