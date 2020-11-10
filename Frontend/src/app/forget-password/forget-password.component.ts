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

// export class MyErrorStateMatcher implements ErrorStateMatcher {
//   isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
//     const isSubmitted = form && form.submitted;
//     return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
//   }
// }

export class ForgetPasswordComponent implements OnInit {
forgetPasswordForm:FormGroup;
// matcher=new MyErrorStateMatcher

data: string;
error: string;

  constructor(private sendResetLink: SendResetLinkService ) {
   
   }

  ngOnInit(): void {
    this.forgetPasswordForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      
    });
    
  }

  onSubmit(){
    var data={"data1":{"mail":this.forgetPasswordForm.value.email}}
    console.log(data)
    this.sendResetLink.resetLink(data).subscribe(
      (data1) => {
         console.log(data1);
         this.error="Reset link has been send to your email."
        
       },
       (error)=>{
         this.error="Email id doesnot exists. Please try again!"
      
       }
     );

  }

}
