import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LandingPageComponent } from './landing-page.component';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { MAT_DIALOG_DATA, MatDialogRef,MatDialogModule,MatDialog } from '@angular/material/dialog';
import {MatMenuModule} from '@angular/material/menu'
import {Overlay} from '@angular/cdk/overlay'
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
        { provide: MatDialogRef, useValue: {} }],
      declarations: [ LandingPageComponent ]
    })
    .compileComponents();
    httpClient=TestBed.inject(HttpClient)
    httpTestingController=TestBed.inject(HttpTestingController)
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LandingPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    let store={User:{
      id:1,
      name:"Kartik",
      email:"ks8346@gmail.com",
      team:{
        id:1,
        name:"Devs"
      }
    }}
    spyOn(sessionStorage, 'getItem').and.callFake((User) => {
      return store[User];
    });
  });

  it('should create Session', () => {
    expect(component.User).toBeTruthy();
  });

  it("should flood feed array",()=>{
    component.getAll()
    expect(component.feed.length).toBeGreaterThan(0)
  })

  it("should flood feed array",()=>{
    component.getTeam()
    expect(component.feed.length).toBeGreaterThan(0)
  })

  it("should filter the data",()=>{
    const data=[new Date(),new Date()]
    component.onFilter(data)
    expect(component.data.startDate).toEqual(new Date())
  })
});
