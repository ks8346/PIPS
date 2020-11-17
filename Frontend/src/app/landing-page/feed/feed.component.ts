import { ShareProposalComponent } from './share-proposal/share-proposal.component';
import { CommentComponent } from './comment/comment.component';
import { Component, OnInit ,Input, Output, EventEmitter, NgModule} from '@angular/core';
import { Post } from 'src/app/post';
import {ProposalService} from '../proposal.service';
import {Comment} from '../comment'
import { JsonPipe } from '@angular/common';
/**
 * This is the child component of the landing-page and resposible for all the Proposals Shown on the landing-Page.
 */
@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})

export class FeedComponent implements OnInit {
  /**@ignore */
  @Input() post:Post;

  /**@ignore */
  public new_comment:string;

  /**@ignore */
  public canUpdate=false;

  /**@ignore */
  public numberLikes:number;

  /**@ignore */
  public comments:Comment[]=[];

  /**@ignore */
  public noComments:boolean=true;

  /**@ignore */
  public height:number;

  /**@ignore */
  public commentVisibility=false;

  /**@ignore */
  public commentsMessage="Comments";

  /**@ignore */
  commentError:string;

  /**@ignore */
  postCommentError:string;

  /** EventEmitter for the Update button on the Proposals */
  @Output() update=new EventEmitter;

  /**EventEmitter for the Share button on the Proposals */
  @Output() share=new EventEmitter;

  /**EventEmitter for the delete button on the Proposals */
  @Output() deleteProposal=new EventEmitter;

  /**Input from the current Session */
  @Input() userId:number;

  /**@ignore */
  @Input() type:string;

  /**@ignore */
  public hasLiked=false;

  /**
   * Constructor of the FeedComponent 
   * @constructor
   * @param proposalWork Instance of the ProposalService
   */
  constructor(public proposalWork:ProposalService) { }

  /** 
   * calls three methods in the same component on intialization -
   * updateApproval(),   likeSetup(),     commentsSetup()
  */
  ngOnInit(): void {
    this.updateApproval()
    this.likeSetup()
    this.commentsSetup()
    
  }
  /**This method checks whether user can Update a particular proposal or not.A user can those Proposal 
   * that are created by a user not others.
  */
  updateApproval(){
    if(this.post.user.id==this.userId){
      this.canUpdate=true
    }
    else{
      this.canUpdate=false
    }
  }
  /**This method is Resposible for the arrangement of the comments as per a particular manner.
  */
  commentsSetup(){
    this.proposalWork.getComment(this.post.id).subscribe(
      (data)=>{
        this.comments=[]
        this.comments=this.comments.concat(data)
        this.commentError=""
        if(data!=null){
          if(data.length==1){
            this.commentVisibility=true
            this.commentsMessage="Comments"
            this.height=95
          }
          else{
            this.commentsMessage="Comments"
            this.height=200
          }
        }
        else{
          this.commentVisibility=true
          this.commentsMessage="No comments on this post yet"
          this.noComments=false
          this.height=95
        }
      },
      (error)=>{
        if(error.status!=200){
          alert("Some error has occured retrieving the comments please reload")
          this.commentError="Some error has occured retrieving the comments please reload"
        }
      }
    )
  }
  /**This method checks total number of like on a post and whether a user with current session has liked it or not
  */
  likeSetup(){
    this.numberLikes=this.post.upvotesCount;
    this.proposalWork.getLike(this.post.id,this.userId).subscribe(
      (data)=>{
        this.hasLiked=data,console.log(this.hasLiked)
      }
    )
  }

  /**
   * This method is resposible for posting a comment on a post
   * @param {number} id This is proposalId of the proposal to which a user want to post a comment.
   */
  postComment(id:number){
    this.proposalWork.postComment(id,this.new_comment,this.userId).subscribe(
      (data)=>{
          this.new_comment=""
          this.commentsMessage="Comments"
          this.commentVisibility=true
          this.noComments=true
          this.commentsSetup()
      },(error)=>{
        if(error.status==200){
          console.log("in error")
          this.new_comment=""
          this.commentVisibility=true
          this.noComments=true
          this.commentsMessage="Comments"
          this.commentsSetup()
        }
        if(error.status!=200){
          this.postCommentError="Some error has occured sending the comment please try again."
        }
      } 
    );
    console.log(id+this.userId+this.new_comment)
  }
/**
 * This method is resposible for the feature of liking a Proposal and Unlike feature too.
 */
  postLike(id:number){
    if(this.hasLiked){
      this.proposalWork.postDislike(id,this.userId).subscribe((data)=>{
        this.hasLiked=false
        this.numberLikes-=1;
      },(error)=>{
        if(error.status==200){
          this.hasLiked=false
          this.numberLikes-=1;
        }
        else{
          alert("Some error has happened while disliking, please try again")
        }
      })
    }
    else{
      this.proposalWork.postLike(id,this.userId).subscribe((data)=>{
        this.hasLiked=true
        this.numberLikes+=1;
      },(error)=>{
        if(error.status==200){
          this.hasLiked=true
          this.numberLikes+=1;
        }
        else{
          alert("Some error has happened while liking, please try again")
        }
      })
    }
  }
  /**
   * This method emits the Post to which user want to open a share dialog.
   */
  openDialogshare(){
    this.share.emit(this.post)
  }
  /**
   * This method emits the proposal which a user want to update and open a update dialog
   * @param {object} post this is an object of a proposal
   */
  openDialog(post){
    this.update.emit(post)
  }

  /**
   * This method is responsible for the real-time deletion of the comment.
   * @param {number} commentId this is the id of the comment which user wants to delete.
   */
  onDelete(commentId){
    this.proposalWork.deleteComment(commentId).subscribe((data)=>console.log(data),
      (error)=>{
        if(error.status==200){
          this.comments=this.comments.filter(item => item.id != commentId);
        }
      }
    )
  }

  /**
   * This method is responsible for the real-time deletion of the proposal.
   */
  delProposal()
  {
    this.proposalWork.deletePost(this.post.id).subscribe( 
      (data)=>{
       console.log(data)
        this.deleteProposal.emit(this.post.id)
      },
      (error)=>{
        if(error.status==406){
          alert("Error deleting proposal")
        }
        else if(error.status==200){
          console.log(error)
          this.deleteProposal.emit(this.post.id)
        }
      }
    )
  }

}
