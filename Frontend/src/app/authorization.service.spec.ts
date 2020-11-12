import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing'
import { AuthorizationService } from './authorization.service';
import {SpyLocation} from '@angular/common/testing'
import {User} from './user'
describe('AuthorizationService', () => {
  let service: AuthorizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[RouterTestingModule],
      providers:[
        { provide: Location, useClass: SpyLocation },
        {provide:SpyLocation}
      ]
    });
    service = TestBed.inject(AuthorizationService);
    let location:SpyLocation
    location=TestBed.inject(SpyLocation)
    location.setBaseHref("http://localhost:4200/")
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it("should create session",()=>{
    let team={id:1,name:"Devs"}
    const user=new User(1,"Kartik","ks8346@gmail.com",team)
    let spy=spyOn(sessionStorage,"getItem").and.returnValue(JSON.stringify(user))
    service.authorization()
    expect(spy).toHaveBeenCalled()
  })

  it("should not create session and relocate to home",()=>{
    let location:SpyLocation
    location=TestBed.inject(SpyLocation)
    spyOn(sessionStorage,"getItem").and.returnValue(null)
    service.authorization()
    expect(location.path()).toEqual('')
  })

  it("should clear session",()=>{
    let spy=spyOn(sessionStorage,"clear")
    service.clearSession()
    expect(spy).toHaveBeenCalled()
  })
});
