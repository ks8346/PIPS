import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TeamComponent } from './team.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GetTeamService } from '../service/get-team.service';
import { AppRoutingModule } from '../app-routing.module';

describe('TeamComponent', () => {
  let component: TeamComponent;
  let fixture: ComponentFixture<TeamComponent>;

  beforeEach(async () => {
    let httpClient: HttpClient;
    let httpTestingController: HttpTestingController;
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        FormsModule,
        AppRoutingModule
      ],
      declarations: [ TeamComponent ],
      providers: [ GetTeamService]
    })
    .compileComponents();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
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
});
