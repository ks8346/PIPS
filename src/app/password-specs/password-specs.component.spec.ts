import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordSpecsComponent } from './password-specs.component';

describe('PasswordSpecsComponent', () => {
  let component: PasswordSpecsComponent;
  let fixture: ComponentFixture<PasswordSpecsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PasswordSpecsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordSpecsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
