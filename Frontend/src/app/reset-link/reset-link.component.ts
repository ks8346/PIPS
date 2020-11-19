import { PasswordSpecsComponent } from './../password-specs/password-specs.component';
import { ResetPasswordService } from './../service/reset-password.service';
import { ApiResponseComponent } from './../api-response/api-response.component';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmPasswordValidator } from '../confirmPassword.Validator';

/**This component enables user to reset password */
@Component({
  selector: 'app-reset-link',
  templateUrl: './reset-link.component.html',
  styleUrls: ['./reset-link.component.css']
})
export class ResetLinkComponent implements OnInit {

  /**This variable stores instance of reset password form */
  resetLinkForm: FormGroup

  /**@ignore */
  validToken:boolean;

  /**Message to be displayed on successful password reset */
  msg:string="reset sucessfull!"

  /**@ignore */
  token

  /**@ignore */
  hide=true;

  /**@ignore */
  hide1=true;
  
/**@ignore */
  constructor(
    /**@ignore */
    public dialog: MatDialog,
    /**@ignore */
    private activatedRoute:ActivatedRoute,
    /**@ignore */
    private resetPassword: ResetPasswordService,
    /**@ignore */
    private formBuilder: FormBuilder,) { }

  /**This method creates a form for password reset */
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
/** This method opens a dialog box displaying required specifications for the password field.*/
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
