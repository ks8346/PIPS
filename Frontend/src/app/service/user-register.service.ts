import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class UserRegisterService {

  constructor(private http:HttpClient) {
    
   }

   doRegister(data){
    console.log("inside do_register")
    return this.http.post("http://localhost:8080/userRegister",
    data["data1"],{responseType:'text' as 'json'})
    
    
    // return resp
}
}
