import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {UserComponent} from "../user.component";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  hide: boolean = false;

  userDeatils!:FormGroup;
  constructor(public dialogRef:MatDialogRef<UserComponent>,private toaster:ToastrService,
              private _formBuilder: FormBuilder,private userService:UserService) { }

  ngOnInit(): void {
    this.userDeatils=this._formBuilder.group({
      name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      login:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      email:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      password:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(45)]),
      roles:[],


    })
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
