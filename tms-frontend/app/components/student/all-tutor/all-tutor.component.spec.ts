import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllTutorComponent } from './all-tutor.component';

describe('AllTutorComponent', () => {
  let component: AllTutorComponent;
  let fixture: ComponentFixture<AllTutorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllTutorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllTutorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
