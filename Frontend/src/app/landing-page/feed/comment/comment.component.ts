import { Component, OnInit ,Input,Output,EventEmitter } from '@angular/core';

import {Comment} from 'src/app/landing-page/comment'
/**
 * CommentComponent is the child Component of FeedComponent and responsible for all the comments shown the feedComponent.
 */
@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {
  /**@ignore */
  @Input() comment:Comment;

  /**@ignore */
  public commentId;

  /**@ignore */
  public userName;

  /**@ignore */
  public commentData;

  /**@ignore */
  public commentedUserId:number;

  /** UserId of the user from the current session 
   * @example
   * Input from the FeedComponent
   */
  @Input() userId:number;

  /** 
   * This the emitter to the FeedComponent
   */
  @Output() 
 parentFunction:EventEmitter<any> = new EventEmitter<any>();
 
  /**@ignore */
  constructor() { }

  /**
   * This method is resposible for the comment data to show on the Page.
   */
  ngOnInit(): void {

     console.log(this.comment)

    this.commentData = this.comment.comment;
    this.commentId = this.comment.id;
    this.userName = this.comment.user.name;
    this.commentedUserId = this.comment.user.id;

  }
  /**
   * This method is responsible for emiting the commendId of the comment which we want to delete to Feed Component.
   */
  deleteComment()
  {
       this.parentFunction.emit(this.commentId) 
  }

}
