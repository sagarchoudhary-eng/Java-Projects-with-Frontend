import { Injectable } from '@angular/core';
import { Action, Selector } from '@ngxs/store';
import { StateContext } from '@ngxs/store';
import { State } from '@ngxs/store';
import { Feedback } from '../interfaces/feedback.model';
import { StudentList } from '../interfaces/tables.model';
import {
  AcceptStudent,
  AddFeedback,
  ClearTutorData,
  LoadFeedback,
  LoadPendingStudents,
  LoadSubscribedStudents,
  RejectStudent,
  SetTutorId,
} from './tutor.action';

export interface TutorStateModel {
  pendingStudents: StudentList[];
  subscribedStudents: StudentList[];
  allFeedback: Feedback[];
  userId: number | string;
}

export const getTutorInitialState = (): TutorStateModel => ({
  pendingStudents: [],
  subscribedStudents: [],
  allFeedback: [],
  userId: -1,
});

@State<TutorStateModel>({
  name: 'tutor',
  defaults: getTutorInitialState(),
})
@Injectable()
export class TutorState {
  @Selector()
  public static getState(state: TutorStateModel) {
    return state;
  }

  @Selector()
  public static pendingStudents(state: TutorStateModel) {
    return state.pendingStudents;
  }

  @Selector()
  public static subscribedStudents(state: TutorStateModel) {
    return state.subscribedStudents;
  }

  @Selector()
  public static getAllFeedback(state: TutorStateModel) {
    return state.allFeedback;
  }

  @Selector()
  public static getTutorId(state: TutorStateModel) {
    return state.userId;
  }

  @Action(ClearTutorData)
  public clearData({ setState }: StateContext<TutorStateModel>) {
    setState(getTutorInitialState());
  }

  @Action(LoadPendingStudents)
  public pendingStudentsLoaded(
    { patchState }: StateContext<TutorStateModel>,
    action: LoadPendingStudents
  ) {
    patchState({ pendingStudents: [...action.payload] });
  }

  @Action(LoadSubscribedStudents)
  public userListLoaded(
    { patchState }: StateContext<TutorStateModel>,
    action: LoadSubscribedStudents
  ) {
    patchState({ subscribedStudents: [...action.payload] });
  }

  @Action(AcceptStudent)
  public acceptStudent(
    { patchState, getState }: StateContext<TutorStateModel>,
    action: AcceptStudent
  ) {
    const current = getState();
    let pendingStudents = current.pendingStudents;
    let toUpdateStudentList = [...current.subscribedStudents, action.payload];
    let pendingStudentsList = pendingStudents.filter(
      (student) => student.id !== action.payload.id
    );
    patchState({
      subscribedStudents: toUpdateStudentList,
      pendingStudents: pendingStudentsList,
    });
  }

  @Action(RejectStudent)
  public rejectStudent(
    { patchState, getState }: StateContext<TutorStateModel>,
    action: RejectStudent
  ) {
    const current = getState();
    let pendingStudents = current.pendingStudents;
    let pendingStudentsList = pendingStudents.filter(
      (student) => student.id !== action.payload.id
    );
    patchState({
      pendingStudents: pendingStudentsList,
    });
  }

  @Action(LoadFeedback)
  public loadFeedback(
    { patchState }: StateContext<TutorStateModel>,
    action: LoadFeedback
  ) {
    patchState({ allFeedback: [...action.payload] });
  }

  @Action(AddFeedback)
  public addFeedback(
    { patchState, getState }: StateContext<TutorStateModel>,
    action: AddFeedback
  ) {
    const current = getState();
    patchState({ allFeedback: [...current.allFeedback, action.payload] });
  }

  @Action(SetTutorId)
  public setTutorId(
    { patchState }: StateContext<TutorStateModel>,
    action: SetTutorId
  ) {
    patchState({ userId: action.payload });
  }
}
