import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ResetLinkComponent } from './reset-link.component';
import { MatDialogModule} from '@angular/material/dialog';
import { AppRoutingModule } from '../app-routing.module';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { ResetPasswordService } from '../service/reset-password.service';
import { throwError,of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('ResetLinkComponent', () => {
  let component: ResetLinkComponent;
  let fixture: ComponentFixture<ResetLinkComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        MatDialogModule,
        AppRoutingModule,
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        NoopAnimationsModule
      ],
      declarations: [ ResetLinkComponent ]
    })
    .compileComponents();

    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResetLinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('verify token - successful',()=>{
    let service = TestBed.inject(ResetPasswordService)
    let response = {id : 'itisantoken'}

    spyOn(service,'tokenVerification').and.returnValue(of(response))
    component.ngOnInit();
    expect(component.validToken).toBe(true);
  });

  it('verify token - successful',()=>{
    let service = TestBed.inject(ResetPasswordService)
    let error = { status : 202 };
    spyOn(service,'tokenVerification').and.returnValue(throwError(error))
    component.ngOnInit();

    expect(component.validToken).toBe(true);
  })

  it('verify token - unsuccessful',()=>{
    let service = TestBed.inject(ResetPasswordService)
    let error = { status : 406 };
    
    spyOn(service,'tokenVerification').and.returnValue(throwError(error))
    component.ngOnInit();

    expect(component.validToken).toBe(false);
  })

  it('update existing credentials',()=>{
    let service = TestBed.inject(ResetPasswordService)
    let response = { id: 'thisistoken', password: 'Qwerty@123'};
    
    spyOn(service,'resetPassword').and.returnValue(of(response))
    component.onSubmit();

    expect(component.msg).toBe('Your password has been reset sucessfully!');
  })
  
});
