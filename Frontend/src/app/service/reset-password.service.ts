import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService {

  constructor(public http: HttpClient) { }
  
  resetPassword(data){
    console.log("inside resetPassword")
    return this.http.put("http://localhost:8080/resetPassword",
    data["data1"],{responseType:'text' as 'json'})
}

resetLink(data){
  console.log("inside forgetPassword")
  return this.http.post("http://localhost:8080/forgotPassword",
  data["data1"],{responseType:'text' as 'json'})
}

tokenVerification(data){
  console.log("inside tokenVerification")
  return this.http.post("http://localhost:8080/validateToken",
  data["data1"],{responseType:'text' as 'json'})
}
}
