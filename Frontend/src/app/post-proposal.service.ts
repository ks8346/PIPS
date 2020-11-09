import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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
  shareProposal(data){
    this.http.post(this.shareUrl,data)
  }
  postProposal(data,userId){
    data.userId=userId
    console.log(data.key)
    data.value.key=data.key
    if(!data.value.key){
      data.value.key="null"
      console.log(data.value)
      data.value.userId=userId
      return this.http.post(this.createUrl,data.value)
    }
    else{
      console.log("Updated")
      return this.http.post(this.updateUrl,data.value)
    }
    
  }
}
