import { Component, OnInit } from '@angular/core';
import { GetTeamService } from './../service/get-team.service';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

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

  constructor(
    private getTeam : GetTeamService,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.teamForm = this.formBuilder.group({
      team: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]]
  });
  this.getTeam.getTeam().subscribe(
    data=> this.teams=data
  );
  }

  onSubmit(){
    this.submitted = true;
    
    console.log(this.teamForm.value.team);

    // stop here if form is invalid
    if (this.teamForm.invalid) {
        return;
    }

    this.loading = true;
  }

}
