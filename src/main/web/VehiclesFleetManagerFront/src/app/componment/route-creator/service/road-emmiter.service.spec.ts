import { TestBed } from '@angular/core/testing';

import { RoadEmmiterService } from './road-emmiter.service';

describe('RoadEmmiterService', () => {
  let service: RoadEmmiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoadEmmiterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
