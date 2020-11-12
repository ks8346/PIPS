import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule,MatDialog} from '@angular/material/dialog';
import { LoginComponent } from './login.component';
import { UserLoginService } from '../service/user-login.service';
import { GoogleLoginProvider, SocialAuthService } from 'angularx-social-login';
import { SocialLoginModule, SocialAuthServiceConfig } from "angularx-social-login";


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
      declarations: [ LoginComponent ],
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
    })
    .compileComponents();
    
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('invalid form when blank', () => {
    expect(component.loginForm.valid).toBeFalsy();
  });

  it('Every field Required',()=>{
    let email = component.loginForm.controls.email;
    let password = component.loginForm.controls.password;

    email.setValue('');
    password.setValue('');

    expect(email.hasError('required', ['required'])).toBeTruthy;
    expect(password.hasError('required', ['required'])).toBeTruthy;

    email.setValue('ankit@gmail.com');
    password.setValue('Qwerty@123');

    expect(email.hasError('required', ['required'])).toBeFalsy;
    expect(password.hasError('required', ['required'])).toBeFalsy;

  })

  it('email pattern validity', () => {
    let errors = {};
    let email = component.loginForm.controls.email;
    expect(email.valid).toBeFalsy()

    errors = email.errors || {};
    expect(errors['required']).toBeTruthy();

    email.setValue("test");
    errors = email.errors||{};
    expect(errors['email']).toBeTruthy();

    email.setValue("test@example.com");
    errors = email.errors || {};
    expect(errors['email']).toBeFalsy();

  });

  it("Form submitted on click submit",()=>{
    component.handleLogin();
    expect(component.loginSuccess).toBe(false);
  })
});