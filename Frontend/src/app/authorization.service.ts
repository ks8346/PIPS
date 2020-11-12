import { Injectable } from '@angular/core';
import {Router} from '@angular/router'
@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  authenticatedUser:string
  constructor(private router:Router) { }
  authorization(){
  this.authenticatedUser=sessionStorage.getItem('authenticatedUser')
    if(!this.authenticatedUser){
      this.router.navigate(['/home'])
    }
    return JSON.parse(this.authenticatedUser)
  }
}
