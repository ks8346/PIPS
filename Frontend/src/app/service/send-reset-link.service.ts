import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class SendResetLinkService {

  constructor(public http: HttpClient) { }

  resetLink(data){
    console.log("inside forgetPassword")
    return this.http.post("http://localhost:8080/forgotPassword",
    data["data1"],{responseType:'text' as 'json'})
}
}
