import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { GetProposalsService } from './get-proposals.service';
import {FeedParams} from './feed-params'
import { Post } from './post';
describe('GetProposalsService', () => {
  let service: GetProposalsService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(GetProposalsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should send an array of all posts', () => {
    const feedData=new FeedParams(new Date("2020-10-25"),new Date("2020-11-06"), "0","10");
    service.getAllPosts(feedData).subscribe(
      (data)=>{
        for(let i of data)
          expect(i).toBeInstanceOf(Post)
      },
      fail
    )
  });
  it('On scroll should send an array of all next posts', () => {
    const feedData=new FeedParams(new Date("2020-10-25"),new Date("2020-11-06"), "0","10");
    service.getAllNextPost(feedData).subscribe(
      (data)=>{
        for(let i of data)
          expect(i).toBeInstanceOf(Post)
      },
      fail
    )
  });
  it('should send an array of team posts', () => {
    const feedData=new FeedParams(new Date("2020-10-25"),new Date("2020-11-06"), "0","10");
    service.getTeamPosts(feedData,1).subscribe(
      (data)=>{
        for(let i of data)
          expect(i).toBeInstanceOf(Post)
      },
      fail
    )
  });
  it('On scroll should send an array of team next posts', () => {
    const feedData=new FeedParams(new Date("2020-10-25"),new Date("2020-11-06"), "0","10");
    service.getTeamNextPost(feedData,1).subscribe(
      (data)=>{
        for(let i of data)
          expect(i).toBeInstanceOf(Post)
      },
      fail
    )
  });
  it('should send an array of your posts', () => {
    const feedData=new FeedParams(new Date("2020-10-25"),new Date("2020-11-06"), "0","10");
    service.getYourPosts(feedData,1).subscribe(
      (data)=>{
        for(let i of data)
          expect(i).toBeInstanceOf(Post)
      },
      fail
    )
  });
  it('On scroll should send an array of your next posts', () => {
    const feedData=new FeedParams(new Date("2020-10-25"),new Date("2020-11-06"), "0","10");
    service.getYourNextPost(feedData,1).subscribe(
      (data)=>{
        for(let i of data)
          expect(i).toBeInstanceOf(Post)
      },
      fail
    )
  });
});
