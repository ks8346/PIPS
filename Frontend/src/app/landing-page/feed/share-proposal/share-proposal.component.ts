import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog,MAT_DIALOG_DATA} from '@angular/material/dialog';
import {FormGroup,FormControl,Validators} from '@angular/forms';
import {ReactiveFormsModule} from '@angular/forms';
/**
 * This is a shareProposalComponent and a child component of feedComponent responsible for Sharing a Proposal.
 */
@Component({
  selector: 'app-share-proposal',
  templateUrl: './share-proposal.component.html',
  styleUrls: ['./share-proposal.component.css']
})
export class ShareProposalComponent implements OnInit {
  /**@ignore */
  sentTeam=[]

  /**@ignore */
  Teams:{}[]=[]

  /**@ignore */
  constructor(public dialog: MatDialog,@Inject(MAT_DIALOG_DATA) public data) {this.updateForm= new FormGroup({
    teams: new FormControl('0', [Validators.required]),
  });} 

  /**@ignore */
  updateForm: FormGroup;
    
   /**
   * This method checks whether a proposal is already shared to a particular team and filter those to which it is not shared.
   */
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

  /**@ignore */
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
  

}
