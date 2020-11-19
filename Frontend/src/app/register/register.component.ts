import { teamList } from './../teamList';
import { ApiResponseComponent } from './../api-response/api-response.component';
import { Router } from '@angular/router';
import { UserRegisterService } from './../service/user-register.service';
import { PasswordSpecsComponent } from './../password-specs/password-specs.component';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmPasswordValidator} from '../confirmPassword.Validator';
import {MatDialog} from '@angular/material/dialog';

/**This component enables user to register to the application */
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  /**@ignore */
  message: any;

  /**This variable stores instance of registration form */
  registerForm: FormGroup;

  /**@ignore */
  loading = false;

  /**This variable is set true on successful submission of registration form */
  submitted = false;

  /**This variable stores the team selected by user */
  selectedTeam: string='';

  /**Array of all existing teams in database */
  teams:teamList[]

  /**@ignore */
  hide=true

  /**@ignore */
  hide1=true
  
  /**List of filtered teams while searching */
  public filteredTeams;

 /**@ignore */
  constructor(
    /**@ignore */
    private formBuilder: FormBuilder,
    /**@ignore */
    public dialog: MatDialog,
    /**@ignore */
    private userService: UserRegisterService,
    /**@ignore */
    public router: Router,
  ) { }

/**
 * This method opens dialog box displaying required specifications for the password field.
 */
  openDialog() {
    const dialogRef = this.dialog.open(PasswordSpecsComponent, {
      height: '380px',
      width: '400px',
    });
  }

/**
 * This Method opens a Dialog Box, after you submit the details, showing the response saying if the registraion was successful or not.
 */
  responseDialog() {
    const dialogRef = this.dialog.open(ApiResponseComponent, {
      height: '180px',
      width: '400px',
      data:{data:this.message}
    });
  }

/**
 * Creates a registration form with creatain validation checks.
 * 
 * Initializes a array of existing teams in the database.
 */
  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      userName: ['',[ Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
      email: ['', [Validators.required,  Validators.email,, Validators.minLength(2), Validators.maxLength(50), Validators.pattern('[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,63}$')]],
      team: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
      password: ['', [Validators.required, , Validators.minLength(2), Validators.maxLength(20), Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')]], 
      confirmPass: ['', Validators.required],
      acceptTerms: [false, Validators.requiredTrue]
  },
  {
    validator: ConfirmPasswordValidator("password", "confirmPass")
  });

  this.getTeams();
  }

/**
 * This Method calls the Get Team Service to fetch the list of teams currently in the database.
 */
  getTeams(){
    this.userService.getTeam().subscribe(
      (data)=> {this.teams=data
        this.filteredTeams=this.teams.slice();
      },
      (error)=>{
       if(error.status==404){
        this.filteredTeams=this.teams.slice();
        }
        else{
          alert("Some error has occured! please try again later.")
        }
      }); 
  }
  
  /**
   * This method returns error message based on email field validations.
   */
  getEmailError() {
    if (this.registerForm.controls.email.hasError('required')) {
      return 'Please enter a value';
    }

    if(this.registerForm.controls.email.hasError('email') || this.registerForm.controls.email.hasError('pattern') ){
      return 'Please enter a valid email';
    }
  }
/**
 * This method calls the User Register Service to send data to register a user into the system.
 */
  onSubmit() {
    this.submitted = true;
    
    console.log(this.registerForm.value.team);

    // stop here if form is invalid
    if (this.registerForm.invalid) {
        return;
    }

    this.loading = true;
    var data={"data1":
      {"team":{
          "name":this.registerForm.value.team}
      ,"user":{
        "name": this.registerForm.value.userName,
        "email": this.registerForm.value.email,
        "password" :this.registerForm.value.password}
      }

    }

    this.userService.doRegister(data).subscribe(
       (data1) => {
          this.message="You have been signed up! Now please login again to continue."
            this.responseDialog()
            this.loading=false;
            this.router.navigate(['/home']) ;
        },
        (error)=>{
          if(error.status==200){
            console.log(error.status)
            this.message="You have been signed up! Now please login again to continue."
            this.responseDialog()
            this.loading=false;
            this.router.navigate(['/home']) ;
         
          }
          else if(error.status==302){
            this.message="This email Id already exists, Please try again!"
            this.responseDialog()
            this.loading=false;
          }
          else{
            alert("Some error has occured! please try again later.")
          }
        });
}
}

