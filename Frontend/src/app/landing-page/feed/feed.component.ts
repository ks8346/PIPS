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
  public singleComment:string;
  public singleCommentUser:string;
  public canUpdate=false;
  public numberLikes:number;
  public comments:Comment[]=[];
  public show=false;
  public commentVisibility=false;
  public commentsMessage="Comments";
  commentError:string;
  postCommentError:string;
  @Output() update=new EventEmitter;
  @Input() userId:number;
  @Input() type:string;
  public hasLiked=false;
  constructor(public proposalWork:ProposalService) { }
  ngOnInit(): void {
    if(this.type=="yourPost"){
      this.canUpdate=true
    }
    this.numberLikes=this.post.upvotesCount;
    this.proposalWork.getComment(this.post.id).subscribe((data)=>{
        this.comments=this.comments.concat(data)
        console.log(this.comments)
        this.commentError=""
        if(data.length<=1){
          this.commentVisibility=true
          if(data.length==0){
            this.commentsMessage="No comments on this post yet"
          }
          else{
            this.singleComment=data[0].comment
            this.singleCommentUser=data[0].user.name
            this.commentsMessage="Comments"
          }
        }
        else{
          this.singleComment=data[0].comment
          this.singleCommentUser=data[0].user.name
          this.commentsMessage="Comments"
        }
      },
      (error)=>{
        if(error.status!=200){
          console.log("Some error has occured retrieving the comments please reload")
          this.commentError="Some error has occured retrieving the comments please reload"
        }
      }
    )
    console.log(this.post.id)
    this.proposalWork.getLike(this.post.id,this.userId).subscribe((data)=>{this.hasLiked=data,console.log(this.hasLiked)})
  }
  postComment(id:number){
    this.proposalWork.postComment(id,this.new_comment,this.userId)
    .subscribe(
      (data)=>{
          this.comments.push({'id':this.post.id,'comment':this.new_comment,'creationDate':new Date(),'user':{
            'id':JSON.parse(sessionStorage.getItem('authenticatedUser')).id,'name':JSON.parse(sessionStorage.getItem('authenticatedUser')).name}}) 
          this.new_comment=""
          this.commentsMessage="Comments"
      },(error)=>{
        if(error.status==200){
          this.comments.push({'id':this.post.id,'comment':this.new_comment,'creationDate':new Date(),'user':{
            'id':JSON.parse(sessionStorage.getItem('authenticatedUser')).id,'name':JSON.parse(sessionStorage.getItem('authenticatedUser')).name}}) 
          this.new_comment=""
          this.commentsMessage="Comments"
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
          console.log("Some error has happened while disliking, please try again")
        }
      })
      console.log("dislike")
    }
    else{
      this.proposalWork.postLike(id,this.userId).subscribe((data)=>{
        this.hasLiked=false
        this.numberLikes-=1;
      },(error)=>{
        if(error.status==200){
          this.hasLiked=true
          this.numberLikes+=1;
          console.log("liked")
        }
        else{
          console.log("Some error has happened while liking, please try again")
        }
      })
    }
  }
  openDialog(id:number){
    this.update.emit(id)
  }
  seeMore(){
    if(this.show){
      this.show=false
    }
    else{
      this.show=true
    }
  }
}
