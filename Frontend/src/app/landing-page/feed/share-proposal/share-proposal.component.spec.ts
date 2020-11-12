import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareProposalComponent } from './share-proposal.component';
import { MAT_DIALOG_DATA, MatDialogRef,MatDialogModule,MatDialog } from '@angular/material/dialog'; 
import { SelectionModel } from '@angular/cdk/collections';
describe('ShareProposalComponent', () => {
  let component: ShareProposalComponent;
  let fixture: ComponentFixture<ShareProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[MatDialogModule],
      providers:[
        { provide: MAT_DIALOG_DATA, useValue: {teams:[{
              id:1,
              name:"Devs"
            },
            {
              id:2,
              name:"DevsB"
            }
          ],
          prop:[{
            id:1,
            name:"Devs"
          }]
        } },
        { provide: MatDialogRef, useValue: {} },
      ],
      declarations: [ ShareProposalComponent ]
    })
    .compileComponents();
    
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShareProposalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should return true",()=>{
    let flag=component.selected({id:1,name:"Devs"})
    expect(flag).toEqual(true)
  })

});
