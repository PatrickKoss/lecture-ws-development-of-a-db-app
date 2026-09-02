import { ApiError, CreateStudentRequest, StudentResponse } from '@/types/api';
import { appConfig } from './config';

class ApiClient {
  private async request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), appConfig.api.timeout);

    try {
      const response = await fetch(`${appConfig.api.baseUrl}${endpoint}`, {
        ...options,
        headers: {
          'Content-Type': 'application/json',
          ...(options.headers as Record<string, string>),
        },
        signal: controller.signal,
      });

      if (!response.ok) {
        const data = await response.json().catch(() => ({ message: 'API-Fehler' }));
        const error: ApiError = new Error(data.message || 'API-Fehler');
        error.status = response.status;
        error.data = data;
        throw error;
      }

      return response.json();
    } catch (caught) {
      if (caught instanceof Error && caught.name === 'AbortError') {
        throw new Error('Zeitüberschreitung beim API-Aufruf');
      }
      throw caught;
    } finally {
      clearTimeout(timeoutId);
    }
  }

  getAllStudents() {
    return this.request<StudentResponse[]>('/api/students');
  }

  createStudent(student: CreateStudentRequest) {
    return this.request<StudentResponse>('/api/students', {
      method: 'POST',
      body: JSON.stringify(student),
    });
  }

  healthCheck() {
    return this.request<{ status: string; service: string }>('/api/students/health');
  }
}

export const apiClient = new ApiClient();
