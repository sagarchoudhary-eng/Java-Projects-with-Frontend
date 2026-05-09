import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType } from 'chart.js';

import {
  Color,
  Label,
  monkeyPatchChartJsLegend,
  monkeyPatchChartJsTooltip,
  SingleDataSet,
} from 'ng2-charts';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss'],
})
export class ChartComponent implements OnInit {
  public pieChartOptions: ChartOptions = {
    responsive: true,
  };
  public pieChartLabels: Label[] = [];

  public pieChartData: SingleDataSet = [];
  public pieChartType: ChartType = 'pie';
  public pieChartLegend = true;
  public pieChartPlugins = [];

  public pieChartColors: Color[] = [
    {
      backgroundColor: [
        'lightpink',
        'lightblue',
        'plum',
        'purple',
        'yellow',
        'lightbrown',
        'lightorange',
        'pink',
        'red',
        'lightgrey',
      ],
      borderColor: [
        'rgba(252, 235, 89, 0.2)',
        'rgba(77, 152, 202, 0.2)',
        'rgba(241, 107, 119, 0.2)',
      ],
    },
  ];

  ngOnInit(): void {}

  constructor(private _service: AdminService) {
    monkeyPatchChartJsTooltip();
    monkeyPatchChartJsLegend();
    this._service.chartData().subscribe((data) => {
      for (let i of data) {
        this.pieChartLabels.push(i.name);
        this.pieChartData.push(i.size);
      }
    });
  }
}
