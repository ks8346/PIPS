import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { teamList } from './../teamList';
import {Observable} from 'rxjs';

/**This service handles http requests for registration process */
@Injectable({
  providedIn: 'root'
})
export class UserRegisterService {

  /**@constructor
   * @param http This is the instance of object HttpClient
  */
  constructor(private http:HttpClient) {
    
   }
/**
 * This method returns the response message which shows whether the user has successfully registered or not.
 * @param data 
 */
   doRegister(data){
    console.log("inside do_register")
    return this.http.post("http://localhost:8080/userRegister",
    data["data1"],{responseType:'text' as 'json'})
 // return resp
}
/**
 * This method returns list of the team which are already exist in our database.
 */
getTeam():Observable<teamList[]>{
  console.log("inside GetTeam")
  return this.http.get<teamList[]>("http://localhost:8080/getTeam")
  
}
}
