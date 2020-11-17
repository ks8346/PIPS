import { FilterComponent } from './filter/filter.component';
import { FeedComponent } from './feed/feed.component';

import { SocialAuthService } from 'angularx-social-login';
import { Component, OnInit ,HostListener, NgModule} from '@angular/core';
import { GetProposalsService } from '../get-proposals.service';
import {MatDialog} from '@angular/material/dialog';
import { CreateProposalComponent } from './create-proposal/create-proposal.component';
import { ShareProposalComponent } from './feed/share-proposal/share-proposal.component';
import {PostProposalService} from 'src/app/post-proposal.service'
import { FeedParams } from '../feed-params';
import {TeamsService} from '../teams.service'
import {Teams} from '../teams'
import {Router} from '@angular/router'
import {User} from '../user';
import {AuthorizationService} from '../authorization.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
 
})

export class LandingPageComponent implements OnInit {

  message=null;
  menuVisibility=true;
  menuButton=false;
  innerWidth;
  _teams:Teams[];
  feed=[];
  newFeed=[];
  proposalError:string;
  user:User;
  type="teamPost";
  page=0;
  width:number;
  padding:number;
  endMessage="";
  morePost=true;
  startDate=new Date()
  data=new FeedParams(new Date(this.startDate.setDate(this.startDate.getDate()-30)),new Date(),"0","3")
  constructor(private authService: SocialAuthService,public autho:AuthorizationService,public router:Router,public post:PostProposalService,public dialog:MatDialog,private getProposals:GetProposalsService,private teams:TeamsService) { }

  ngOnInit(): void {
    this.user=this.autho.authorization()
    this.selectApi(this.type)
    this.getTeams()
    this.resize()
  }

  getTeams(){
    this.teams.getTeams().subscribe(
      (data)=>{
        this._teams=data
      }
    );
  }

  getAll(){
    this.getProposals.getAllPosts(this.data).subscribe((data)=>this.feed=data,(error)=>this.errorHandling(error));
  }

  getTeam(){
    this.getProposals.getTeamPosts(this.data,this.user.team.id).subscribe((data)=>this.feed=data,(error)=>this.errorHandling(error));
  }
  
  getYour(){
    this.getProposals.getYourPosts(this.data,this.user.id).subscribe((data)=>this.feed=data,(error)=>this.errorHandling(error));
  }
  
  selectApi(data){
    if(data==="allPost"){
      this.getAll()
    }
    else if(data==="teamPost"){
      this.getTeam()
    }
    else if(data==="yourPost"){
      this.getYour()
    }
  }
  /**
 * this function is resposible for filtering the feed on the basis of
 * team Post and
 * the dates selectes in the advanced filter
 * @param data can be array of dates and can be a string.
 */
  onFilter(data){
    this.feed=[]
    if(Array.isArray(data)){
      this.data.startDate=new Date(data[0])
      this.data.endDate=new Date(data[1])
    }
    else{
      this.type=data;
      console.log(this.type)
    }
    this.page=0
    this.data.page=this.page.toString()
    this.selectApi(this.type)
    this.morePost=true
    this.endMessage=""
  }
   /**
 * this function is resposible for adding more proposals to the page as user scroll down.
 * here we uses infine-Scroll feature of angular
 */
  onScroll(){
    if((this.newFeed.length>0 || this.page==0)&&this.morePost){
      this.page++
      this.data.page=this.page.toString()
      console.log(this.data)
      if(this.type.includes("allPost")){
        this.getProposals.getAllNextPost(this.data).subscribe((data)=>this.newFeed=data,(error)=>this.errorHandling(error))
      }
      else if(this.type.includes("teamPost")){
        this.getProposals.getTeamNextPost(this.data,this.user.team.id).subscribe((data)=>this.newFeed=data,(error)=>this.errorHandling(error))
      }
      else if(this.type.includes("yourPost")){
        this.getProposals.getYourNextPost(this.data,this.user.id).subscribe((data)=>this.newFeed=data,(error)=>this.errorHandling(error))
      }      
      this.feed=this.feed.concat(this.newFeed)
      console.log(this.newFeed)
      this.newFeed=[]
    }
  }

  /**
 * opens a dialog for the sharing of proposal
 */
  openDialogshare(post){
    let dialogRef = this.dialog.open(ShareProposalComponent, {
      height: '250px',
      width: '400px',
      data:{prop:post.teams,teams:this._teams}
    });
    dialogRef.afterClosed().subscribe(result =>{
      if(result){
        this.post.shareProposal(result,post.id).subscribe(
          (data)=>console.log(data),
          (error)=>{
            if(error.status==200){
              window.location.reload()
            }
          })
      }
    })

  }
  
  /**
 * opens a dialog for the creating a Proposal.
 */
  openDialog(post?){
    let dialogRef = this.dialog.open(CreateProposalComponent, {
      height: '400px',
      width: '600px',
      data:{name:this.user.id,post:post,teams:this._teams}
    });
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.post.postProposal(result,this.user.id).subscribe(
          (data)=>{
            this.selectApi(this.type)
            this.page=0
            this.data.page=this.page.toString()
            window.location.reload()
            this.proposalError=""
          },
          (error)=>{
            if(error.status==200){
              this.selectApi(this.type)
              this.page=0
              this.data.page=this.page.toString()
              window.location.reload()
              this.proposalError=""
            }
            else{
              alert("Some error has occured! please try again later.")
            }
          }
        ) 
      }
    });
  }
  
  showMenu(){
    if(this.menuVisibility){
      this.menuVisibility=false
    }
    else{
      this.menuVisibility=true
    }
  }
  
  resize(){
    if(this.innerWidth<916){
      this.menuButton=true
      this.width=100
      this.padding=10
    }
    else{
      this.menuButton=false
      this.menuVisibility=true
      this.width=23.5
      this.padding=4
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.innerWidth = event.target.innerWidth;
    this.resize()
  }
  
  errorHandling(error){
    if(error.status==406){
      this.morePost=false;
      this.endMessage="There aren't any more proposals to show"
    }
    else{
      this.endMessage=""
    }
  }
  
 /**
 * @example
 * This Destroys a session when called
 * destroySession()
 * @returns To Home page
 */
  destroySession(){
   
    this.router.navigate(['/home']);
    this.autho.clearSession()
    this.authService.signOut();
  }
/**
 * @example
 * This deletes a proposal in real time
 * deleteProposal(1)
 *
 * @param {number} 
 * 
 */
  deleteProposal(id){
    this.feed=this.feed.filter(item => item.id != id);
  }
}
