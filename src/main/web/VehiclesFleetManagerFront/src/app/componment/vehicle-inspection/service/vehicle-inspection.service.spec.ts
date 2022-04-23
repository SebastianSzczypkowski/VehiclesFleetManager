import { TestBed } from '@angular/core/testing';

import { VehicleInspectionService } from './vehicle-inspection.service';

describe('VehicleInspectionService', () => {
  let service: VehicleInspectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehicleInspectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
