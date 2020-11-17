import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA, MatDialogRef,MatDialogModule,MatDialog } from '@angular/material/dialog';
import { CreateProposalComponent } from './create-proposal.component';
import {Overlay} from '@angular/cdk/overlay'
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatMenuModule} from '@angular/material/menu'
describe('CreateProposalComponent', () => {
  let component: CreateProposalComponent;
  let fixture: ComponentFixture<CreateProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[MatDialogModule,MatMenuModule,NoopAnimationsModule],
      providers:[
        {provide:Overlay},
        {provide:MatDialog},
        { provide: MAT_DIALOG_DATA, useValue: {
          post:{
                id:2,
                title:"blah",
                description:"blah blah"
              },
              userId:2
              

        } },
        { provide: MatDialogRef, useValue: {} },],
      declarations: [ CreateProposalComponent ]
    })
    .compileComponents();
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
    component.data={
      userId:1
    }
    component.ngOnInit()
    expect(component.prop).toEqual("Create Proposal")
  });
});
