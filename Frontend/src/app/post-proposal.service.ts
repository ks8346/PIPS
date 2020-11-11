import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Proposal} from './proposal'
@Injectable({
  providedIn: 'root'
})
export class PostProposalService {
  public sentTeams:{}[]
  private createUrl="http://localhost:8080/proposal/add";
  private updateUrl="http://localhost:8080/proposal/update";
  private shareUrl="http://localhost:8080/proposal/share"
  constructor(private http:HttpClient) { }
  shareProposal(data,id):Observable<any>{
    data.id=id
    return this.http.post(this.shareUrl,data)
  }
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
