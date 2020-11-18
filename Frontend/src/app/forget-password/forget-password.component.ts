import { ResetPasswordService } from './../service/reset-password.service';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { Component, OnInit } from '@angular/core';

/**This component enables user to reset password via valid email id */
@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})

export class ForgetPasswordComponent implements OnInit {

/**@ignore */
forgetPasswordForm:FormGroup;

/**@ignore */
data: string;

/**@ignore */
error: string;

/**@ignore */
isSubmit : boolean=true;

  /**@ignore */
  constructor(
    /**@ignore */
    private resetLinkService: ResetPasswordService) {
   
   }

   /**This method creates a form with a input field to enter email of user */
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
    this.resetLinkService.resetLink(data).subscribe(
      (data1) => {
         console.log(data1);
         this.isSubmit = false;
         this.error="Reset link has been send to your email."
        
       },
       (error)=>{
        if(error.status==200){
          this.error="Reset link has been send to your email."
          
        }
        else if(error.status==406){
          this.error="Email id doesnot exists. Please try again!"
        }
        else{
          alert("Some error has occured! please try again later.")
        }
       }
     );

  }

}
