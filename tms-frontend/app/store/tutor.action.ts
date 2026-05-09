import { Feedback } from '../interfaces/feedback.model';
import { StudentList } from '../interfaces/tables.model';

export class LoadPendingStudents {
  public static readonly type = '[TUTOR] Load Pending Students';
  constructor(public payload: StudentList[]) {}
}

export class LoadSubscribedStudents {
  public static readonly type = '[TUTOR] Load Subscribed Students';
  constructor(public payload: StudentList[]) {}
}

export class AcceptStudent {
  public static readonly type = '[TUTOR] Accept Student';
  constructor(public payload: StudentList) {}
}

export class RejectStudent {
  public static readonly type = '[TUTOR] Reject Student';
  constructor(public payload: StudentList) {}
}

export class LoadFeedback {
  public static readonly type = '[TUTOR] Load Feedback';
  constructor(public payload: Feedback[]) {}
}

export class AddFeedback {
  public static readonly type = '[TUTOR] Add Feedback';
  constructor(public payload: Feedback) {}
}

export class ClearTutorData {
  public static readonly type = '[TUTOR] Clear Data';
  constructor() {}
}

export class SetTutorId {
  public static readonly type = '[TUTOR] Set Tutor ID';
  constructor(public payload: number | string) {}
}
