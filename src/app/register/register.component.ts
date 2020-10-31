import { PasswordSpecsComponent } from './../password-specs/password-specs.component';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {ConfirmPasswordValidator} from '../confirmPassword.Validator';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  loading = false;
  submitted = false;
  error: string;
  selectedTeam: string='';
  newTeam: string='';
  checked= false;


  constructor(
    private formBuilder: FormBuilder,
    public dialog: MatDialog
  ) { }

  /*checkAgreement(event: any){
    if ( event.target.checked ) {
      this.checked = true;
    }
  }*/

  openDialog() {
    const dialogRef = this.dialog.open(PasswordSpecsComponent, {
      height: '380px',
      width: '400px',
    });
  }

  updateTeam(event:any){
    this.newTeam=event.target.value;
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      userName: ['', Validators.required],
      email: ['', [Validators.required,  Validators.email]],
      team: ['', [Validators.required]],
      password: ['', [Validators.required,  Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{8,}')]], 
      confirmPass: ['', Validators.required],
      acceptTerms: [false, Validators.requiredTrue]
  },
  {
    validator: ConfirmPasswordValidator("password", "confirmPass")
  });
  }

  get f() { return this.registerForm.controls; }

  getEmailError() {
    if (this.registerForm.controls.email.hasError('required')) {
      return 'You must enter a value';
    }

    if(this.registerForm.controls.email.hasError('email')){
      return 'Not a valid email';
    }
  }

  onSubmit() {
    this.submitted = true;
    if(this.selectedTeam=='others'){
      console.log(this.newTeam);
    }
    else{
    console.log(this.selectedTeam);}

    // stop here if form is invalid
    if (this.registerForm.invalid) {
        return;
    }

    this.loading = true;

}

teams:string[]=['Devs', 'Sparks'];

}

