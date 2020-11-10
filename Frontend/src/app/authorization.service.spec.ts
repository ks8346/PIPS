import { TestBed } from '@angular/core/testing';

import { AuthorizationService } from './authorization.service';
import {User} from './user'
import {RouterTestingModule} from '@angular/router/testing'
describe('AuthorizationService', () => {
  let service: AuthorizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[RouterTestingModule]
    });
    service = TestBed.inject(AuthorizationService);
    let team={id:1,name:"Devs"}
    const user=new User(1,"Kartik","ks8346@gmail.com",team)
    spyOn(sessionStorage,"getItem").and.returnValue(
      JSON.stringify(user)
    )
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  
  it('should create session',()=>{
    service.authorization()
    expect(service.authenticatedUser).toBeInstanceOf(User)
  })
});
