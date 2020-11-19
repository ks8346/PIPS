import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Feed } from './feed';
import {FeedParams} from './feed-params'
/**
 * handles all type of feed api's
 */
@Injectable({
  providedIn: 'root'
})
/**
 * Proposal to get different types of posts
*/
export class GetProposalsService {
  /**get all posts url*/
  allUrl="http://localhost:8080/feed/all"
  /**get all posts url include pagination*/
  allNextUrl="http://localhost:8080/feed/all"
  /**get team's posts url*/
  teamUrl="http://localhost:8080/feed/team"
  /**get team's posts url include pagination*/
  teamNextUrl="http://localhost:8080/feed/team"
  /**get created type posts url*/
  yourUrl="http://localhost:8080/feed/create"
  /**get created type posts url include pagination*/
  yourNextUrl="http://localhost:8080/feed/create"
  
  constructor(
    /**@ignore*/
    private _http:HttpClient) { }
  /**
   * This function makes a post api call to get all posts
   * @param {FeedParams}
   * @returns array of posts
  */
  getAllPosts(data:FeedParams): Observable<Feed[]>{
    console.log(data)
    return this._http.post<Feed[]>(this.allUrl,data,{responseType:'json'});
  }
  /**
   * This function makes a post api call to get all posts includes pagination
   * @param {FeedParams}
   * @returns array of posts
  */
  getAllNextPost(data:FeedParams):Observable<Feed[]>{
    return this._http.post<Feed[]>(this.allNextUrl,data);
  }
  /**
   * This function makes a post api call to get team type posts
   * @param {FeedParams}
   * @param {number}
   * @returns array of posts
  */
  getTeamPosts(data:FeedParams,teamId): Observable<Feed[]>{
    data.teamId=teamId
    console.log(data)
    return this._http.post<Feed[]>(this.teamUrl,data);
   
  }
  /**
   * This function makes a post api call to get team type posts include pagination
   * @param {FeedParams}
   * @param {number}
   * @returns array of posts
  */
  getTeamNextPost(data:FeedParams,teamId):Observable<Feed[]>{
    data.teamId=teamId
    return this._http.post<Feed[]>(this.teamNextUrl,data);
  }
  /**
   * This function makes a post api call to get created type posts
   * @param {FeedParams}
   * @param {number}
   * @returns array of posts
  */
  getYourPosts(data:FeedParams,userId): Observable<Feed[]>{
    data.userId=userId
    console.log(data)
    return this._http.post<Feed[]>(this.yourUrl,data);
   
  }
  /**
   * This function makes a post api call to get team type posts include pagination
   * @param {FeedParams}
   * @param {number}
   * @returns array of posts
  */
  getYourNextPost(data:FeedParams,userId):Observable<Feed[]>{
    data.userId=userId
    return this._http.post<Feed[]>(this.yourNextUrl,data);
  }

}
