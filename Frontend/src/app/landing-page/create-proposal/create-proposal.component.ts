import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog,MAT_DIALOG_DATA} from '@angular/material/dialog';
import { TestServiceService } from '../test-service.service';
/**
 * This Component is child Component of Landing-page and resposible for creation of Proposal and updation of propsal.
 */
@Component({
  selector: 'app-create-proposal',
  templateUrl: './create-proposal.component.html',
  styleUrls: ['./create-proposal.component.css']
})
export class CreateProposalComponent implements OnInit {

  /**@ignore */
  constructor(public dialog:MatDialog,public service:TestServiceService,@Inject(MAT_DIALOG_DATA) public data){}

  /**@ignore */
  sentTeam=[]

  /**@ignore */
  Teams:{}[]=[]
  /** This is the Id of user from Current Session.*/
  userId:string

  /**@ignore */
  teamVisibility=true

  /**@ignore */
  dialogTitle:string="Create Proposal"

  /**@ignore */
  id:number=null

  /**@ignore */
  values:TestServiceService;

  /** This is the Title of proposal*/
  public tit 

  /**This is the description of proposal*/
  public descrip

  /**@ignore */
  public prop

  /**This method is Resposible for differentiating whether dialog is opened  for creation of proposal or updation of proposal */
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
