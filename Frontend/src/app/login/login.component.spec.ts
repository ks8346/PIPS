import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule,MatDialog} from '@angular/material/dialog';
import { LoginComponent } from './login.component';
import { UserLoginService } from '../service/user-login.service';
import { GoogleLoginProvider, SocialAuthService, SocialAuthServiceConfig, SocialLoginModule, SocialUser } from 'angularx-social-login';
import { User } from '../user';
import { Location} from '@angular/common';
import { of, throwError } from 'rxjs';
import { SocialMediaAuthService } from '../service/social-media-auth.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        RouterTestingModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        MatDialogModule,
        SocialLoginModule
      ],
      declarations: [ LoginComponent],
      providers:[ 
        UserLoginService,
        SocialAuthService,
        {
          provide: 'SocialAuthServiceConfig',
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
          } as SocialAuthServiceConfig,
        }
      ]
    });
    
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  })

  it('login should be successful',()=>{
    component.handleLogin();
    if(component.loginForm.valid){
      expect(component.loginSuccess).toBe(false);
      expect(component.invalidLogin).toBe(true);
    }   
  })

  it("should open Dialog",()=>{
    spyOn(component.dialog,"open")
    component.openDialog();
    expect(component.dialog.open).toHaveBeenCalled()
  })

  it('should go to landing page',()=>{
    component.handleLogin();
    const location: Location = TestBed.inject(Location);
    if(component.loginForm.valid){
      expect(location.path()).toBe('/welcome');
    }
  })

  it('should call functions',()=>{
    component.googleSignIn();
    expect(component.socialMedia()).toHaveBeenCalled()
  })


  it('login should not be successful', () => {
    let email = component.loginForm.controls.email;
    let password = component.loginForm.controls.password;
    email.setValue("ankit@gmail.com")
    password.setValue("Qwerty@123")
    let loginservice = TestBed.inject(UserLoginService)
    let error = {status : 200}
    
    spyOn(loginservice,'doLogin').and.returnValue(throwError(error));
    component.handleLogin();

    expect(component.invalidLogin).toBe(false);
    expect(component.loginSuccess).toBe(true);
  });

  it('login should be successful', () => {
    let email = component.loginForm.controls.email;
    let password = component.loginForm.controls.password;
    email.setValue("ankit@gmail.com")
    password.setValue("Qwerty@123")
    let loginservice = TestBed.inject(UserLoginService)
    let response: void;
    
    spyOn(loginservice,'doLogin').and.returnValue(of(response));
    component.handleLogin();

    expect(component.invalidLogin).toBe(false);
    expect(component.loginSuccess).toBe(true);
  });

  // it('social login should redirect to team', () => {
  //   let loginservice = TestBed.inject(SocialMediaAuthService)
  //   let error = {status : 406}
  //   component.user = new SocialUser{name: 'ankit',email:'ankit@gmail.com'}
    
  //   spyOn(loginservice,'socialMedia').and.returnValue(throwError(error));
  //   component.socialMedia();
  //   const location: Location = TestBed.inject(Location);
  //   expect(location.path()).toBe('/team');
  // });

  // it('social login should directly go to landing page', () => {
  //   let loginservice = TestBed.inject(SocialMediaAuthService)
  //   let response: void;
  //   component.user = {name: 'ankit',email:'ankit@gmail.com'}
    
  //   spyOn(loginservice,'socialMedia').and.returnValue(of(response));
  //   component.socialMedia();

  //   expect(component.invalidLogin).toBe(false);
  //   expect(component.loginSuccess).toBe(true);
  // });

}); 