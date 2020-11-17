import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ResetLinkComponent } from './reset-link.component';
import { MatDialogModule} from '@angular/material/dialog';
import { AppRoutingModule } from '../app-routing.module';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";

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
        FormsModule
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
});
