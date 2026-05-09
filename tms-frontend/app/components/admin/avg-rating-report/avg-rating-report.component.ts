import { Component, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Color, Label, SingleDataSet } from 'ng2-charts';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-avg-rating-report',
  templateUrl: './avg-rating-report.component.html',
  styleUrls: ['./avg-rating-report.component.scss'],
})
export class AvgRatingReportComponent implements OnInit {
  public polarAreaChartLabels: Label[] = [];
  public polarAreaChartData: SingleDataSet = [];
  public polarAreaLegend = true;
  public polarChartColors: Color[] = [
    {
      backgroundColor: ['lightpink', 'lightblue', 'plum', 'purple', 'yellow'],
      borderColor: [
        'rgba(252, 235, 89, 0.2)',
        'rgba(77, 152, 202, 0.2)',
        'rgba(241, 107, 119, 0.2)',
      ],
    },
  ];

  public polarAreaChartType: ChartType = 'polarArea';
  constructor(private _adminService: AdminService) {
    this._adminService.getAllTutorAvgRating().subscribe((data) => {
      for (let i of data) {
        this.polarAreaChartLabels.push(i.tutorName);
        this.polarAreaChartData.push(i.rating);
      }
    });
  }

  ngOnInit(): void {}
  // events
  public chartClicked({
    event,
    active,
  }: {
    event: MouseEvent;
    active: {}[];
  }): void {
    console.log(event, active);
  }

  public chartHovered({
    event,
    active,
  }: {
    event: MouseEvent;
    active: {}[];
  }): void {
    console.log(event, active);
  }
}
