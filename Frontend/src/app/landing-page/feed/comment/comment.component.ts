import { Component, OnInit ,Input,Output,EventEmitter } from '@angular/core';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
  @Input() comment:Comment;
  public commentId;
  public userName;
  public commentData;
  public commentedUserId:number;
  @Input() userId:number;

  @Output() 
 parentFunction:EventEmitter<any> = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
    // console.log(this.comment)
    this.commentData = this.comment.comment;
    this.commentId = this.comment.id;
    this.userName = this.comment.user.name;
    this.commentedUserId = this.comment.user.id;

    
  }

  deleteComment()
  {
      //  console.log(this.commentId)

       this.parentFunction.emit(this.commentId) 
  }

}
