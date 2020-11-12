import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA, MatDialogRef,MatDialogModule,MatDialog } from '@angular/material/dialog';
import { CreateProposalComponent } from './create-proposal.component';
import {Overlay} from '@angular/cdk/overlay'
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatMenuModule} from '@angular/material/menu'
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import {TestServiceService} from '../test-service.service';
import {MatSelectModule} from '@angular/material/select'
import {MatInputModule} from '@angular/material/input'
describe('CreateProposalComponent', () => {
  let component: CreateProposalComponent;
  let fixture: ComponentFixture<CreateProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[MatDialogModule,MatMenuModule,NoopAnimationsModule,ReactiveFormsModule,
        FormsModule,MatSelectModule,MatInputModule],
      providers:[
        {provide:Overlay},
        {provide:MatDialog},
        TestServiceService,
        { provide: MAT_DIALOG_DATA, useValue: {
          post:{
                id:2,
                title:"This is title",
                description:"This is desc"
              },
              userId:2
        } },
        { provide: MatDialogRef, useValue: {} },],
      declarations: [ CreateProposalComponent ]
    })
    .compileComponents();
    let service:TestServiceService
    service=TestBed.inject(TestServiceService)
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateProposalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should create CreateProposal', () => {
    expect(component).toBeTruthy();
  });

  
  it('should select message', () => {
    component.ngOnInit()
    expect(component.prop).toEqual("Update Proposal")
  });

  it("should return error",()=>{
    // let service=TestBed.inject(TestServiceService)
    let errors={}
    let title = component.service.form.controls.title;
    let desc = component.service.form.controls.description;
    let teams = component.service.form.controls.teams;
    teams.setValue('') 
    title.setValue('')
    desc.setValue('')
    errors=title.errors
    expect(title.hasError("required")).toBeTruthy()
    expect(desc.errors['required']).toBeTruthy()

    title.setValue('hell')
    desc.setValue('hll')
    expect(title.errors['required']).toBeFalsy()
    expect(desc.errors['required']).toBeFalsy()
    expect(title.errors['minlength']).toBeTruthy()
    expect(desc.errors['minlength']).toBeTruthy()

    title.setValue('This is title')
    desc.setValue('this is description')
    expect(title.hasError("required")).toBeFalsy()
    expect(desc.hasError("required")).toBeFalsy()
  })
});
