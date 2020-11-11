import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SocialMediaAuthService {
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
  public username: String;

  public res;
  constructor(private http: HttpClient) { }

  socialMedia(data){
    console.log("inside socialMedia(Frontend)")
    return this.http.post("http://localhost:8080/user/fbTeam",data["data1"],{responseType:'text' as 'json'})
  }

  // socialMedia(data) {
  //   return this.http.post(`http://localhost:8080/fbTeam`,data).pipe(map((res) => {
  //       this.res=res;
  //       this.username = res["username"];
  //       this.registerSuccessfulLogin(this.username);
  //     }));
  // }

  createBasicAuthToken(username: String) {
    return 'Basic ' + (username)
  }

  registerSuccessfulLogin(username) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, this.res.message)
  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this.username = null;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return false
    return true
  }

  getLoggedInUserName() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }
}
