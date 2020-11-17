import { Injectable } from '@angular/core';
import {Router} from '@angular/router'
@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  /**user object as a string*/
  authenticatedUser:string
  constructor(
    /**@ignore */
    private router:Router) { }
  /**
   * function returns the session data as object and if there is no session takes user to login page
  */
  authorization(){
  this.authenticatedUser=sessionStorage.getItem('authenticatedUser')
    if(!this.authenticatedUser){
      this.router.navigate([''])
    }
    return JSON.parse(this.authenticatedUser)
  }
  /**
   * this function destroys the session
  */
  clearSession(){
    sessionStorage.clear()
  }
}
