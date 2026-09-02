'use client';

import { useEffect, useState } from 'react';
import { apiClient } from '@/lib/api-client';
import { ApiError, CreateStudentRequest, StudentResponse } from '@/types/api';

export function useStudents() {
  const [students, setStudents] = useState<StudentResponse[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchStudents = async () => {
    try {
      setError(null);
      setIsLoading(true);
      setStudents(await apiClient.getAllStudents());
    } catch (caught) {
      const apiError = caught as ApiError;
      setError(apiError.message || 'Studierende konnten nicht geladen werden');
    } finally {
      setIsLoading(false);
    }
  };

  const createStudent = async (request: CreateStudentRequest) => {
    try {
      setError(null);
      const created = await apiClient.createStudent(request);
      setStudents((current) => [...current, created]);
      return created;
    } catch (caught) {
      const apiError = caught as ApiError;
      setError(apiError.message || 'Student konnte nicht angelegt werden');
      throw caught;
    }
  };

  useEffect(() => {
    fetchStudents();
  }, []);

  return {
    students,
    isLoading,
    error,
    createStudent,
    refreshStudents: fetchStudents,
    clearError: () => setError(null),
  };
}
