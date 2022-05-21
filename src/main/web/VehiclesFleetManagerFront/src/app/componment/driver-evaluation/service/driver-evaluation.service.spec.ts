import { TestBed } from '@angular/core/testing';

import { DriverEvaluationService } from './driver-evaluation.service';

describe('DriverEvaluationService', () => {
  let service: DriverEvaluationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DriverEvaluationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
