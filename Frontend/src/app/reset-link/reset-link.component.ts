import { PasswordSpecsComponent } from './../password-specs/password-specs.component';
import { ResetPasswordService } from './../service/reset-password.service';
import { ValidateTokenService } from './../service/validate-token.service';
import { ApiResponseComponent } from './../api-response/api-response.component';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';
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
    private validateToken: ValidateTokenService,
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

    this.validateToken.tokenVerification(data).subscribe(
      (data1) => {
        this.validToken=true
         console.log(data1);
        
       },
       (error)=>{
      this.validToken=false;
       }
     );
    
  }

  responseDialog() {
    const dialogRef = this.dialog.open(ApiResponseComponent, {
      height: '180px',
      width: '400px',
      data:{data:this.msg}
    });
  }

  openDialog() {
    const dialogRef = this.dialog.open(PasswordSpecsComponent, {
      height: '380px',
      width: '400px',
    });
  }

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
