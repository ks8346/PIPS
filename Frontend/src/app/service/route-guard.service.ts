import { HardcodedAuthenticationService } from './hardcoded-authentication.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService implements CanActivate {

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){      
    if(this.hardcodedAuthenticationService.isUserLoggedIn())
      return true
    else{
      this.router.navigate(['login'])
      return false
    }
  }
  constructor(private hardcodedAuthenticationService:HardcodedAuthenticationService,
    private router:Router){ }
}