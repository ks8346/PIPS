import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

/**This service is responsible for reset password */
@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService {

  /**@constructor
   * @param http This is the instance of object HttpClient
  */
  constructor(public http: HttpClient) { }

  /**
   * This method helps in fetching the token from the database and sends an error if token is not present in the database.
	 * stores the updated password in the database
	 * @param data new password entered by the user
   */
 
  resetPassword(data){
    console.log("inside resetPassword")
    return this.http.put("http://localhost:8080/resetPassword",
    data["data1"],{responseType:'text' as 'json'})
}
 /**
   * This method is used for verification of valid email id entered by the user, 
	 * generation of the token link and setting up the timer for the same.
   * @param data email registered email of the user who wants to reset the password.
   */
resetLink(data){
  console.log("inside forgetPassword")
  return this.http.post("http://localhost:8080/forgotPassword",
  data["data1"],{responseType:'text' as 'json'})
}
/**
 * This method helps in validating the token whether the session is in continuation or expired 
 * @param data Id of the token 
 */
tokenVerification(data){
  console.log("inside tokenVerification")
  return this.http.post("http://localhost:8080/validateToken",
  data["data1"],{responseType:'text' as 'json'})

}
}
