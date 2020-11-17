import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterComponent } from './filter.component';

describe('FilterComponent', () => {
  let component: FilterComponent;
  let fixture: ComponentFixture<FilterComponent>;

  

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilterComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilterComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit the data',()=>{
    component.sDate = new Date('09/11/2020')
    component.eDate = new Date('10/11/2020')
    component.processFilter()
    expect(component.message).toEqual("")
   });

   it('should emit the data', () => {
    let spy = spyOn(component.parentFunctionFilter,'emit').and.callThrough()
    component.sendData("teamPost");
    expect(spy).toHaveBeenCalled
  });
});
