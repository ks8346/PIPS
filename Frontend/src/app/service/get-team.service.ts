import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
export class ResponseAPI{
  constructor(public message:string[]){}
}

@Injectable({
  providedIn: 'root'
})
export class GetTeamService {

  constructor(private http:HttpClient) {
    
   }

   getTeam(){
    console.log("inside GetTeam")
    let arr =this.http.get<ResponseAPI>("http://localhost:8080/getTeam")
    console.log(arr)
    return arr
    
    
    // return resp
}
}

