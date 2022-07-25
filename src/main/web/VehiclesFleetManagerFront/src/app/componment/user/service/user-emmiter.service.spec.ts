import { TestBed } from '@angular/core/testing';

import { UserEmmiterService } from './user-emmiter.service';

describe('UserEmmiterService', () => {
  let service: UserEmmiterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserEmmiterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
