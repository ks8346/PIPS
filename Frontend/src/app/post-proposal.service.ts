import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Proposal} from './proposal'
@Injectable({
  providedIn: 'root'
})
export class PostProposalService {
  public sentTeams:{}[]
  private url="http://localhost:8080/proposal/add"
  constructor(private http:HttpClient) { }
  postProposal(data,userId,teams){
    if(!data.key){
      data.key="null"
    }
    data.userId=userId
    console.log(data)
    return this.http.post(this.url,data)
  }
}
