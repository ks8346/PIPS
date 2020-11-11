import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import {HttpClientModule} from '@angular/common/http';
import { FeedComponent } from './feed.component';
import {ProposalService} from '../proposal.service';
import { Proposal } from 'src/app/proposal';


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
      id:7,
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
    component.postLike(component.post.id)
    expect(component.hasLiked).toBe(false)
  });


  it('emit data',()=>{
    component.openDialog(component.post)
    spyOn(component.update, 'emit').and.callThrough();
  });

  it('should delete comment',()=>{
    component.onDelete(6)
    spyOn(component.update, 'emit').and.callThrough();
  });

  it('should update approval',()=>{
     component.updateApproval()
     expect(component.canUpdate).toBe(true)

  });

  it('should get all comments',()=>{
    let proposalServe: ProposalService
    proposalServe = TestBed.inject(ProposalService)
    spyOn(proposalServe,'getComment').and.callThrough()
 });


});
