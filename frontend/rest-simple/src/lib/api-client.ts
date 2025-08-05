import {
  ApiError,
  AuthResponse,
  CreateStudentRequest,
  LoginRequest,
  RefreshTokenRequest,
  RegisterRequest,
  StudentResponse,
  StudentsListResponse,
  UpdateStudentRequest,
} from '@/types/api';
import { appConfig } from './config';

class ApiClient {
  private baseUrl: string;
  private timeout: number;

  constructor() {
    this.baseUrl = appConfig.api.baseUrl;
    this.timeout = appConfig.api.timeout;
  }

  private getAuthToken(): string | null {
    if (typeof window === 'undefined') return null;
    return localStorage.getItem(appConfig.auth.tokenStorageKey);
  }

  private getRefreshToken(): string | null {
    if (typeof window === 'undefined') return null;
    return localStorage.getItem(appConfig.auth.refreshTokenStorageKey);
  }

  private setTokens(accessToken: string, refreshToken: string): void {
    if (typeof window === 'undefined') return;
    localStorage.setItem(appConfig.auth.tokenStorageKey, accessToken);
    localStorage.setItem(appConfig.auth.refreshTokenStorageKey, refreshToken);
  }

  private clearTokens(): void {
    if (typeof window === 'undefined') return;
    localStorage.removeItem(appConfig.auth.tokenStorageKey);
    localStorage.removeItem(appConfig.auth.refreshTokenStorageKey);
  }

  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<T> {
    const url = `${this.baseUrl}${endpoint}`;
    const token = this.getAuthToken();

    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
      ...(options.headers as Record<string, string>),
    };

    if (token && appConfig.auth.enabled) {
      headers.Authorization = `Bearer ${token}`;
    }

    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), this.timeout);

    try {
      const response = await fetch(url, {
        ...options,
        headers,
        signal: controller.signal,
      });

      clearTimeout(timeoutId);

      if (!response.ok) {
        let errorData;
        try {
          errorData = await response.json();
        } catch {
          errorData = { message: 'An error occurred' };
        }

        const error: ApiError = new Error(errorData.message || 'API Error');
        error.status = response.status;
        error.data = errorData;
        throw error;
      }

      if (response.status === 204) {
        return {} as T;
      }

      return await response.json();
    } catch (error) {
      clearTimeout(timeoutId);
      
      if (error instanceof Error && error.name === 'AbortError') {
        const timeoutError: ApiError = new Error('Request timeout');
        timeoutError.status = 408;
        throw timeoutError;
      }

      throw error;
    }
  }

  private async refreshAccessToken(): Promise<void> {
    const refreshToken = this.getRefreshToken();
    if (!refreshToken) {
      throw new Error('No refresh token available');
    }

    const response = await this.request<AuthResponse>('/auth/refresh', {
      method: 'POST',
      body: JSON.stringify({ refresh_token: refreshToken }),
    });

    this.setTokens(response.access_token, response.refresh_token);
  }

  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await this.request<AuthResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(credentials),
    });

    if (appConfig.auth.enabled) {
      this.setTokens(response.access_token, response.refresh_token);
    }

    return response;
  }

  async register(userData: RegisterRequest): Promise<void> {
    await this.request('/auth/register', {
      method: 'POST',
      body: JSON.stringify(userData),
    });
  }

  async logout(): Promise<void> {
    this.clearTokens();
  }

  async getAllStudents(): Promise<StudentsListResponse> {
    return this.request<StudentsListResponse>('/students');
  }

  async createStudent(student: CreateStudentRequest): Promise<StudentResponse> {
    return this.request<StudentResponse>('/students', {
      method: 'POST',
      body: JSON.stringify(student),
    });
  }

  async updateStudent(
    id: string,
    student: UpdateStudentRequest
  ): Promise<StudentResponse> {
    return this.request<StudentResponse>(`/students/${id}`, {
      method: 'PUT',
      body: JSON.stringify(student),
    });
  }

  async deleteStudent(id: string): Promise<void> {
    await this.request(`/students/${id}`, {
      method: 'DELETE',
    });
  }

  async healthCheck(): Promise<any> {
    return this.request('/healthz');
  }
}

export const apiClient = new ApiClient();