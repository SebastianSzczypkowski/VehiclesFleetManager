import { TestBed } from '@angular/core/testing';

import { VehilceInspectionEmmiterService } from './vehilce-inspection-emmiter.service';

describe('VehilceInspectionEmmiterService', () => {
  let service: VehilceInspectionEmmiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VehilceInspectionEmmiterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
