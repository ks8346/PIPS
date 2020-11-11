import { TestBed } from '@angular/core/testing';

import { SocialMediaAuthService } from './social-media-auth.service';

describe('SocialMediaAuthService', () => {
  let service: SocialMediaAuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocialMediaAuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
