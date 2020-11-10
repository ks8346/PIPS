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
    // this.id=this.data.post.id
    if(this.data.post){
      this.teamVisibility=false
      this.id=this.data.post.id
      this.tit = this.data.post.title
      this.descrip = this.data.post.description
<<<<<<< HEAD
      this.dialogTitle="Update Proposal"
    }
=======
      this.prop= "Update Proposal"
     }
     else{
       this.prop="Create Proposal"
     }
>>>>>>> cd264f04b762fe81f8960aa940f3dddcb5975191
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
