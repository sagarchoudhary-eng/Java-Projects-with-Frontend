import { OverlayContainer } from '@angular/cdk/overlay';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { SetThemeStatus } from 'src/app/store/app.actions';
import { AppState } from 'src/app/store/app.state';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  @Output() sidenavToggle = new EventEmitter<void>();
  @Select(AppState.isAuthenticated)
  isAuthenticated$!: Observable<boolean>;

  toggleControl = new FormControl(false);

  constructor(
    private overlayContainer: OverlayContainer,
    private store: Store,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.toggleControl.valueChanges.subscribe((val) => {
      this.store.dispatch(new SetThemeStatus(val));
      const classes = this.overlayContainer.getContainerElement().classList;
      if (val) {
        classes.add('darkMode');
      } else {
        classes.remove('darkMode');
      }
    });
  }

  onToggleSidenav() {
    this.sidenavToggle.emit();
  }

  onLogout() {
    this.authService.logout();
  }
}
