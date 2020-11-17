import { PasswordSpecsComponent } from './../password-specs/password-specs.component';
import { ResetPasswordService } from './../service/reset-password.service';
import { ApiResponseComponent } from './../api-response/api-response.component';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmPasswordValidator } from '../confirmPassword.Validator';


@Component({
  selector: 'app-reset-link',
  templateUrl: './reset-link.component.html',
  styleUrls: ['./reset-link.component.css']
})
export class ResetLinkComponent implements OnInit {

  resetLinkForm: FormGroup
  validToken:boolean;
  msg:string="reset sucessfull!"
  token
  hide=true;
  hide1=true;
  

  constructor(public dialog: MatDialog,
    private activatedRoute:ActivatedRoute,
    private resetPassword: ResetPasswordService,
    private formBuilder: FormBuilder,) { }

  ngOnInit(): void {
    this.resetLinkForm = this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20), Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')]], 
      confirmPassword: ['', Validators.required]
  },
  {
    validator: ConfirmPasswordValidator("password", "confirmPassword")
  });
   

    this.token=this.activatedRoute.snapshot.paramMap.get('token')
    var data={"data1":{"id":this.token}}

    this.resetPassword.tokenVerification(data).subscribe(
      (data1) => {
        this.validToken=true
         console.log(data1);
        
       },
       (error)=>{
        if(error.status==202){
          this.validToken=true;
        }
        else if(error.status==406){
          this.validToken=false;
        }
        else{
          alert("Some error has occured! please try again later.")
        }
      
       }
     );
    
  }
/**
 * This Method opens a Dialog Box, after you submit the new password details, showing the response saying if the password reset was successful or not.
 */
  responseDialog() {
    const dialogRef = this.dialog.open(ApiResponseComponent, {
      height: '180px',
      width: '400px',
      data:{data:this.msg}
    });
  }
/** ask Priyanka*/
  openDialog() {
    const dialogRef = this.dialog.open(PasswordSpecsComponent, {
      height: '380px',
      width: '400px',
    });
  }
/** 
 * This method calls the Reset Link API that makes a PUT request to update the existing credentials saved by the user.
*/
  onSubmit(){
    this.responseDialog()
    var data ={"data1":{
      "id":this.token,
      "password":this.resetLinkForm.controls.password.value
    }}
    this.resetPassword.resetPassword(data).subscribe(
      (data1) => {
         console.log(data1);
         this.msg="Your password has been reset sucessfully!"
        
       },
       (error)=>{
         
      
       }
     );
  }

}
