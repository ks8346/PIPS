import { TestBed } from '@angular/core/testing';
import {Proposal} from './proposal' ;
import { PostProposalService } from './post-proposal.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
describe('PostProposalService', () => {
  let service: PostProposalService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(PostProposalService);
  });
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should be sent in proposal format', () => {
    const data={value:{
      key : "null",
      title : "this is a title",
      description : "This is a description",
      userId : "2",
      teams : [
          {
              id : "1",
              name : "DevsFrontend"
          },
          {
              id : "2",
              name : "DevsBackend"
          }
      ]},
      key:null
    }
    service.postProposal(data,2).subscribe(
      (result)=>expect(result).toEqual(data),fail
    )
  });
});
