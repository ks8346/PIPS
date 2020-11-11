import { Component, OnInit ,Input, Output, EventEmitter} from '@angular/core';
import { Post } from 'src/app/post';
import {ProposalService} from '../proposal.service';
import {Comment} from '../comment'
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  @Input() post:Post;
  public new_comment:string;
  public canUpdate=false;
  public numberLikes:number;
  public comments:Comment[]=[];
  public noComments:boolean=true;
  public height:number;
  public commentVisibility=false;
  public commentsMessage="Comments";
  commentError:string;
  postCommentError:string;
  @Output() update=new EventEmitter;
  @Output() share=new EventEmitter;
  @Output() deleteProposal=new EventEmitter;
  @Input() userId:number;
  @Input() type:string;
  public hasLiked=false;
  constructor(public proposalWork:ProposalService) { }
  ngOnInit(): void {
    this.updateApproval()
    this.likeSetup()
    this.commentsSetup()
    
  }

  updateApproval(){
    if(this.post.user.id==this.userId){
      this.canUpdate=true
    }
    else{
      this.canUpdate=false
    }
  }

  commentsSetup(){
    this.proposalWork.getComment(this.post.id).subscribe(
      (data)=>{
        this.comments=[]
        this.comments=this.comments.concat(data)
        this.commentError=""
        if(data!=null){
          if(data.length<=1){
            this.commentVisibility=true
            if(this.comments.length==0){
              this.commentsMessage="No comments on this post yet"
            }
            else{
              this.commentsMessage="Comments"
              this.height=95
            }
          }
          else{
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
        else if(error.status==200) {
          this.commentVisibility=true
          this.commentsMessage="No comments on this post yet"
        }
      }
    )
  }

  likeSetup(){
    this.numberLikes=this.post.upvotesCount;
    this.proposalWork.getLike(this.post.id,this.userId).subscribe(
      (data)=>{
        this.hasLiked=data,console.log(this.hasLiked)
      }
    )
  }

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
        this.numberLikes-=1;
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

  openDialogshare(){
    this.share.emit(this.post)
  }

  openDialog(post){
    this.update.emit(post)
  }

  onDelete(commentId){
    this.proposalWork.deleteComment(commentId).subscribe((data)=>console.log(data),
      (error)=>{
        if(error.status==200){
          this.comments=this.comments.filter(item => item.id != commentId);
        }
      }
    )
  }
 
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
