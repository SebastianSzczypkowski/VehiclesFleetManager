import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {UserComponent} from "../user.component";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {UserService} from "../service/user.service";
import {DriverEmmiterService} from "../../driver/service/driver-emmiter.service";
import {UserEmmiterService} from "../service/user-emmiter.service";
import {Driver} from "../../../model/driver";
import {User} from "../../../model/user";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  hide: boolean = false;

  userDeatils!:FormGroup;
  userData!: User;
  userId!:number;
  constructor(public dialogRef:MatDialogRef<UserComponent>,private toaster:ToastrService,
              private _formBuilder: FormBuilder,private userService:UserService, private userEmmiter:UserEmmiterService) { }

  ngOnInit(): void {
    this.userDeatils=this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      login:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      email:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      password:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      roles:[],
    })

    this.userId=this.userEmmiter.getuserID();
    this.userData=this.userEmmiter.getuser();
    this.userDeatils.patchValue({name:this.userEmmiter.getuser().name});
    this.userDeatils.patchValue({login:this.userEmmiter.getuser().login});
    this.userDeatils.patchValue({email:this.userEmmiter.getuser().email});
    this.userDeatils.patchValue({password:this.userEmmiter.getuser().password});
    this.userDeatils.patchValue({roles:this.userEmmiter.getuser().roles});

  }

  closeModal() {
    this.dialogRef.close();
  }

  onSubmit() {
    this.userService.add(this.userDeatils.getRawValue()).subscribe(
      data=>{
        this.toaster.success("Dodano użytkownika");
      },
      err=>{
        this.toaster.error("Nie udało się dodać użytkownika");
      }
    );
  }
}
