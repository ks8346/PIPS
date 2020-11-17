import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs';

import { ProposalService } from './proposal.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
describe('ProposalService', () => {
  let service: ProposalService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(ProposalService);
    httpClient=TestBed.inject(HttpClient)
    httpTestingController=TestBed.inject(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should send a comment to db', () => {
    service.postComment(1,"new Comment",2).subscribe((data)=>expect(data).toEqual("SUCCESS"),fail)
  });
  it('should send a Like to db', () => {
    service.postLike(1,2).subscribe((data)=>expect(data).toEqual("SUCCESS"),fail)
  });
  it("should send a dislike to db",()=>{
    service.postDislike(1,2).subscribe((data)=>expect(data).toEqual("SUCCESS"),fail)
  })
  it("should get a boolean of liked or disliked",()=>{
    service.getLike(1,2).subscribe((data)=>expect(data).toBe(Boolean),fail)
  })
  it("should get an array of comments",()=>{
    service.getComment(1).subscribe((data)=>expect(data).toBe([]),fail)
  })
  it("should delete a comment",()=>{
    service.deleteComment(1).subscribe((data)=>expect(data).toBe("SUCCESS"),fail)
  })
  it("should delete a post",()=>{
    service.deletePost(1).subscribe((data)=>expect(data).toBe("SUCCESS"),fail)
  })
});
