import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DriverCreatorComponent } from './driver-creator.component';

describe('DriverCreatorComponent', () => {
  let component: DriverCreatorComponent;
  let fixture: ComponentFixture<DriverCreatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DriverCreatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DriverCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
