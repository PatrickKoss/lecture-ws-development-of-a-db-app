export interface StudentResponse {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  studentNumber: string;
  enrollmentDate: string;
}

export interface CreateStudentRequest {
  firstName: string;
  lastName: string;
  email: string;
  studentNumber: string;
}

export interface ErrorResponse {
  code: string;
  message: string;
  correlationId?: string;
  fields?: Record<string, string>;
}

export interface ApiError extends Error {
  status?: number;
  data?: ErrorResponse;
}
