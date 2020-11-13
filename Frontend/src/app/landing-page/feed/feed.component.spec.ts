import { ComponentFixture, ComponentFixtureAutoDetect, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {HttpClientModule} from '@angular/common/http';
import { FeedComponent } from './feed.component';
import {ProposalService} from '../proposal.service';
import { Proposal } from 'src/app/proposal';
import { emit } from 'process';


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

  it('should get all comments',()=>{
    let proposalServe: ProposalService
    proposalServe = TestBed.inject(ProposalService)
    let spy = spyOn(proposalServe,'getComment').and.callThrough()
    component.commentsSetup()
    expect(spy).toHaveBeenCalled()
    expect(component.commentVisibility).toEqual(false)
    expect(component.noComments).toEqual(true)
    expect(component.commentsMessage).toEqual("Comments")
 });

 it('Should delete proposal',()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let spy = spyOn(proposalServe,'deletePost').and.callThrough()
  
  component.delProposal()
   expect(spy).toHaveBeenCalled()

});

it('for the likesetup() method',()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let spy = spyOn(proposalServe,'getLike').and.callThrough()
  component.likeSetup()
  expect(spy).toHaveBeenCalled()
});


it('should post comment',()=>{
  let proposalServe: ProposalService
  proposalServe = TestBed.inject(ProposalService)
  let spy = spyOn(proposalServe,'postComment').and.callThrough()
  component.postComment(7)
  expect(component.commentsSetup()).toHaveBeenCalled
});

});
