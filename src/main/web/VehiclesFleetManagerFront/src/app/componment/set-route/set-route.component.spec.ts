import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SetRouteComponent } from './set-route.component';

describe('SetRouteComponent', () => {
  let component: SetRouteComponent;
  let fixture: ComponentFixture<SetRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SetRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SetRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
