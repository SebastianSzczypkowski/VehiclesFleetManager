import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleInspectionInfoComponent } from './vehicle-inspection-info.component';

describe('VehicleInspectionInfoComponent', () => {
  let component: VehicleInspectionInfoComponent;
  let fixture: ComponentFixture<VehicleInspectionInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VehicleInspectionInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleInspectionInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
