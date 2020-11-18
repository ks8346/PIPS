import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule,MatDialog} from '@angular/material/dialog';
import { UserLoginService } from '../service/user-login.service';
import { ForgetPasswordComponent } from './forget-password.component';
import { ResetPasswordService } from './../service/reset-password.service';
import { throwError,of} from 'rxjs';

describe('LoginComponent', () => {
  let component: ForgetPasswordComponent;
  let fixture: ComponentFixture<ForgetPasswordComponent>;
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
      ],
      declarations: [ ForgetPasswordComponent ],
      providers:[ 
        ResetPasswordService,
        ResetPasswordService
      ]
    });
    
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(ForgetPasswordComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('initial Submit Status to be true',()=>{
    expect(component.isSubmit).toBe(true);
  })

  it('submit button value changed ',()=>{
    let resetPassword = TestBed.inject(ResetPasswordService)
    let response = { email : 'ankitrustagi12@gmail.com'}

    spyOn(resetPassword,'resetLink').and.returnValue(of(response));
    component.onSubmit();

    expect(component.error).toBe('Reset link has been send to your email.')
    expect(component.isSubmit).toBe(false);
  })

  it('reset link to be successfully send',()=>{
    let resetPassword = TestBed.inject(ResetPasswordService)
    let error = { status : 200}

    spyOn(resetPassword,'resetLink').and.returnValue(throwError(error));
    component.onSubmit();

    expect(component.error).toBe('Reset link has been send to your email.')
  })

  it('reset link not send due to some error',()=>{
    let resetPassword = TestBed.inject(ResetPasswordService)
    let error = {status: 406}

    spyOn(resetPassword,'resetLink').and.returnValue(throwError(error));
    component.onSubmit();

    expect(component.error).toBe('Email id doesnot exists. Please try again!')
  })

  
});