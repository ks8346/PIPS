import { ApiResponseComponent } from './../api-response/api-response.component';
import { GetTeamService } from './../service/get-team.service';
import { Router } from '@angular/router';
import { UserRegisterService } from './../service/user-register.service';
import { PasswordSpecsComponent } from './../password-specs/password-specs.component';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {ConfirmPasswordValidator} from '../confirmPassword.Validator';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';



@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  message: any;
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  error: string;
  selectedTeam: string='';
  teams:any
  hide=true

  constructor(
    private getTeam : GetTeamService,
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private userService: UserRegisterService,
    public router: Router
  ) { }


  openDialog() {
    const dialogRef = this.dialog.open(PasswordSpecsComponent, {
      height: '380px',
      width: '400px',
    });
  }

  responseDialog(msg) {
    const dialogRef = this.dialog.open(ApiResponseComponent, {
      height: '180px',
      width: '400px',
      data:{data:this.message}
    });
  }


  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      userName: ['',[ Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
      email: ['', [Validators.required,  Validators.email,, Validators.minLength(2), Validators.maxLength(30)]],
      team: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
      password: ['', [Validators.required, , Validators.minLength(2), Validators.maxLength(20), Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')]], 
      confirmPass: ['', Validators.required],
      acceptTerms: [false, Validators.requiredTrue]
  },
  {
    validator: ConfirmPasswordValidator("password", "confirmPass")
  });
  this.getTeam.getTeam().subscribe(
    data=> this.teams=data
  );
  }

  get f() { return this.registerForm.controls; }

  getEmailError() {
    if (this.registerForm.controls.email.hasError('required')) {
      return 'Please enter a value';
    }

    if(this.registerForm.controls.email.hasError('email')){
      return 'Not a valid email';
    }
  }

  onSubmit() {
    this.submitted = true;
    
    console.log(this.registerForm.value.team);

    // stop here if form is invalid
    if (this.registerForm.invalid) {
        return;
    }

    this.loading = true;
    var data={"data1":
      {"team":
{
    "name":this.registerForm.value.team
}
   , "user":
{
    "name": this.registerForm.value.userName,
    "email": this.registerForm.value.email,
    "password" :this.registerForm.value.password

}
    }

    }
    this.userService.doRegister(data).subscribe(
       (data1) => {
          console.log(data1);
          if(data1=="Email Id already exists !!"){
            this.message="Email Id already exists!"
            this.loading=false;
            this.responseDialog(this.message)
          }
          else{
            this.message="You have been signed up!"
            this.responseDialog(this.message)
            this.router.navigate(['/home']) ;
            this.loading=false;
          }
        },
        (error)=>{
          if(error.status==200){
            this.message="You have been signed up!"
            this.responseDialog(this.message)
            this.router.navigate(['/home']) ;
            this.loading=false;
          }
          else{
            this.message="Email Id already exists!"
            this.loading=false;
          }
        }
      );


}
handle(responce){
  console.log(responce)
  console.log(responce.mesaage)
  this.message=responce.message
}




}

