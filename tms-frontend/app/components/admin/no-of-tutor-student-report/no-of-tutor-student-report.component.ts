import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-no-of-tutor-student-report',
  templateUrl: './no-of-tutor-student-report.component.html',
  styleUrls: ['./no-of-tutor-student-report.component.scss'],
})
export class NoOfTutorStudentReportComponent implements OnInit {
  tutor: any[] = [];
  student: any[] = [];

  public barChartOptions: ChartOptions = {
    responsive: true,
  };
  public barChartLabels: Label[] = ['No Of Tutor', 'No of Student'];
  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  public barChartPlugins = [];

  public barChartColors: Color[] = [
    {
      backgroundColor: ['lightpink', 'lightblue'],
    },
  ];

  public barChartData: ChartDataSets[] = [
    { data: this.tutor, label: 'No Of Tutor' },
    { data: this.student, label: 'No Of Student' },
  ];

  constructor(private _adminService: AdminService) {
    this._adminService.totalTutor().subscribe((data) => {
      // this.tutor.push(0);
      this.tutor.push(data);
    });
    this._adminService.totalStudent().subscribe((data) => {
      this.student.push(0);
      this.student.push(data);
    });
  }

  ngOnInit(): void {}
}
