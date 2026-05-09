import { Component, HostBinding, OnInit } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuthService } from './services/auth.service';
import { AppState } from './store/app.state';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  @HostBinding('class') className = '';
  @Select(AppState.isDark)
  isDark$!: Observable<boolean>;
  title: any;
  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.autoAuthUser();
    this.isDark$.subscribe((status) =>
      status ? (this.className = 'darkMode') : (this.className = '')
    );
  }
}
