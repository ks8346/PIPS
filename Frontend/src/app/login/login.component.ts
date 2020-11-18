import { TransferDataService } from './../service/transfer-data.service';
import { SocialMediaAuthService } from './../service/social-media-auth.service';
import { ForgetPasswordComponent } from './../forget-password/forget-password.component';
import { Component, OnInit ,Inject} from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, FormGroupDirective, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import {ErrorStateMatcher} from '@angular/material/core';
import {MatDialog} from '@angular/material/dialog';
import { UserLoginService } from '../service/user-login.service';
import { SocialAuthService } from "angularx-social-login";
import { GoogleLoginProvider } from "angularx-social-login";
import { SocialUser } from "angularx-social-login";


// export interface DialogData {
//   email: string; 
// }


// export class MyErrorStateMatcher implements ErrorStateMatcher {
//   isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
//     const isSubmitted = form && form.submitted;
//     return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
//   }
// }

/**This component enables user to login to application */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  // check:boolean=false
  // email:string;
  /**instance of login form */
  loginForm: FormGroup;

  /**This variable set true when user enters valid login credentials */
  invalidLogin = false

  /**This variable is set true on successful login */
  loginSuccess=false;


  // successMessage: string;

  /**message to be displayed for invalid credentials */
  errorMessage = "Invalid Credentials"

  /**This variable stores user data */
  userData;

  /**this variable stores user data when loggedin via social media */
  socialData;
  // uData

  /**@ignore */
  private user: SocialUser;

  /**Flag to check if user has logged in earlier via social media*/
  private loggedIn: boolean;

  /**@ignore */
  public res

  /**Creates a login form with email amd password fields */
  constructor(
    /**@ignore */
    private router: Router,
    /**@ignore */
    private transferDataService:TransferDataService,
    /**@ignore */
   private socialMediaAuth:SocialMediaAuthService,
   /**@ignore */
    public dialog: MatDialog,
    /**@ignore */
    public loginService:UserLoginService,
    /**@ignore */
    private authService: SocialAuthService,
) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });
  }

  /**@ignore */
  // matcher = new MyErrorStateMatcher()

  /**@ignore */
  ngOnInit(): void {
    
  }
  /**
 * You are notified when user logs in or logs out. You receive a SocialUser object when the user logs in and a null when the user logs out. SocialUser object contains basic user information such as name, email, photo URL, etc. along with the auth_token. You can communicate the auth_token to your server to authenticate the user in server and make API calls from server.
 */
  googleSignIn(){
      this.authService.authState.subscribe((user) => {
        if(user!=null)
        {this.user = user;
        this.loggedIn = (user != null);
        this.socialMedia()
        }});
        if(!this.loggedIn){
          this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)}
  }


  /**
 * This methods calls the Social Media Authentication service to log the user into a session to acces the Proposal Improvement System Application. The credentials are checked in the Database to either Sign Up or Sign In the user.
 */
  socialMedia(){
    this.socialData={"name":this.user["name"],"email":this.user["email"]}
      console.log("after2____",this.user)
    this.socialMediaAuth.socialMedia(this.socialData).subscribe(
      (data1:any) => {
        this.res=data1
      console.log("Response----",this.res);
     this.invalidLogin = false;
          this.loginSuccess = true;
          console.log("data", data1)
          // this.successMessage = 'Login Successful.';
          this.router.navigate(['/welcome']);
    }
    ,
    (error)=>{
      console.log("error------",error['error']);
      this.transferDataService.emitData(error['error'])
      this.router.navigate(['/team']);
    }
  );
  }
    /**
 * This method calls the Log In Service which checks the Submitted credentials to the database to Sign the user into the Application.
 */
  handleLogin() {
    if(this.loginForm.valid) {
         this.loginService.doLogin(this.loginForm.get('email').value,this.loginForm.get('password').value).subscribe(
           (result)=> {
          this.userData=sessionStorage.getItem('authenticatedUser')
          this.invalidLogin = false;
          this.loginSuccess = true;
          // this.successMessage = 'Login Successful.';
          this.router.navigate(['/welcome']);
        }, (error) => {
          if(error.status==200){
              this.userData=sessionStorage.getItem('authenticatedUser')
              this.invalidLogin = false;
              this.loginSuccess = true;
              // this.successMessage = 'Login Successful.';
              this.router.navigate(['/welcome']);
          }
          else if(error.status!=200){
            this.invalidLogin = true;
            this.loginSuccess = false;
          }
          else{
            alert("Some error has occured! please try again later.")
          }
        });

    }
  }
/**
 * This method opens the Reset Password component as Dialog Box allowing the user to Reset their Password.
 */
  openDialog(): void{
    const dialogRef = this.dialog.open(ForgetPasswordComponent, {
      width: '500px',
    });
  }
}
