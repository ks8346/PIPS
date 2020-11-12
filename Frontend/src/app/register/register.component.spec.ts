import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { UserRegisterService } from '../service/user-register.service';
import { RegisterComponent } from './register.component';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatDialogModule} from '@angular/material/dialog';
import {By} from '@angular/platform-browser';
import { compileComponentFromMetadata } from '@angular/compiler';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        RouterTestingModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientTestingModule,
        MatDialogModule
      ],
      declarations: [ RegisterComponent ],
      providers:[ UserRegisterService]
    })
    .compileComponents();
    
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    component.ngOnInit();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('invalid form when blank', () => {
    expect(component.registerForm.valid).toBeFalsy();
  });

  it('Every field Required',()=>{
    let userName = component.registerForm.controls.userName;
    let email = component.registerForm.controls.email;
    let password = component.registerForm.controls.password;
    let confirmpassword = component.registerForm.controls.confirmPass;

    userName.setValue('');
    email.setValue('');
    password.setValue('');
    confirmpassword.setValue('');

    expect(userName.hasError('required', ['required'])).toBeTruthy;
    expect(email.hasError('required', ['required'])).toBeTruthy;
    expect(password.hasError('required', ['required'])).toBeTruthy;
    expect(confirmpassword.hasError('required', ['required'])).toBeTruthy;

    userName.setValue('Ankit');
    email.setValue('ankit@gmail.com');
    password.setValue('Qwerty@123');
    confirmpassword.setValue('Qwerty@123');

    expect(userName.hasError('required', ['required'])).toBeFalsy;
    expect(email.hasError('required', ['required'])).toBeFalsy;
    expect(password.hasError('required', ['required'])).toBeFalsy;
    expect(confirmpassword.hasError('required', ['required'])).toBeFalsy;

  })

  it('length of Username should be >=2 && <=20',()=>{
    let userName = component.registerForm.controls.userName;

    userName.setValue("");
    expect(userName.hasError('minlength', ['minlength'])).toBeTruthy;
    expect(userName.hasError('maxlength', ['maxlength'])).toBeFalsy;
  
    userName.setValue("a");
    expect(userName.hasError('minlength', ['minlength'])).toBeTruthy;
    expect(userName.hasError('maxlength', ['maxlength'])).toBeFalsy;

    userName.setValue("abcdefghijklmnopqrstuvwxyz");
    expect(userName.hasError('minlength', ['minlength'])).toBeFalsy;
    expect(userName.hasError('maxlength', ['maxlength'])).toBeTruthy;

    userName.setValue("abcdefghijklmnopqrstuvwxyz");
    expect(userName.hasError('minlength', ['minlength'])).toBeFalsy;
    expect(userName.hasError('maxlength', ['maxlength'])).toBeFalsy;
  })

  it('email pattern validity', () => {
    let errors = {};
    let email = component.registerForm.controls.email;
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

  it('password specifications check', () => {
    let password = component.registerForm.controls.password;

    password.setValue("");
    expect(password.hasError('pattern', ['pattern'])).toBeTruthy;

    password.setValue("123456789");
    expect(password.hasError('pattern', ['pattern'])).toBeTruthy;

    password.setValue("Qwerty@123");
    expect(password.hasError('pattern', ['pattern'])).toBeFalsy;
  });

  it('password not equal to confirmpassword is invalid',()=>{
    let password = component.registerForm.controls['password'];
    let confirmpassword = component.registerForm.controls['confirmPass'];

    password.setValue("Qwerty@123");
    confirmpassword.setValue("Abcdef@123");

    expect(password.valid).not.toEqual(confirmpassword.valid);
  })

  it('password should be equal to confirmpassword',()=>{
    let password = component.registerForm.controls['password'];
    let confirmpassword = component.registerForm.controls['confirmPass'];

    password.setValue("Qwerty@123");
    confirmpassword.setValue("Qwerty@123");

    expect(password.valid).toEqual(confirmpassword.valid);
  })

  it("Form submitted on click submit",()=>{
    component.onSubmit();
    expect(component.submitted).toBe(true);
  })

  it("should open Dialog",()=>{
    //spyOn(component.dialog,"open")
    component.onSubmit();
    expect(component.dialog.open).toHaveBeenCalled()
  })

  it("submit loading status changed",()=>{
    component.onSubmit();
    expect(component.loading).toBe(true);
  })
  
});