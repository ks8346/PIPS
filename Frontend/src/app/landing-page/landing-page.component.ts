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
/**Landing page parent to feed, and filter components*/
@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css'],
 
})

export class LandingPageComponent implements OnInit {
/**@ignore */
  message=null;
  /**@ignore */
  menuVisibility=true;
  /**@ignore */
  menuButton=false;
  /**@ignore */
  innerWidth;
  /**teams array */
  _teams:Teams[];
  /**feed array */
  feed=[];
  /**@ignore */
  newFeed=[];
  /**@ignore */
  proposalError:string;
  /**User session */
  user:User;
  /**type of feed */
  type="teamPost";
  /**@ignore */
  page=0;
  /**@ignore */
  width:number;
  /**@ignore */
  padding:number;
  /**@ignore */
  endMessage="";
  /**@ignore */
  morePost=true;
  /**@ignore */
  startDate=new Date()
  /**@ignore */
  data=new FeedParams(new Date(this.startDate.setDate(this.startDate.getDate()-30)),new Date(),"0","3")
  
  constructor(
    /**@ignore */
    private authService: SocialAuthService,
    /**session generation and deletion*/
    public autho:AuthorizationService,
    /**@ignore */
    public router:Router,
    /**posts proposal and update proposal*/
    public post:PostProposalService,
    /**@ignore */
    public dialog:MatDialog,
    /**gets all the posts from the backend*/
    private getProposals:GetProposalsService,
    /**teams objects api call*/
    private teams:TeamsService) { }
/**gets the session data and calls functions to setup the initial view*/
  ngOnInit(): void {
    this.user=this.autho.authorization()
    this.selectApi(this.type)
    this.getTeams()
    this.resize()
  }
/**
  * This method calls a service method that is an api call to Team posts
  */ 
  getTeams(){
    this.teams.getTeams().subscribe(
      (data)=>{
        this._teams=data
      }
    );
  }
  /**
  * This method calls a service method that is an api call to all posts
  */ 
  getAll(){
    this.getProposals.getAllPosts(this.data).subscribe((data)=>this.feed=data,(error)=>{
      this.errorHandling(error)
      if(error.status!=200){
        alert("There was some error at server please try later")
      }
    });
  }
/**
 * This method calls a service method that is an api call to all created posts
 */ 
  getTeam(){
    this.getProposals.getTeamPosts(this.data,this.user.team.id).subscribe((data)=>this.feed=data,(error)=>{
      this.errorHandling(error)
      if(error.status!=200){
        alert("There was some error at server please try later")
      }
    });
  }
  /**
 * This method calls a service method that is an api call to all created posts
 */ 
  getYour(){
    this.getProposals.getYourPosts(this.data,this.user.id).subscribe((data)=>this.feed=data,(error)=>{
      this.errorHandling(error)
      if(error.status!=200){
        alert("There was some error at server please try later")
      }
    });
  }
  /**
 * This method filters the api calls on the basis of which type is selected
 * @param {string}
 * @returns Calls different service methods to call different post api's
 */ 
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
 * This method is called when filter components sends a data to filter feed
 * @param {string?,array?}
 * @returns Calls select api function to select which feed api to call or changes the date to filter api feed
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
 * This method runs when a scroll event is emitted
 * @returns returns new 3 proposals every time scroll event is emitted
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
 * This method opens a Share proposal dialog
 * @returns After closing calls an api to update teams of a proposal
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
 * This method opens a Create proposal dialog
 * @returns After closing calls an api to store proposal and refreshs the page
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
  
/**
 * This method checks the responsive filter menu is open or not
 */
  showMenu(){
    if(this.menuVisibility){
      this.menuVisibility=false
    }
    else{
      this.menuVisibility=true
    }
  }
  
  /**
 * This method is called by OnResize() and sets values of different variables that controls the padding and width
 */
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
/**window resize evenr handled*/
  @HostListener('window:resize', ['$event'])
/**
 * This method listens to the window resize event
 * @returns it calls a resize funtion for making the application responsive
*/
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
 * This method destroys a session when called
 * @example
 * destroySession()
 * @returns Returns to Home page of the application after destroying session
 */
  destroySession(){
   
    this.router.navigate(['/home']);
    this.autho.clearSession()
    this.authService.signOut();
  }
/**
 * 
 * This method deletes a proposal in real time by removing it from the array stored on frontend and also sending an api call to backend at the same time
 * @example
 * deleteProposal(1)
 *
 * @param {number} 
 * 
 */
  deleteProposal(id){
    this.feed=this.feed.filter(item => item.id != id);
  }
}
