import { UserList } from '../interfaces/tables.model';

export class LoadUsers {
  public static readonly type = '[ADMIN] Load Users';
  constructor(public payload: UserList[]) {}
}

export class ChangeUserStatus {
  public static readonly type = '[ADMIN] Change User Status';
  constructor(public payload: UserList) {}
}

export class DeleteUser {
  public static readonly type = '[ADMIN] Delete User';
  constructor(public payload: number) {}
}

export class ClearAdminData {
  public static readonly type = '[ADMIN] Clear Data';
  constructor() {}
}
