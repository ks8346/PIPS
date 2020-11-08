import { TestBed } from '@angular/core/testing';
import { Feed } from './feed';
let httpClientSpy: { get: jasmine.Spy };
import { GetProposalsService } from './get-proposals.service';
import {of} from 'rxjs';
import {FeedParams} from './feed-params'
describe('GetProposalsService', () => {
  let service: GetProposalsService;

  beforeEach(() => {
    httpClientSpy=jasmine.createSpyObj("HttpClient",["get"])
    TestBed.configureTestingModule({});
    service=new GetProposalsService(httpClientSpy as any)
    service = TestBed.inject(GetProposalsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should send an array of posts', () => {
    const data=new FeedParams(new Date("2020-10-25"),new Date("2020-11-06"), "0","10");
    let expectedFeed:Feed[]
    httpClientSpy.get.and.returnValue(of(expectedFeed));
    service.getAllPosts(data).subscribe(
      (feed)=>expect(feed).toBe(expectedFeed,"expected Feed"),fail
    )
  });
  it('On scroll should send an array of next posts', () => {
    expect(service.getAllNextPost).toBe([Feed]);
  });
  it('should send an array of posts', () => {
    expect(service.getTeamPosts).toBe([Feed]);
  });
  it('On scroll should send an array of next posts', () => {
    expect(service.getTeamNextPost).toBe([Feed]);
  });
  it('should send an array of posts', () => {
    expect(service.getYourPosts).toBe([Feed]);
  });
  it('On scroll should send an array of next posts', () => {
    expect(service.getYourNextPost).toBe([Feed]);
  });
});
