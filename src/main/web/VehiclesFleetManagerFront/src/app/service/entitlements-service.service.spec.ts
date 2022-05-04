import { TestBed } from '@angular/core/testing';

import { EntitlementsServiceService } from './entitlements-service.service';

describe('EntitlementsServiceService', () => {
  let service: EntitlementsServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EntitlementsServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
