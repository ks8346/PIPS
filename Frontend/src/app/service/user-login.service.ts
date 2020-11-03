import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class UserLoginService {

  constructor(private http:HttpClient) {
    
   }

   doLogin(data){
    console.log("inside do_register")
    return this.http.post("http://localhost:1998/signin",
    data["data1"],{responseType:'text' as 'json'})
    
    
    // return resp
}
}
