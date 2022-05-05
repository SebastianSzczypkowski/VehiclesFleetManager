import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoadTabelComponent } from './road-tabel.component';

describe('RoadTabelComponent', () => {
  let component: RoadTabelComponent;
  let fixture: ComponentFixture<RoadTabelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoadTabelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RoadTabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
