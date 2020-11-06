import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Proposal} from './proposal'
@Injectable({
  providedIn: 'root'
})
export class PostProposalService {
  public sentTeams:{}[]
  private createUrl="http://localhost:8080/proposal/add";
  private updateUrl="";
  constructor(private http:HttpClient) { }
  postProposal(data,userId){
    data.userId=userId
    if(!data.key){
      data.key="null"
      console.log(data)
      return this.http.post(this.createUrl,data)
    }
    else{
      return this.http.post(this.updateUrl,data)
    }
    
  }
}
