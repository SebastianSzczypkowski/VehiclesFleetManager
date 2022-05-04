import { TestBed } from '@angular/core/testing';

import { TypofpermissionServiceService } from './typofpermission-service.service';

describe('TypofpermissionServiceService', () => {
  let service: TypofpermissionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TypofpermissionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
