import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { SocialAuthServiceConfig,SocialAuthService, GoogleLoginProvider } from 'angularx-social-login';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamComponent } from './team.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GetTeamService } from '../service/get-team.service';
import { AppRoutingModule } from '../app-routing.module';
import { RouterTestingModule } from '@angular/router/testing';
import { MatSelectModule } from '@angular/material/select';
import { NoopAnimationPlayer } from '@angular/animations';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { compileComponentFromMetadata } from '@angular/compiler';
describe('TeamComponent', () => {
  let component: TeamComponent;
  let fixture: ComponentFixture<TeamComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let social:SocialAuthService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        RouterTestingModule,
        AppRoutingModule,
        MatSelectModule,
        NoopAnimationsModule,MatDialogModule,
        
      ],
      declarations: [ TeamComponent ],
      providers: [{ provide: 'SocialAuthServiceConfig',
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
      } as SocialAuthServiceConfig,},GetTeamService,SocialAuthService]
    })
    .compileComponents();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    social=TestBed.inject(SocialAuthService)
    fixture = TestBed.createComponent(TeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.ngOnInit();
    component.userData={
      name:"Kartik",
      email:"ks@gmail.com"
    }
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('team should be selected',()=>{
    let team = component.teamForm.controls.team;
    team.setValue("");
    expect(team.hasError('required', ['required'])).toBeTruthy;
    team.setValue("Sparks");
    expect(team.hasError('required', ['required'])).toBeFalsy;
  });
  it('loading status changed',()=>{
    let x = component.teamForm.valid;
    component.onSubmit();
    if(x){
      expect(component.loading).toBe(true);
    }
    else{
      expect(component.loading).toBe(false);
    }
  })
  it('submitted status changed',()=>{
    component.onSubmit();
    expect(component.submitted).toBe(true);
  })
});