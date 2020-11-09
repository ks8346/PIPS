import { TestBed } from '@angular/core/testing';

import { SendResetLinkService } from './send-reset-link.service';

describe('SendResetLinkService', () => {
  let service: SendResetLinkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SendResetLinkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
