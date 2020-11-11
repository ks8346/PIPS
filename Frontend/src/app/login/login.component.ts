import { SocialMediaAuthService } from './../service/social-media-auth.service';
import { ForgetPasswordComponent } from './../forget-password/forget-password.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Component, OnInit ,Inject} from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray, FormGroupDirective, NgForm } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import {ErrorStateMatcher} from '@angular/material/core';
import { ReactiveFormsModule } from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatDialog} from '@angular/material/dialog';
import { MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MatDialogRef} from '@angular/material/dialog';
import { UserLoginService } from '../service/user-login.service';
import { SocialAuthService } from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { SocialUser } from "angularx-social-login";

export interface DialogData {
  email: string;
  
}


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email:string;
  loginForm: FormGroup;
  invalidLogin = false
  loginSuccess=false;
  successMessage: string;
  errorMessage = "Invalid Credentials"
  userData;
  socialData;
  private user: SocialUser;
  private loggedIn: boolean;

  constructor(private router: Router,
   private socialMediaAuth:SocialMediaAuthService,
    public dialog: MatDialog,
    public loginService:UserLoginService,
    private authService: SocialAuthService) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });
  }
  matcher = new MyErrorStateMatcher()
  ngOnInit(): void {
    // this.authService.authState.subscribe((user) => {
    //   this.user = user;
    //   this.loggedIn = (user != null);
    //   });
  }

  socialMedia(){
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.socialData={"data1":{"name":this.user["name"],"email":this.user["email"]}}
      console.log(this.user["name"])
      console.log(this.user["email"])
      this.loggedIn = (user != null);
      });
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
    // console.log(this.user)
    this.socialMediaAuth.socialMedia(this.socialData).subscribe(
      (data1) => {
      console.log(data1);
      // data1={"email":"kshitij.goel@gmail.com","name":"Kshitij","team":"default"} -----> reroute to team page ---> Reroute to landing page
      // data1={"email":"kshitij.goel@gmail.com","name":"Kshitij","team":"Sparks"} ----> Reroute to landing page
     
    },
    (error)=>{
    }
  );
  }

  handleLogin() {
    if(this.loginForm.valid) {

      this.loginForm.get('email').value;

        //Redirect
        // this.loginService.doLogin(this.loginForm.get('email').value,this.loginForm.get('password').value;).subscribe(
        //   data1 => {
        //     console.log(data1);
        //    this.message=data1
        //    this.responseDialog(this.message)
        //    this.router.navigate("/welcome") ;
        //    this.loading=false;
        //  });

         this.loginService.doLogin(this.loginForm.get('email').value,this.loginForm.get('password').value).subscribe((result)=> {
        
          this.userData=sessionStorage.getItem('authenticatedUser')
          console.log("results",this.userData, this.userData)
          
          this.invalidLogin = false;
          this.loginSuccess = true;
          sessionStorage.setItem('data', JSON.stringify(result));
          console.log("data", result)
          this.successMessage = 'Login Successful.';
          this.router.navigate(['/welcome']);
        }, (error) => {
          console.log(error)
          this.invalidLogin = true;
          this.loginSuccess = false;
        });

    }
  }

  openDialog(): void{
    const dialogRef = this.dialog.open(ForgetPasswordComponent, {
      width: '500px',
     
    });

  //   dialogRef.afterClosed().subscribe(result => {
  //     console.log('The dialog was closed');
  //     this.email = result;
  //   });
  // }
  }
}

// @Component({
//   selector: 'forgot-password',
//   templateUrl: 'forgotPassword.component.html',
// })
// export class ForgotPasswordDialog {

//   constructor(
//     public dialogRef: MatDialogRef<ForgotPasswordDialog>,
//     @Inject(MAT_DIALOG_DATA) public data: DialogData) {}
//    // matcher = new MyErrorStateMatcher()

//   onNoClick(): void {
//     this.dialogRef.close();
//   }

// }

