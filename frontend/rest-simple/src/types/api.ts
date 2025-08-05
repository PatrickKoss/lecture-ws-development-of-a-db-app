export interface StudentResponse {
  id: string;
  mnr: string;
  name: string;
  lastName: string;
  createdOn: string;
}

export interface ErrorResponse {
  message: string;
  details?: string;
}

export interface UpdateStudentRequest {
  name: string;
  lastName: string;
}

export interface CreateStudentRequest {
  name: string;
  lastName: string;
}

export interface RegisterRequest {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface RefreshTokenRequest {
  refresh_token: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface StudentsListResponse {
  students: StudentResponse[];
}

export interface AuthResponse {
  access_token: string;
  refresh_token: string;
  token_type: string;
  expires_in: number;
}

export interface ApiError extends Error {
  status?: number;
  data?: ErrorResponse;
}