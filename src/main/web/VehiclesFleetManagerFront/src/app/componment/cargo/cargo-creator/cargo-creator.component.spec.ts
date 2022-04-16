import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoCreatorComponent } from './cargo-creator.component';

describe('CargoCreatorComponent', () => {
  let component: CargoCreatorComponent;
  let fixture: ComponentFixture<CargoCreatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CargoCreatorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CargoCreatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
