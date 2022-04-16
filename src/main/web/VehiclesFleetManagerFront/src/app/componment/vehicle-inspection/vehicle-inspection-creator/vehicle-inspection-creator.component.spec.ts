import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehicleInspectionCreatorComponent } from './vehicle-inspection-creator.component';

describe('VehicleInspectionCreatorComponent', () => {
  let component: VehicleInspectionCreatorComponent;
  let fixture: ComponentFixture<VehicleInspectionCreatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VehicleInspectionCreatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VehicleInspectionCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
