import { teamList } from './../teamList';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class GetTeamService {

  constructor(private http:HttpClient) {
    
   }

   getTeam():Observable<teamList[]>{
    console.log("inside GetTeam")
    return this.http.get<teamList[]>("http://localhost:8080/getTeam")
    
    
    
    // return resp
}
}

