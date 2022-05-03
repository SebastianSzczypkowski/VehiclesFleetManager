import { TestBed } from '@angular/core/testing';

import { CargoEmmiterService } from './cargo-emmiter.service';

describe('CargoEmmiterService', () => {
  let service: CargoEmmiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CargoEmmiterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
