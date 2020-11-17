import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ApiResponseComponent } from './api-response.component';

describe('ApiResponseComponent', () => {
  let component: ApiResponseComponent;
  let fixture: ComponentFixture<ApiResponseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers:[
        {provide:MatDialog},
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: {} },
      ],
      declarations: [ ApiResponseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ApiResponseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('dialog should close', ()=>{
    spyOn(component.dialogRef,"close")
    component.onNoClick();
    expect(component.dialogRef.close).toHaveBeenCalled() 
   })
});