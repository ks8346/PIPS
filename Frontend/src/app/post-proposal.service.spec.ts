import { TestBed } from '@angular/core/testing';
import {Proposal} from './proposal' ;
import { PostProposalService } from './post-proposal.service';

describe('PostProposalService', () => {
  let service: PostProposalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostProposalService);
  });
  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should be sent in proposal format', () => {
    expect(service.postProposal).toBe(Proposal);
  });
});
