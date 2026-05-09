export class StartLoading {
  public static readonly type = '[UI] Start Loading';
  constructor() {}
}

export class StopLoading {
  public static readonly type = '[UI] Stop Loading';
  constructor() {}
}

export class SetAuthenticated {
  public static readonly type = '[Auth] Set Authenticated';
  constructor(public payload: string) {}
}

export class SetUnauthenticated {
  public static readonly type = '[Auth] Set Unauthenticated';
  constructor() {}
}

export class SetAdminTrue {
  public static readonly type = '[Auth] Set Admin True';
  constructor() {}
}

export class SetAdminFalse {
  public static readonly type = '[Auth] Set Admin False';
  constructor() {}
}

export class SetThemeStatus {
  public static readonly type = '[Theme] Update Theme';
  constructor(public payload: boolean) {}
}

export class OpenSnackbar {
  public static readonly type = '[UI] Open Snackbar';
  constructor(public payload: string) {}
}

export class SetRole {
  public static readonly type = '[Auth] Set Role';
  constructor(public payload: string) {}
}

export class SetEmail {
  public static readonly type = '[Auth] Set Email';
  constructor(public payload: string) {}
}

export class SetFiles {
  public static readonly type = '[Auth] Set Files';
  constructor(public payload: string[]) {}
}

export class DeleteFile {
  public static readonly type = '[Auth] Delete File';
  constructor(public payload: string) {}
}
