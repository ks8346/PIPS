import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog,MAT_DIALOG_DATA} from '@angular/material/dialog';
import { TestServiceService } from '../test-service.service';

@Component({
  selector: 'app-create-proposal',
  templateUrl: './create-proposal.component.html',
  styleUrls: ['./create-proposal.component.css']
})
export class CreateProposalComponent implements OnInit {
  constructor(public dialog:MatDialog,public service:TestServiceService,@Inject(MAT_DIALOG_DATA) public data){}
  sentTeam=[]
  Teams:{}[]=[]
  userId:string
  id="null"
  values:TestServiceService;
  ngOnInit(){
    this.userId=this.data.userId
    this.id=this.data.id
  }
  onClear(){
    this.service.form.reset();
    this.service.initialiseFormGroup();
  }
  onSubmit(){
    console.log(this.service.form.value);
    this.values=this.service.form.value
    this.sentTeam=this.service.form.controls["teams"].value
    
    // this.Teams=JSON.parse(this.service.form.controls["teams"].value[0])
    
    return {"title":this.service.form.controls["title"].value,"description":this.service.form.controls["description"].value,"teams":this.sentTeam}
  }
}
