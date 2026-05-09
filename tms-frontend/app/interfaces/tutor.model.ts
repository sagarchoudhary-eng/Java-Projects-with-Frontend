export interface ExtraDetails {
  id: number;
  technology: string;
  about: string;
  downloads: number;
  uploads: number;
}

export interface Tutor {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  mobileNo: string;
  role: string;
  status: string;
  questionId: number;
  answer: number;
  extraDetails: ExtraDetails;
  enabled: boolean;
}
