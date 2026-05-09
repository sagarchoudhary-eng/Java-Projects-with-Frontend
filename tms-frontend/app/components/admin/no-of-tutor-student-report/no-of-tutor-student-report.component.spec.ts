import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoOfTutorStudentReportComponent } from './no-of-tutor-student-report.component';

describe('NoOfTutorStudentReportComponent', () => {
  let component: NoOfTutorStudentReportComponent;
  let fixture: ComponentFixture<NoOfTutorStudentReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NoOfTutorStudentReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoOfTutorStudentReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
