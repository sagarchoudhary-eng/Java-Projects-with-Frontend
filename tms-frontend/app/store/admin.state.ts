import { Injectable } from '@angular/core';
import { StateContext } from '@ngxs/store';
import { Action, Selector, State } from '@ngxs/store';
import { MiniCardData } from '../interfaces/minicard-data.model';
import { UserList } from '../interfaces/tables.model';
import {
  ChangeUserStatus,
  ClearAdminData,
  DeleteUser,
  LoadUsers,
} from './admin.action';

export interface AdminStateModel {
  usersList: UserList[];
  miniCardData: MiniCardData[];
}

export const getAdminInitialState = (): AdminStateModel => ({
  usersList: [],
  miniCardData: [],
});

@State<AdminStateModel>({
  name: 'admin',
  defaults: getAdminInitialState(),
})
@Injectable()
export class AdminState {
  @Selector()
  public static getState(state: AdminStateModel) {
    return state;
  }

  @Selector()
  public static getUsers(state: AdminStateModel) {
    return state.usersList;
  }

  @Selector()
  public static getMiniCardData(state: AdminStateModel) {
    return state.miniCardData;
  }

  @Action(LoadUsers)
  public userListLoaded(
    { patchState }: StateContext<AdminStateModel>,
    action: LoadUsers
  ) {
    patchState({ usersList: [...action.payload] });
  }

  @Action(ChangeUserStatus)
  public changeUserStatus(
    { getState, patchState }: StateContext<AdminStateModel>,
    action: ChangeUserStatus
  ) {
    const current = getState();
    let toUpdateUserList = [
      ...current.usersList.filter((x) => x.id != action.payload.id),
      action.payload,
    ];
    patchState({ usersList: toUpdateUserList });
  }

  @Action(DeleteUser)
  public deleteUser(
    { setState, getState }: StateContext<AdminStateModel>,
    action: DeleteUser
  ) {
    const state = getState();

    const current = {
      usersList: [...state.usersList.filter((x) => x.id !== +action.payload)],
    };

    setState({
      ...state,
      ...current,
    });
  }

  @Action(ClearAdminData)
  public clearData({ setState }: StateContext<AdminStateModel>) {
    setState(getAdminInitialState());
  }
}
