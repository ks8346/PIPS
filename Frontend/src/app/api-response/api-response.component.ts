import { DialogData } from './../login/login.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-api-response',
  templateUrl: './api-response.component.html',
  styleUrls: ['./api-response.component.css']
})
export class ApiResponseComponent {
  msg:any
  islogin:boolean=true;
  message:string;
  constructor(
    public dialogRef: MatDialogRef<ApiResponseComponent>,
    @Inject(MAT_DIALOG_DATA) public data) {
      this.msg=data.data
      
    }
 
  onNoClick(): void {
    
    this.dialogRef.close();
  }
}
