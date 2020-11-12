import { TestBed } from '@angular/core/testing';
import { GetTeamService } from './get-team.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

describe('GetTeamService', () => {
  let service: GetTeamService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(GetTeamService);
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