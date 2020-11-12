import { TestBed } from '@angular/core/testing';

import { ShareProposalService } from './share-proposal.service';

describe('ShareProposalService', () => {
  let service: ShareProposalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShareProposalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
