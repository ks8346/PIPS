import { ComponentFixture, ComponentFixtureAutoDetect, fakeAsync, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {HttpClientModule} from '@angular/common/http';
import { FeedComponent } from './feed.component';
import {ProposalService} from '../proposal.service';
import { Proposal } from 'src/app/proposal';
import { emit } from 'process';
import {of, throwError} from 'rxjs'
import {Comment} from '../comment'
import { error } from 'protractor';

describe('FeedComponent', () => {
  let component: FeedComponent;
  let fixture: ComponentFixture<FeedComponent>;
 

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      declarations: [ FeedComponent ]
    })
    .compileComponents();
    let proposalServe: ProposalService
    
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedComponent);
    component = fixture.componentInstance;
    component.post = {
      id:16,
      description :'this is description',
      title:'this is title',
      teams: [],
      upvotesCount:20,
      user:{
                id: 1,
                name: 'priyank',
                email: 'priyank@gmail.com',
                teams: {
                    id: 2,
                    name: 'dev',
                      }
           }
         };

         component.userId = 1
         component.type = "allPost"
         fixture.detectChanges();
  });

  it('should create feed Component', () => {
    expect(component).toBeTruthy();
  });

  it('should emit when we delete a comment', () => {
    component.openDialogshare()
    spyOn(component.share, 'emit').and.callThrough();
    
  });

  it('should upvote', () => {
    component.hasLiked=false
    
    let proposalServe:ProposalService
    proposalServe=TestBed.inject(ProposalService)
    let spy = spyOn(proposalServe,'postLike').and.callThrough()
    component.postLike(7)
    expect(spy).toHaveBeenCalled()
  });


  it('emit data',()=>{
    component.openDialog(component.post)
    spyOn(component.update, 'emit').and.callThrough();
  });

  it('should delete comment',()=>{
    let proposalServe:ProposalService
    proposalServe=TestBed.inject(ProposalService)
    let spy = spyOn(proposalServe, 'deleteComment').and.callThrough();
    component.onDelete(6)
    expect(spy).toHaveBeenCalled();    
  });

  it('should update approval',()=>{
     component.updateApproval()
     expect(component.canUpdate).toBe(true)

  });

  it('should get 2 comments',fakeAsync(()=>{
    let proposalServe: ProposalService
    proposalServe = TestBed.inject(ProposalService)
    let data:Comment[]=[{id:1,comment:"this is comment",creationDate:new Date("2020/10/12"),user:{id:1,name:"kartik"}},
    
      {id:2,comment:"this is second comment",creationDate:new Date("2020/10/12"),user:{id:1,name:"kartik"}},
    ]
    let spy = spyOn(proposalServe,'getComment').and.returnValue(of(data))
    component.commentsSetup()
    expect(spy).toHaveBeenCalled()
    expect(component.comments.length).toEqual(2)
    expect(component.commentError).toEqual("")
    expect(component.commentVisibility).toEqual(false)
    expect(component.noComments).toEqual(true)
    expect(component.commentsMessage).toEqual("Comments")
 }));

 it('should get 1 comment',fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let data:Comment[]=[{id:1,comment:"this is comment",creationDate:new Date("2020/10/12"),user:{id:1,name:"kartik"}}]
  let spy = spyOn(proposalServe,'getComment').and.returnValue(of(data))
  component.commentsSetup()
  expect(spy).toHaveBeenCalled()
  expect(component.comments.length).toEqual(1)
  expect(component.commentError).toEqual("")
  expect(component.commentVisibility).toEqual(true)
  expect(component.noComments).toEqual(true)
  expect(component.commentsMessage).toEqual("Comments")
}));

it('should get 0 comment',fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let data=null
  let spy = spyOn(proposalServe,'getComment').and.returnValue(of(data))
  component.commentsSetup()
  expect(spy).toHaveBeenCalled()
  expect(component.commentError).toEqual("")
  expect(component.commentVisibility).toEqual(true)
  expect(component.noComments).toEqual(false)
  expect(component.commentsMessage).toEqual("No comments on this post yet")
}));

it('should get error',fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:406}
  let spy = spyOn(proposalServe,'getComment').and.returnValue(throwError(error))
  component.commentsSetup()
  expect(spy).toHaveBeenCalled()
  expect(component.commentError).toEqual("Some error has occured retrieving the comments please reload")
}));


 it('Should delete proposal',()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:200}
  let spy = spyOn(proposalServe,'deletePost').and.returnValue(throwError(error))
  let spyEvent=spyOn(component.deleteProposal,"emit")
  component.delProposal()
  expect(spy).toHaveBeenCalled()
  expect(spyEvent).toHaveBeenCalled()

});

it('Should not delete proposal',()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:406}
  let spy = spyOn(proposalServe,'deletePost').and.returnValue(throwError(error))
  let spyEvent=spyOn(component.deleteProposal,"emit")
  component.delProposal()
  expect(spy).toHaveBeenCalled()
  expect(spyEvent).not.toHaveBeenCalled()

});

it('for the likesetup() method',fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let like=true
  let spy = spyOn(proposalServe,'getLike').and.returnValue(of(like))
  component.likeSetup()
  expect(spy).toHaveBeenCalled()
  expect(component.hasLiked).toBeTrue()
}));


it('should post comment',fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:200}
  let spy = spyOn(proposalServe,'postComment').and.returnValue(throwError(error))
  let spySetup = spyOn(component,'commentsSetup')
  component.postComment(7)
  expect(spySetup).toHaveBeenCalled()
  expect(component.commentVisibility).toEqual(true)
  expect(component.noComments).toEqual(true)
  expect(component.commentsMessage).toEqual("Comments")
}));

it('should post comment',fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:200}
  let spy = spyOn(proposalServe,'postComment').and.returnValue(of(throwError(error)))
  let spySetup = spyOn(component,'commentsSetup')
  component.postComment(7)
  expect(spySetup).toHaveBeenCalled()
  expect(component.commentVisibility).toEqual(true)
  expect(component.noComments).toEqual(true)
  expect(component.commentsMessage).toEqual("Comments")
}));

it("should post a like",fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:200}
  component.hasLiked=false
  let spy = spyOn(proposalServe,'postLike').and.returnValue(of(throwError(error)))
  component.postLike(7)
  expect(spy).toHaveBeenCalled()
  expect(component.hasLiked).toBeTrue()
}));

it("should post a like",fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:200}
  component.hasLiked=false
  let spy = spyOn(proposalServe,'postLike').and.returnValue(throwError(error))
  component.postLike(7)
  expect(spy).toHaveBeenCalled()
  expect(component.hasLiked).toBeTrue()
}));

it("should post a dislike",fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:200}
  component.hasLiked=true
  let spy = spyOn(proposalServe,'postDislike').and.returnValue(of(throwError(error)))
  component.postLike(7)
  expect(spy).toHaveBeenCalled()
  expect(component.hasLiked).toBeFalse()
}));

it("should post a dislike",fakeAsync(()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let error={status:200}
  component.hasLiked=true
  let spy = spyOn(proposalServe,'postDislike').and.returnValue(throwError(error))
  component.postLike(7)
  expect(spy).toHaveBeenCalled()
  expect(component.hasLiked).toBeFalse()
}));

});
