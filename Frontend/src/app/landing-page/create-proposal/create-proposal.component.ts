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
  teamVisibility=true
  dialogTitle:string="Create Proposal"
  id:number=null
  values:TestServiceService;
  public tit 
  public descrip
  public prop
  ngOnInit(){
    this.userId=this.data.userId
    if(this.data.post){
      this.teamVisibility=false
      this.id=this.data.post.id
      this.tit = this.data.post.title
      this.descrip = this.data.post.description
      this.prop= "Update Proposal"
     }
     else{
       this.prop="Create Proposal"
     }
  }
  
}
