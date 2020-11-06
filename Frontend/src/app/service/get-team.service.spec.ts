import { TestBed } from '@angular/core/testing';

import { GetTeamService } from './get-team.service';

describe('GetTeamService', () => {
  let service: GetTeamService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetTeamService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
