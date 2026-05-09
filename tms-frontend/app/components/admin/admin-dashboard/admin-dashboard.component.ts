import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { MiniCardData } from 'src/app/interfaces/minicard-data.model';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss'],
})
export class AdminDashboardComponent implements OnInit {
  cardLayout = this.breakpointObserver
    .observe([Breakpoints.Handset, Breakpoints.Tablet])
    .pipe(
      map(({ matches }) => {
        if (matches) {
          return {
            columns: 1,
            miniCard: { cols: 1, rows: 1 },
            chart: { cols: 1, rows: 2 },
            table: { cols: 1, rows: 4 },
          };
        }

        return {
          columns: 4,
          miniCard: { cols: 1, rows: 1 },
          chart: { cols: 2, rows: 2 },
          table: { cols: 4, rows: 4 },
        };
      })
    );

  miniCardData: MiniCardData[] = [];

  constructor(
    private breakpointObserver: BreakpointObserver,
    private _adminServ: AdminService
  ) {
    this.miniCardData = this._adminServ.getAllMiniCardData();
  }

  ngOnInit(): void {}
}
