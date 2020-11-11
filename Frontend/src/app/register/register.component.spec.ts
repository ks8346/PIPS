import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { UserRegisterService } from '../service/user-register.service';
import { RegisterComponent } from './register.component';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { MatDialogModule} from '@angular/material/dialog';

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
    //.compileComponents();
    
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

  it('email field validity', () => {
    let errors = {};
    let email = component.registerForm.controls.email;
    // expect(email.valid).toBeFalsy()

    // errors = email.errors || {};
    // expect(errors['required']).toBeTruthy();

    email.setValue("test");
    errors = email.errors;
    expect(errors['required']).toBeFalsy();
    expect(errors['email']).toBeTruthy();

    // email.setValue("test@example.com");
    // errors = email.errors || {};
    // expect(errors['required']).toBeFalsy();
    // expect(errors['pattern']).toBeFalsy();
    email.setValue('')
    expect(email.valid).toBeFalsy();

   

  });

  it('password field validity', () => {
    let errors = {};
    let password = component.registerForm.controls['password'];

    errors = password.errors || {};
    expect(errors['required']).toBeTruthy();

    password.setValue("123456");
    errors = password.errors || {};
    expect(errors['required']).toBeFalsy();
    expect(errors['pattern']).toBeTruthy();

    // password.setValue("123456789");
    // errors = password.errors || {};
    // expect(errors['required']).toBeFalsy();
    // expect(errors['minlength']).toBeFalsy();
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

});

