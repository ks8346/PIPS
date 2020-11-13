import { TestBed } from '@angular/core/testing';

import { TeamsService } from './teams.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
describe('TeamsService', () => {
  let service: TeamsService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(TeamsService);
    httpClient=TestBed.inject(HttpClient)
    httpTestingController=TestBed.inject(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  
  it("should get all the teams",()=>{
    const teams=[{id:1,name:"Devs"},{id:2,name:"Devs-Frontend"}]
    service.getTeams().subscribe((data)=>expect(data).toEqual(teams))
  })
  
});
