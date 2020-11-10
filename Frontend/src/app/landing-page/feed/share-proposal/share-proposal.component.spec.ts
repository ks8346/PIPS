import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShareProposalComponent } from './share-proposal.component';

describe('ShareProposalComponent', () => {
  let component: ShareProposalComponent;
  let fixture: ComponentFixture<ShareProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
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
});
