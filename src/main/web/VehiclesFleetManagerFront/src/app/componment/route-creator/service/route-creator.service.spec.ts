import { TestBed } from '@angular/core/testing';

import { RouteCreatorService } from './route-creator.service';

describe('RouteCreatorService', () => {
  let service: RouteCreatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouteCreatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
