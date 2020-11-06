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
  constructor(private http:HttpClient) { }
  postProposal(data,userId){
    data.userId=userId
    console.log(data.key)
    data.value.key=data.key
    if(!data.value.key){
      data.key="null"
      console.log(data)
      return this.http.post(this.createUrl,data.value)
    }
    else{
      console.log("Updated")
      return this.http.post(this.updateUrl,data.value)
    }
    
  }
}
