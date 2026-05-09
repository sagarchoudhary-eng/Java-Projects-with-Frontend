import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { State, Action, Selector, StateContext, Store } from '@ngxs/store';
import {
  StartLoading,
  StopLoading,
  SetAuthenticated,
  SetUnauthenticated,
  SetThemeStatus,
  OpenSnackbar,
  SetRole,
  SetEmail,
  SetFiles,
  DeleteFile,
} from './app.actions';

export interface AppStateModel {
  isLoading: boolean;
  isAuthenticated: boolean;
  darkModeSwitch: boolean;
  role: string;
  email: string;
  fileList: string[];
}

@State<AppStateModel>({
  name: 'app',
  defaults: {
    isLoading: false,
    isAuthenticated: false,
    darkModeSwitch: false,
    role: '',
    email: '',
    fileList: [],
  },
})
@Injectable()
export class AppState {
  constructor(private _snackBar: MatSnackBar, private store: Store) {}

  @Selector()
  public static getState(state: AppStateModel) {
    return state;
  }

  @Selector()
  public static isLoading(state: AppStateModel) {
    return state.isLoading;
  }

  @Selector()
  public static isAuthenticated(state: AppStateModel) {
    return state.isAuthenticated;
  }

  @Selector()
  public static isDark(state: AppStateModel) {
    return state.darkModeSwitch;
  }

  @Selector()
  public static getRole(state: AppStateModel) {
    return state.role;
  }

  @Selector()
  public static getEmail(state: AppStateModel) {
    return state.email;
  }

  @Selector()
  public static getFiles(state: AppStateModel) {
    return state.fileList;
  }

  @Action(StartLoading)
  public startLoading({ patchState }: StateContext<AppStateModel>) {
    patchState({ isLoading: true });
  }

  @Action(StopLoading)
  public stopLoading({ patchState }: StateContext<AppStateModel>) {
    patchState({ isLoading: false });
  }

  @Action(SetAuthenticated)
  public setAuthenticated(
    { patchState }: StateContext<AppStateModel>,
    action: SetAuthenticated
  ) {
    this.store.dispatch(new SetRole(action.payload));
    patchState({ isAuthenticated: true });
  }

  @Action(SetUnauthenticated)
  public setUnauthenticated({ patchState }: StateContext<AppStateModel>) {
    patchState({ isAuthenticated: false, role: '' });
  }

  @Action(SetThemeStatus)
  public setThemeStatus(
    { patchState }: StateContext<AppStateModel>,
    action: SetThemeStatus
  ) {
    patchState({ darkModeSwitch: action.payload });
  }

  @Action(OpenSnackbar)
  public openSnackbar(
    { patchState }: StateContext<AppStateModel>,
    action: OpenSnackbar
  ) {
    this._snackBar.open(action.payload, 'OK', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
    });
  }

  @Action(SetRole)
  public setRole({ patchState }: StateContext<AppStateModel>, action: SetRole) {
    patchState({ role: action.payload });
  }

  @Action(SetEmail)
  public setEmail(
    { patchState }: StateContext<AppStateModel>,
    action: SetEmail
  ) {
    patchState({ email: action.payload });
  }

  @Action(SetFiles)
  public setFiles(
    { patchState }: StateContext<AppStateModel>,
    action: SetFiles
  ) {
    patchState({ fileList: action.payload });
  }

  @Action(DeleteFile)
  public deleteFile(
    { setState, getState }: StateContext<AppStateModel>,
    action: DeleteFile
  ) {
    const state = getState();
    const current = {
      fileList: [...state.fileList.filter((f) => f != action.payload)],
    };
    setState({
      ...state,
      ...current,
    });
  }
}
