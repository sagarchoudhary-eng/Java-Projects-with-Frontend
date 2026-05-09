export interface AuthLoginData {
  email: string;
  password: string;
}

export interface AuthLoginResponseData {
  token: string;
  expiresIn: number;
  role: string;
  userId: number;
}
