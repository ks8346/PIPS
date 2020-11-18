import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { UserRegisterService } from '../service/user-register.service';
import { RegisterComponent } from './register.component';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatDialogModule} from '@angular/material/dialog';
import { teamList } from '../teamList';
import {of, throwError} from 'rxjs';
import { Teams } from '../teams';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
 
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
        MatDialogModule,
        NoopAnimationsModule
      ],
      declarations: [ RegisterComponent ],
      providers:[ 
        UserRegisterService
      ]
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
    let response = component.getEmailError();
    expect(response).toEqual('Please enter a value')

    email.setValue("test");
    errors = email.errors||{};
    response = component.getEmailError();
    expect(response).toEqual('Please enter a valid email')
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

  it("submit loading status changed",()=>{
    component.onSubmit();
    expect(component.loading).toBe(false);
  })

  it("should open Dialog",()=>{
    spyOn(component.dialog,"open")
    component.openDialog();
    expect(component.dialog.open).toHaveBeenCalled()
  })

  it("should open Dialog",()=>{
    spyOn(component.dialog,"open")
    component.responseDialog();
    expect(component.dialog.open).toHaveBeenCalled()
  })

  it('team should be selected',()=>{
    let team = component.registerForm.controls.team;
    team.setValue("");
    expect(team.hasError('required', ['required'])).toBeTruthy;
    team.setValue("Sparks");
    expect(team.hasError('required', ['required'])).toBeFalsy;
  })

  it("should call getTeams and return list of teams",fakeAsync(() => {
    let team = TestBed.inject(UserRegisterService);
    let response: teamList[] = [{teamName:"Devs"},{teamName:"Sparks"}];

    spyOn(team, 'getTeam').and.returnValue(of(response))
  
    component.getTeams();
    expect(component.teams).toEqual(response);
  }));

  it("should check data with existing data",() => {
    let userservice = TestBed.inject(UserRegisterService);
    let response = {team:{ name : 'Sparks'},user: {name: 'Ankit',email:'ankit',password:'Qwerty@123'}}

    let email=component.registerForm.controls.email;
    let password = component.registerForm.controls.password;
    let confirmpassword = component.registerForm.controls.confirmPass;
    let team = component.registerForm.controls.team;
    let name = component.registerForm.controls.userName;
    let terms = component.registerForm.controls.acceptTerms;

    email.setValue('ankit@gmail.com')
    password.setValue('Qwerty@123')
    confirmpassword.setValue('Qwerty@123')
    team.setValue('Sparks')
    name.setValue('Ankit')
    terms.setValue(true);

    spyOn(userservice, 'doRegister').and.returnValue(of(response))

    component.onSubmit()
    expect(component.registerForm.valid).toBeTruthy();
  
    component.onSubmit();
    expect(component.loading).toBe(false);
    expect(component.message).toEqual('You have been signed up! Now please login again to continue.');
  });

  it('error validation',()=>{

    let userservice = TestBed.inject(UserRegisterService);
    //let response = {team:{ name : 'Sparks'},user: {name: 'Ankit',email:'ankit',password:'Qwerty@123'}}

    let email=component.registerForm.controls.email;
    let password = component.registerForm.controls.password;
    let confirmpassword = component.registerForm.controls.confirmPass;
    let team = component.registerForm.controls.team;
    let name = component.registerForm.controls.userName;
    let terms = component.registerForm.controls.acceptTerms;

    email.setValue('ankit@gmail.com')
    password.setValue('Qwerty@123')
    confirmpassword.setValue('Qwerty@123')
    team.setValue('Sparks')
    name.setValue('Ankit')
    terms.setValue(true);

    let error = { status: 200}
    spyOn(userservice, 'doRegister').and.returnValue(throwError(error))
  
    component.onSubmit();
    expect(component.loading).toBe(false);
    expect(component.message).toEqual('You have been signed up! Now please login again to continue.');
  });

  it('error validation',()=>{

    let userservice = TestBed.inject(UserRegisterService);
    //let response = {team:{ name : 'Sparks'},user: {name: 'Ankit',email:'ankit',password:'Qwerty@123'}}

    let email=component.registerForm.controls.email;
    let password = component.registerForm.controls.password;
    let confirmpassword = component.registerForm.controls.confirmPass;
    let team = component.registerForm.controls.team;
    let name = component.registerForm.controls.userName;
    let terms = component.registerForm.controls.acceptTerms;

    email.setValue('ankit@gmail.com')
    password.setValue('Qwerty@123')
    confirmpassword.setValue('Qwerty@123')
    team.setValue('Sparks')
    name.setValue('Ankit')
    terms.setValue(true);
    let error = { status: 302}
    spyOn(userservice, 'doRegister').and.returnValue(throwError(error))
  
    component.onSubmit();
    expect(component.loading).toBe(false);
    expect(component.message).toEqual('This email Id already exists, Please try again!');

  });
  
}); 