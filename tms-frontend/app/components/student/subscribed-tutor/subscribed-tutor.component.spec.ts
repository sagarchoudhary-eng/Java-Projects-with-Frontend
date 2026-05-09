import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscribedTutorComponent } from './subscribed-tutor.component';

describe('SubscribedTutorComponent', () => {
  let component: SubscribedTutorComponent;
  let fixture: ComponentFixture<SubscribedTutorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubscribedTutorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscribedTutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
