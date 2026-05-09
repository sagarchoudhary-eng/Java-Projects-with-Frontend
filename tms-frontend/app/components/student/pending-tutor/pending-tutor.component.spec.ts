import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingTutorComponent } from './pending-tutor.component';

describe('PendingTutorComponent', () => {
  let component: PendingTutorComponent;
  let fixture: ComponentFixture<PendingTutorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingTutorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingTutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
