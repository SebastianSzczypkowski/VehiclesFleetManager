import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoadDeatilsComponent } from './road-deatils.component';

describe('RoadDeatilsComponent', () => {
  let component: RoadDeatilsComponent;
  let fixture: ComponentFixture<RoadDeatilsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoadDeatilsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoadDeatilsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
