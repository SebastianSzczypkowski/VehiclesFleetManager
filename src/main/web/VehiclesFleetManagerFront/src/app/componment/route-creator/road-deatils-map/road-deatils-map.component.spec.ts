import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoadDeatilsMapComponent } from './road-deatils-map.component';

describe('RoadDeatilsMapComponent', () => {
  let component: RoadDeatilsMapComponent;
  let fixture: ComponentFixture<RoadDeatilsMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoadDeatilsMapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoadDeatilsMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
