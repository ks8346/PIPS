import { ComponentFixture, TestBed } from '@angular/core/testing';

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
import { getMultipleValuesInSingleSelectionError } from '@angular/cdk/collections';
describe('LandingPageComponent', () => {
  let component: LandingPageComponent;
  let fixture: ComponentFixture<LandingPageComponent>;
  let httpClient:HttpClient;
  let httpTestingController: HttpTestingController;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[RouterTestingModule,HttpClientTestingModule,MatDialogModule,MatMenuModule],
      providers:[
        {provide:Overlay},
        {provide:MatDialog},
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: {} },
        AuthorizationService
      ],
      declarations: [ LandingPageComponent ]
    })
    .compileComponents();
    httpClient=TestBed.inject(HttpClient)
    httpTestingController=TestBed.inject(HttpTestingController)
    let team={id:1,name:"Devs"}
    const user=new User(1,"Kartik","ks8346@gmail.com",team)
    let autho:AuthorizationService;
    autho=TestBed.inject(AuthorizationService)
    spyOn(autho, 'authorization').and.returnValue(user);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LandingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should create Landing Page', () => {
    expect(component).toBeTruthy()
  });

  it('should create session', () => {
    expect(component.user).toBeTruthy()
  });

  it("should flood feed array",()=>{
    let get:GetProposalsService
    spyOn(get,"getAllPosts").and.callThrough()
  })

  it("should flood feed array",()=>{
    spyOn(component,"getTeam").and.callThrough()
  })

  it("should filter the data",()=>{
    const data=[new Date(),new Date()]
    component.onFilter(data)
    expect(component.data.startDate).toEqual(new Date())
  })
});
