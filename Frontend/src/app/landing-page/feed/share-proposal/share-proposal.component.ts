import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog,MAT_DIALOG_DATA} from '@angular/material/dialog';
import {FormGroup,FormControl,Validators} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-share-proposal',
  templateUrl: './share-proposal.component.html',
  styleUrls: ['./share-proposal.component.css']
})
export class ShareProposalComponent implements OnInit {
  sentTeam=[]
  Teams:{}[]=[]

  constructor(public dialog: MatDialog,@Inject(MAT_DIALOG_DATA) public data) {this.updateForm= new FormGroup({
    teams: new FormControl('0', [Validators.required]),
  });} 
  updateForm: FormGroup;
    

  ngOnInit(): void {
    this.sentTeam=this.data.teams.filter(item => this.data.prop.indexOf(item) == -1);
  }
  onSubmit(){
    console.log(this.updateForm.value);
    this.sentTeam=this.updateForm.controls["teams"].value

  }

}
