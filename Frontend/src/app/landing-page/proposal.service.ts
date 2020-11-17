import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Comment} from './comment'
/**
 * This is Proposal Service
 */
@Injectable({
  providedIn: 'root'
})
export class ProposalService {

  /**Url for adding Comment */
  postCommentUrl="http://localhost:8080/comment/add"

  /**Url for getting all comments*/
  getCommentUrl="http://localhost:8080/comment/all"

  /**Url for Posting Like*/
  postLikeUrl="http://localhost:8080/upvotes/like"

  /**Url for Posting disLike*/
  postDislikeUrl="http://localhost:8080/upvotes/dislike"

  /**Url for checking user has upvoted or not*/
  getLikeUrl="http://localhost:8080/upvotes/hasUpvoted"

  /**Url for deleting comment*/
  deleteCommentUrl ="http://localhost:8080/comment/delete"

  /**Url for deleting a proposal*/
  deletePostUrl="http://localhost:8080/proposal/delete"

  /**@constructor
   * @param http This is the instance of object HttpClient
  */
  constructor(private http:HttpClient) { }

  /**This is the method for posting a comment to a Proposal
   * @param {number} id This is the proposal id.
   * @param {string} new_comment This is the comment user wants to post.
   * @param {number} userId This is the userId from current session.
   * @returns Object of Observable
  */
  postComment(id:number,new_comment:string,userId:number):Observable<any>{
    return this.http.post(this.postCommentUrl,{'id':id,'text':new_comment,'userId':userId})
  }

  /**This is the method for posting a like to a Proposal
   * @param {number} id This is the proposal id.
   * @param {number} userId This is the userId from current session.
   * @returns Object of Observable
  */
  postLike(id:number,userId:number):Observable<any>{
    return this.http.post(this.postLikeUrl,{'id':id,'userId':userId})
  }

  /**This is the method for posting a dislike to a Proposal
   * @param {number} id This is the proposal id.
   * @param {number} userId This is the userId from current session.
   * @returns Object of Observable
  */
  postDislike(id:number,userId:number):Observable<any>{
    console.log({'id':id,'userId':userId})
    return this.http.post(this.postDislikeUrl,{"userId":userId,"id":id})
  }

  /**This is the method for checking whether a user liked a post or not.
   * @param {number} id This is the proposal id.
   * @param {number} userId This is the userId from current session.
   * @returns Object of Observable
  */
  getLike(id:number,userId:number):Observable<any>{
    return this.http.post(this.getLikeUrl,{'id':id,'userId':userId})
  }

  /**This is the method for getting all comments
   * @param {number} id This is the proposal id.
   * @returns Object of Observable
  */
  getComment(id:number):Observable<Comment[]>{
    return this.http.post<Comment[]>(this.getCommentUrl,{'id':id})
  }

  /**This is the method for deleting a comment
   * @param {number} id This is the proposal id.
   * @returns Object of Observable
  */
  deleteComment(id:number):Observable<any>
  {
    return this.http.post<String>(this.deleteCommentUrl,{'id':id})
  }

  /**This is the method for deleting a proposal
   * @param {number} id This is the proposal id.
   * @returns Object of Observable
  */
  deletePost(id:number):Observable<any>
  {
    return this.http.post<String>(this.deletePostUrl,{'id':id})
  }
}
