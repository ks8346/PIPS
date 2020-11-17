import { GoogleLoginProvider, SocialAuthService,SocialAuthServiceConfig } from 'angularx-social-login/';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {SocialLoginModule} from 'angularx-social-login'

import { LandingPageComponent } from './landing-page.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { MAT_DIALOG_DATA, MatDialogRef,MatDialogModule,MatDialog } from '@angular/material/dialog';
import {MatMenuModule} from '@angular/material/menu'
import {Overlay} from '@angular/cdk/overlay'
import {User} from '../user';
import {AuthorizationService} from '../authorization.service'
import {GetProposalsService} from '../get-proposals.service'
import {CreateProposalComponent} from './create-proposal/create-proposal.component'
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {By} from '@angular/platform-browser'
import { Router } from '@angular/router';
import {TeamsService} from '../teams.service';
import { FeedComponent } from './feed/feed.component';
import {SpyLocation} from '@angular/common/testing';
import {of, throwError} from 'rxjs';
describe('LandingPageComponent', () => {
  let component: LandingPageComponent;
  let create:CreateProposalComponent;
  let fixture: ComponentFixture<LandingPageComponent>;
  let feed:FeedComponent;
  let fixtureCreate:ComponentFixture<FeedComponent>;
  let httpClient:HttpClient;
  let httpTestingController: HttpTestingController;
  let router:Router;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[RouterTestingModule,
        
        HttpClientTestingModule,MatDialogModule,MatMenuModule,NoopAnimationsModule,SocialLoginModule],
      providers:[
        { provide: 'SocialAuthServiceConfig',
        useValue: {
          autoLogin: false,
          providers: [
            {
              id: GoogleLoginProvider.PROVIDER_ID,
              provider: new GoogleLoginProvider(
                'clientId'
              )
            }
          ]
        } as SocialAuthServiceConfig,},
        {provide:Overlay},
        {provide:MatDialog},
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: {} },
        AuthorizationService,
        GetProposalsService,
        {provide:FeedComponent},
        {provide:SpyLocation},
        SocialLoginModule

      ],
      declarations: [ LandingPageComponent,CreateProposalComponent,FeedComponent ]
    })
    .compileComponents();
    httpClient=TestBed.inject(HttpClient)
    httpTestingController=TestBed.inject(HttpTestingController)
    router=TestBed.inject(Router)
    let team={id:1,name:"Devs"}
    const user=new User(1,"Kartik","ks8346@gmail.com",team)
    let autho:AuthorizationService;
    autho=TestBed.inject(AuthorizationService)
    spyOn(autho, 'authorization').and.returnValue(user);
    let getProposals:GetProposalsService
    getProposals=TestBed.inject(GetProposalsService)
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LandingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    
  });


  it('should create Landing Page', () => {
    let teams:TeamsService
    teams=TestBed.inject(TeamsService)
    let spy=spyOn(teams,"getTeams").and.callThrough()
    let resize=spyOn(component,"resize").and.callThrough()
    component.ngOnInit()
    expect(component).toBeTruthy()
    expect(component.user).toBeTruthy()
    expect(spy).toHaveBeenCalled()
    expect(resize).toHaveBeenCalled()
  });


  it("should flood feed array with all type",()=>{
    let getProposals:GetProposalsService
    fixtureCreate=TestBed.createComponent(FeedComponent)
    feed = fixtureCreate.componentInstance;
    feed.post={
      user:{
        id:1,
        name:"kartik",
        email:"ks@gmail.com",
        teams:{
          id:1,
          name:"Devs"
        }
      },
      description:"this is desc",
      title:"this is title",
      id:1,
      teams:[],
      upvotesCount:10
    }
    component.feed=[feed.post,feed.post]
    getProposals=TestBed.inject(GetProposalsService)
    component.type="allPost"
    let spy=spyOn(getProposals,"getAllPosts").and.returnValue(of(component.feed))
    component.ngOnInit()
    expect(spy).toHaveBeenCalled()
    expect(component.feed).toEqual([feed.post,feed.post])
  })

  it("should flood feed array with team type",()=>{
    let getProposals:GetProposalsService
    fixtureCreate=TestBed.createComponent(FeedComponent)
    feed = fixtureCreate.componentInstance;
    feed.post={
      user:{
        id:1,
        name:"kartik",
        email:"ks@gmail.com",
        teams:{
          id:1,
          name:"Devs"
        }
      },
      description:"this is desc",
      title:"this is title",
      id:1,
      teams:[],
      upvotesCount:10
    }
    component.feed=[feed.post,feed.post]
    getProposals=TestBed.inject(GetProposalsService)
    component.type="teamPost"
    let spy=spyOn(getProposals,"getTeamPosts").and.returnValue(of(component.feed))
    component.ngOnInit()
    expect(spy).toHaveBeenCalled()
    expect(component.feed).toEqual([feed.post,feed.post])
  })

  it("should not flood feed array with team type",()=>{
    let getProposals:GetProposalsService
    let error={status:406}
    getProposals=TestBed.inject(GetProposalsService)
    component.type="teamPost"
    let spy=spyOn(getProposals,"getTeamPosts").and.returnValue(throwError(error))
    component.ngOnInit()
    expect(spy).toHaveBeenCalled()
  })

  it("should not flood feed array with all type",()=>{
    let getProposals:GetProposalsService
    let error={status:406}
    getProposals=TestBed.inject(GetProposalsService)
    component.type="allPost"
    let spy=spyOn(getProposals,"getAllPosts").and.returnValue(throwError(error))
    component.ngOnInit()
    expect(spy).toHaveBeenCalled()
  })


  it("should flood feed array with your type",()=>{
    let getProposals:GetProposalsService
    fixtureCreate=TestBed.createComponent(FeedComponent)
    feed = fixtureCreate.componentInstance;
    feed.post={
      user:{
        id:1,
        name:"kartik",
        email:"ks@gmail.com",
        teams:{
          id:1,
          name:"Devs"
        }
      },
      description:"this is desc",
      title:"this is title",
      id:1,
      teams:[],
      upvotesCount:10
    }
    component.feed=[feed.post,feed.post]
    getProposals=TestBed.inject(GetProposalsService)
    component.type="yourPost"
    let spy=spyOn(getProposals,"getYourPosts").and.returnValue(of(component.feed))
    component.ngOnInit()
    expect(spy).toHaveBeenCalled()
    expect(component.feed).toEqual([feed.post,feed.post])
  })

  it("should not flood feed array with your type",()=>{
    let getProposals:GetProposalsService
    let error={status:406}
    getProposals=TestBed.inject(GetProposalsService)
    component.type="yourPost"
    let spy=spyOn(getProposals,"getYourPosts").and.returnValue(throwError(error))
    component.ngOnInit()
    expect(spy).toHaveBeenCalled()
  })
  

  it("should filter the data",()=>{
    const data=[new Date("2020/10/05"),new Date("2020/10/11")]
    component.onFilter(data)
    expect(component.data.startDate).toEqual(new Date("2020/10/05"))
  })

  it("should filter the data ",()=>{
    let data="allPost"
    component.onFilter(data)
    expect(component.type).toEqual("allPost")
    data="teamPost"
    component.onFilter(data)
    expect(component.type).toEqual("teamPost")
    data="yourPost"
    component.onFilter(data)
    expect(component.type).toEqual("yourPost")
  })
  


  it("should select Your Post api",()=>{
    let data="yourPost"
    let spy=spyOn(component,"getYour").and.callThrough()
    component.selectApi(data)
    expect(spy).toHaveBeenCalled()
    data="teamPost"
    spy=spyOn(component,"getTeam").and.callThrough()
    component.selectApi(data)
    expect(spy).toHaveBeenCalled()
    data="allPost"
    spy=spyOn(component,"getAll").and.callThrough()
    component.selectApi(data)
    expect(spy).toHaveBeenCalled()
  })

  
  it("should show menu",()=>{
    component.showMenu()
    expect(component.menuVisibility).toEqual(false)
    component.menuVisibility=false
    component.showMenu()
    expect(component.menuVisibility).toEqual(true)
  })

  it("should handle error",()=>{
    let error={
      status:406
    }
    component.errorHandling(error)
    expect(component.morePost).toBeFalse()
    expect(component.endMessage).toEqual("There aren't any more proposals to show")
    error={
      status:200
    }
    component.errorHandling(error)
    expect(component.endMessage).toEqual("")
  })

  it("should open share dialog",()=>{
    feed=TestBed.inject(FeedComponent)
    fixtureCreate=TestBed.createComponent(FeedComponent)
    feed = fixtureCreate.componentInstance;
    feed.post={
      user:{
        id:1,
        name:"kartik",
        email:"ks@gmail.com",
        teams:{
          id:1,
          name:"Devs"
        }
      },
      description:"this is desc",
      title:"this is title",
      id:1,
      teams:[],
      upvotesCount:10
    }
    component.feed=[feed.post,feed.post]
    let spyFeed=spyOn(component,"openDialogshare")
    fixture.detectChanges()
    fixture.debugElement.query(By.css('.feed')).triggerEventHandler("share",feed.post)
    fixture.detectChanges()
    expect(spyFeed).toHaveBeenCalled()
  })

  it("should open Dialog",()=>{
    spyOn(component.dialog,"open")
    let button = fixture.debugElement.query(By.css('.postButton')).nativeElement
    button.click();
    expect(component.dialog.open).toHaveBeenCalled()
  })

  // it("should open Dialog",()=>{
  //   spyOn(component.dialog,"open")
  //   spyOn(component.dialog,"open")
  //   component.openDialogshare(feed.post)
  //   expect(component.dialog.open).toHaveBeenCalled()
  // })

  it("should run delete proposal",()=>{
    feed=TestBed.inject(FeedComponent)
    fixtureCreate=TestBed.createComponent(FeedComponent)
    feed = fixtureCreate.componentInstance;
    feed.post={
      user:{
        id:1,
        name:"kartik",
        email:"ks@gmail.com",
        teams:{
          id:1,
          name:"Devs"
        }
      },
      description:"this is desc",
      title:"this is title",
      id:1,
      teams:[],
      upvotesCount:10
    }
    component.feed=[feed.post,feed.post]
    let spyFeed=spyOn(feed,"delProposal")
    fixtureCreate.debugElement.query(By.css('.delete')).nativeElement.click()
    expect(spyFeed).toHaveBeenCalled()
    
    let spy=spyOn(component,"deleteProposal")
    fixture.detectChanges()
    fixture.debugElement.query(By.css('.feed')).triggerEventHandler("deleteProposal",feed.post.id)
    expect(spy).toHaveBeenCalled()
    
    component.feed=[]
    let feedPost=fixture.debugElement.query(By.css('.feed'))
    expect(feedPost.triggerEventHandler('deleteProposal',feed.post.id)).toBeFalsy()
  })

  it("window resize should run onResize",()=>{
    let spy=spyOn(component,"onResize")
    window.dispatchEvent(new Event('resize'));
    expect(spy).toHaveBeenCalled()
  })

  it("resize should be called",()=>{
    let resize=spyOn(component,"resize")
    window.dispatchEvent(new Event('resize'));
    expect(resize).toHaveBeenCalled()
    // fixture.debugElement.nativeElement.dispatchEvent(new Event('resize'),{event:{target:{innerWidth:900}}})
    // fixture.detectChanges()
    // expect(resize).toHaveBeenCalled()
    // expect(component.menuButton).toBeTrue()
  })

  it("should destroy session ",()=>{
    let autho:AuthorizationService;
    let authorization:SocialAuthService
    let flag=false
    autho=TestBed.inject(AuthorizationService)
    authorization=TestBed.inject(SocialAuthService)
    let spy=spyOn(autho,"clearSession").and.callThrough()
    let authSpy=spyOn(authorization,"signOut").and.callThrough()
    component.destroySession()

    expect(spy).toHaveBeenCalled()
    expect(authSpy).toHaveBeenCalled()
  })

  it("should destroy session on click",()=>{
    let location:SpyLocation
    location=TestBed.inject(SpyLocation)
    let autho:AuthorizationService;
    autho=TestBed.inject(AuthorizationService)
    location.setBaseHref("http://localhost:4200")
    location.setInitialPath('/welcome')
    let button = fixture.debugElement.query(By.css('.profile')).nativeElement
    button.click();
    let log=fixture.debugElement.query(By.css('.logout')).nativeElement
    log.click()
    let spy=spyOn(autho,"clearSession").and.callThrough()
    component.destroySession()
    expect(spy).toHaveBeenCalled()
  })

  it("should get new posts when scroll",()=>{
    let getProposals:GetProposalsService
    getProposals=TestBed.inject(GetProposalsService)
    component.type="yourPost"
    component.newFeed=["this is a post","this is second post"]
    component.morePost=true
    component.page=0
    let spy=spyOn(getProposals,"getYourNextPost").and.callThrough()
    component.onScroll()
    expect(spy).toHaveBeenCalled()
    component.type="teamPost"
    component.newFeed=["this is a post","this is second post"]
    component.morePost=true
    component.page=0
    component.onScroll()
    spy=spyOn(getProposals,"getTeamNextPost").and.callThrough()
    component.type="allPost"
    component.newFeed=["this is a post","this is second post"]
    component.morePost=true
    component.page=0
    component.onScroll()
    spy=spyOn(getProposals,"getAllNextPost").and.callThrough()
  })
  

});
