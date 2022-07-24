import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ToastrService} from "ngx-toastr";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DriverComponent} from "../driver/driver.component";

@Component({
  selector: 'app-email-sender',
  templateUrl: './email-sender.component.html',
  styleUrls: ['./email-sender.component.css']
})
export class EmailSenderComponent implements OnInit {

  fileToUpload: File | null = null;
  email!:FormGroup;
  imagePath!:string;
  imgURL: any;
  message!: string;
  constructor(private _formBuilder: FormBuilder,private toaster:ToastrService,public matDialog:MatDialog,
              public dialogRef:MatDialogRef<DriverComponent>,) { }

  ngOnInit(): void {

    this.email=this._formBuilder.group(
      {
        to:[],
        title:[],
        content:[],
        attachment:[],

      }
    )
  }


  closeModal() {
    this.dialogRef.close();
  }

  onSubmit() {

  }

  handleFileInput(files: any) {


    this.fileToUpload = files.item(0);
    if (files.length === 0)
      return;

    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = "Only images are supported.";
      return;
    }

    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]);
    reader.onload = (_event) => {
      this.imgURL = reader.result;
    }
    this.toaster.success("Dodano załącznik");
    this.email.patchValue({attachment:this.fileToUpload});
  }
}
