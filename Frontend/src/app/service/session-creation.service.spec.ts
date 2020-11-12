import { TestBed } from '@angular/core/testing';

import { SessionCreationService } from './session-creation.service';

describe('SessionCreationService', () => {
  let service: SessionCreationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionCreationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
