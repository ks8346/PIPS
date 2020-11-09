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
    console.log(this.data.teams,this.data.prop)
    this.sentTeam=this.data.teams.filter(item => {
      let flag=true;
      for(let team of this.data.prop){
        if(team.id==item.id){
          flag=false
        }
      }
      if(flag)
        return item 
    });
    console.log(this.sentTeam)
  }
  selected(team){
    let flag=true;
    for(let item of this.data.prop){
      if(item.id==team.id){
        flag=false
      }
    }
    if(flag==false)
        return true
      else
        return false
  }
  onSubmit(){
    console.log(this.updateForm.value);
    this.sentTeam=this.updateForm.controls["teams"].value

  }

}
