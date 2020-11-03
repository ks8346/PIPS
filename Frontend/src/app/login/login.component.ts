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
  errorMessage = "Invalid Credentials"
  constructor(private router: Router,public dialog: MatDialog) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });
  }
  matcher = new MyErrorStateMatcher()
  ngOnInit(): void {
  }

  handleLogin() {
    if(this.loginForm.valid) {
      console.log(this._v());
      this.loginForm.get('email').value;
      if(this.loginForm.get('email').value === "JohnDoe@gmail.com" &&
      this.loginForm.get('password').value === "qwerty"){
        //Redirect
        this.router.navigate(['home',this.loginForm.get('email').value])
        this.invalidLogin=false
      }
      else 
        this.invalidLogin=true
    }
  }
  _v() {
    return this.loginForm.value;
  }
  openDialog(): void{
    const dialogRef = this.dialog.open(ForgotPasswordDialog, {
      width: '500px',
      data: {email: this.email}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.email = result;
    });
  }
}

@Component({
  selector: 'forgot-password',
  templateUrl: 'forgotPassword.component.html',
})
export class ForgotPasswordDialog {

  constructor(
    public dialogRef: MatDialogRef<ForgotPasswordDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}
     matcher = new MyErrorStateMatcher()

  onNoClick(): void {
    this.dialogRef.close();
  }

}

