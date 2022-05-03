import { TestBed } from '@angular/core/testing';

import { VehicleEmmiterService } from './vehicle-emmiter.service';

describe('VehicleEmmiterService', () => {
  let service: VehicleEmmiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehicleEmmiterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
