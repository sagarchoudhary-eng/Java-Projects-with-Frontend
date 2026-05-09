import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvgRatingReportComponent } from './avg-rating-report.component';

describe('AvgRatingReportComponent', () => {
  let component: AvgRatingReportComponent;
  let fixture: ComponentFixture<AvgRatingReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvgRatingReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AvgRatingReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
