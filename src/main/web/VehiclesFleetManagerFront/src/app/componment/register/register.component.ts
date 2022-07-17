import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form:any={};
  registerForm!: FormGroup;
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  constructor(private authService:AuthService,private formBuilder:FormBuilder,private router:Router) { }

  ngOnInit(): void {
    this.registerForm=this.formBuilder.group(
      {
        user:this.formBuilder.group(
          {
            name:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(30)]),
            surname:new FormControl('',[Validators.required,Validators.minLength(2),Validators.maxLength(30)]),
            email:new FormControl('',[Validators.required, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
            password:new FormControl('',[Validators.required,Validators.minLength(6),Validators.maxLength(45),
            Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$')]),

          }
        )
      }
    )
  }
  addUser()
  {
    this.authService.register(this.formBuilder).subscribe(
      data=>{
        console.log(data);
      }
    )
  }

  onSubmit():void{

    this.authService.register(this.form).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

}
