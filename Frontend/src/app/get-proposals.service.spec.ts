import { TestBed } from '@angular/core/testing';
import { Feed } from './feed';

import { GetProposalsService } from './get-proposals.service';

describe('GetProposalsService', () => {
  let service: GetProposalsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetProposalsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  it('should send an array of posts', () => {
    expect(service.getPosts).toBe([Feed]);
  });
  it('On scroll should send an array of next posts', () => {
    expect(service.getNextPost).toBe([Feed]);
  });
});
