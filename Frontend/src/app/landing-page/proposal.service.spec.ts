import { TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs';

import { ProposalService } from './proposal.service';

describe('ProposalService', () => {
  let service: ProposalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProposalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should send a comment to db', () => {
    expect(service.postComment).toBe(Observable);
  });
  it('should send a Like to db', () => {
    expect(service.postLike).toBe(Observable);
  });
});
