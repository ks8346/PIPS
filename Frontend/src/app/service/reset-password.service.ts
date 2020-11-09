import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService {

  constructor(public http: HttpClient) { }
  
  resetPassword(data){
    console.log("inside resetPassword")
    return this.http.put("http://localhost:8080/reset_password",
    data["data1"],{responseType:'text' as 'json'})
}
}
