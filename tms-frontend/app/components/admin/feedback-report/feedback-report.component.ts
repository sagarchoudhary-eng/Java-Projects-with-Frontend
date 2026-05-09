import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Color, Label } from 'ng2-charts';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-feedback-report',
  templateUrl: './feedback-report.component.html',
  styleUrls: ['./feedback-report.component.scss'],
})
export class FeedbackReportComponent implements OnInit {
  dataset: number[] = [];
  public lineChartData: ChartDataSets[] = [
    { data: this.dataset, label: 'Number Of Feedback On Particular Date' },
  ];
  public lineChartLabels: Label[] = [];
  public lineChartOptions: ChartOptions = {
    responsive: true,
  };
  public lineChartColors: Color[] = [
    {
      borderColor: 'black',
      backgroundColor: 'lightblue',
    },
  ];

  public lineChartLegend = true;
  public lineChartType: ChartType = 'line';
  public lineChartPlugins = [];

  constructor(private _adminService: AdminService) {
    this._adminService.feedbackReport().subscribe((data) => {
      for (let i of data) {
        this.dataset.push(i.size);
        this.lineChartLabels.push(i.date);
      }
    });
  }
  ngOnInit(): void {}
}
