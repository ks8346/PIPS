import { SessionCreationService } from './../service/session-creation.service';
import { TransferDataService } from './../service/transfer-data.service';
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
  check:boolean=false
  email:string;
  loginForm: FormGroup;
  invalidLogin = false
  loginSuccess=false;
  successMessage: string;
  errorMessage = "Invalid Credentials"
  userData;
  socialData;
  uData
  private user: SocialUser;
  private loggedIn: boolean;
  public res

  constructor(private router: Router,
    private transferDataService:TransferDataService,
   private socialMediaAuth:SocialMediaAuthService,
    public dialog: MatDialog,
    public loginService:UserLoginService,
    private authService: SocialAuthService,
    private sessionCreation:SessionCreationService) {
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

  googleSignIn(){
      
      console.log("after1____",this.user)
      this.authService.authState.subscribe((user) => {
        if(user!=null)
        // if
        {this.user = user;
        
        console.log("BEFORE____",this.user)
        this.loggedIn = (user != null);
        this.socialMedia()
        }});
        if(!this.loggedIn){
          this.authService.signIn(GoogleLoginProvider.PROVIDER_ID)}
      // return true
  }

  socialMedia(){
    // this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
    // this.googleSignIN()
    // this.authService.authState.subscribe((user) => {
    //   this.user = user;
    //   this.socialData={"name":this.user["name"],"email":this.user["email"]}
    //   console.log(this.user["name"])
    //   console.log(this.user["email"])
    //   this.loggedIn = (user != null);
    //   this.check=true
    //   });
    
    // this.check=true
    // console.log(this.user)


    //buffer
    this.socialData={"name":this.user["name"],"email":this.user["email"]}
      console.log("after2____",this.user)
    this.socialMediaAuth.socialMedia(this.socialData).subscribe(
      (data1:any) => {
        this.res=data1
      console.log("Response----",this.res);
     this.invalidLogin = false;
          this.loginSuccess = true;
          ///
          // this.sessionCreation.fetchSessionDetails(JSON.stringify(data1))
          // sessionStorage.setItem('data', JSON.stringify(data1));
          console.log("data", data1)
          this.successMessage = 'Login Successful.';
          this.router.navigate(['/welcome']);
    }
    ,
    (error)=>{
      console.log("error------",error['error']);
      //team page
      this.transferDataService.emitData(error['error'])
      this.router.navigate(['/team']);

    }
  );
  }

  handleLogin() {
    if(this.loginForm.valid) {

      this.loginForm.get('email').value;

         this.loginService.doLogin(this.loginForm.get('email').value,this.loginForm.get('password').value).subscribe((result)=> {
        
          this.userData=sessionStorage.getItem('authenticatedUser')
          console.log("results",this.userData, this.userData)
          
          this.invalidLogin = false;
          this.loginSuccess = true;
          // sessionStorage.setItem('data', JSON.stringify(result));
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
