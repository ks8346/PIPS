import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Proposal} from './proposal'
@Injectable({
  providedIn: 'root'
})
export class PostProposalService {
  /**@ignore */
  public sentTeams:{}[]
  /**
   * a url to create proposal api
  */
  private createUrl="http://localhost:8080/proposal/add";
  /**url to update proposal api*/
  private updateUrl="http://localhost:8080/proposal/update";
  /**url to share proposal api*/
  private shareUrl="http://localhost:8080/proposal/share";
  constructor(
    /**@ignore */
    private http:HttpClient) { }
  /**
   * share proposal calls a post api to share proposal api
   * @param {objects of teams}
   * @param {number}
   * @returns an observable is returned 
  */
  shareProposal(data,id):Observable<any>{
    data.id=id
    return this.http.post(this.shareUrl,data)
  }
  /**
   * calls a post api to create proposal api
   * @param {objects of post}
   * @param {number}
   * @returns an observable is returned of type void
  */
  postProposal(data,userId){
    data.userId=userId
    console.log(data.key)
    if(!data.key){
      data.key="null"
      data.userId=userId
      return this.http.post(this.createUrl,data)
    }
    else{
      data.key=data.key.toString()
      console.log("Updated")
      console.log(data)
      return this.http.post(this.updateUrl,data)
    }
    
  }
}
