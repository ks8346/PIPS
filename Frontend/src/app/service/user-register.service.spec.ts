import { TestBed } from '@angular/core/testing';
import { UserRegisterService } from './user-register.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

describe('UserRegisterService', () => {
  let service: UserRegisterService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(UserRegisterService);
    httpClient=TestBed.inject(HttpClient)
    httpTestingController=TestBed.inject(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it("should get all the teams",()=>{
    const teamList=[{teamName:"Devs"},{teamName:"Sparks"}]
    service.getTeam().subscribe((data)=>expect(data).toEqual(teamList))
  })
});