import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatorMapComponent } from './creator-map.component';

describe('CreatorMapComponent', () => {
  let component: CreatorMapComponent;
  let fixture: ComponentFixture<CreatorMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreatorMapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatorMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
