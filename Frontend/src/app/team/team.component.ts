import { SocialAuthService } from 'angularx-social-login';
import { ApiResponseComponent } from './../api-response/api-response.component';
import { Router } from '@angular/router';
import { UserRegisterService } from './../service/user-register.service';
import { MatDialog } from '@angular/material/dialog';
import { TransferDataService } from './../service/transfer-data.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';


@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {

  message : any;
  teamForm : FormGroup;
  loading = false;
  submitted = false;
  selectedTeam: string='';
  teams:any
  teamName
  userData


  constructor(
   
    private formBuilder: FormBuilder,
    private transferDataService:TransferDataService,
    public dialog: MatDialog,
    private userService: UserRegisterService,
    public router: Router,
    private authService: SocialAuthService
  ) { }
/**
 * This Method opens a Dialog Box, after you submit the team name, showing the response saying if the team was updated or not.
 */
  responseDialog() {
    const dialogRef = this.dialog.open(ApiResponseComponent, {
      height: '180px',
      width: '400px',
      data:{data:this.message}
    });
  }

  ngOnInit(): void {
    this.userData=(this.transferDataService.userData)
    console.log("Test----",this.userData)

    this.teamForm = this.formBuilder.group({
      team: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]]
  });
  this.userService.getTeam().subscribe(
    data=> this.teams=data
  );
  }
/**
 * This method calls the User Register Service to send data to register a user into the system.
 */
  onSubmit(){
    this.submitted = true;
    console.log(this.userData["name"],this.userData.email)
    this.teamName=this.teamForm.value.team;
    var data={"data1":
    {"team":
      {
        "name":this.teamName
      }
 ,    "user":
      {
        "name": this.userData.name,
        "email": this.userData.email,
        "password" :this.userData["name"]+"@123"
      } 
    }
    }
    this.userService.doRegister(data).subscribe(
      (data1) => {
         console.log(data1);
           this.message="You have been signed up! Now please login again to continue."
           this.responseDialog()
           this.authService.signOut();
           this.router.navigate(['/home']) ;
           this.loading=false;
       },
       (error)=>{
        if(error.status==200){
         
          this.message="You have been signed up! Now please login again to continue."
          this.responseDialog()
          this.authService.signOut();
           this.router.navigate(['/home']) ;
           this.loading=false;
       
        }
        else{
          alert("Some error has occured! please try again later.")
        }
      });

    // stop here if form is invalid
    if (this.teamForm.invalid) {
        return;
    }

    this.loading = true;
  }

}
