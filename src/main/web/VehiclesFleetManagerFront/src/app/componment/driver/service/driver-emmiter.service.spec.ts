import { TestBed } from '@angular/core/testing';

import { DriverEmmiterService } from './driver-emmiter.service';

describe('DriverEmmiterService', () => {
  let service: DriverEmmiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DriverEmmiterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
