import { SendResetLinkService } from './../service/send-reset-link.service';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ErrorStateMatcher } from '@angular/material/core';
import { FormGroup, FormControl, Validators, FormGroupDirective, NgForm } from '@angular/forms';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})

export class ForgetPasswordComponent implements OnInit {
forgetPasswordForm:FormGroup;

data: string;
error: string;
isSubmit : boolean=true;

  constructor(private sendResetLink: SendResetLinkService ) {
   
   }

  ngOnInit(): void {
    this.forgetPasswordForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      
    });
    
  }
/**
 * This method calls the Reset Link Service, which sends a link to the submitted registered Email ID from which the user can reset their password incase forgotten.
 */
  onSubmit(){
    var data={"data1":{"mail":this.forgetPasswordForm.value.email}}
    console.log(data)
    this.sendResetLink.resetLink(data).subscribe(
      (data1) => {
         console.log(data1);
         this.isSubmit = false;
         this.error="Reset link has been send to your email."
        
       },
       (error)=>{
         this.error="Email id doesnot exists. Please try again!"
      
       }
     );

  }

  onOk(){

  }

}
