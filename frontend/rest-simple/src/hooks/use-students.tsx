'use client';

import { useState, useEffect } from 'react';
import { apiClient } from '@/lib/api-client';
import { StudentResponse, CreateStudentRequest, UpdateStudentRequest, ApiError } from '@/types/api';

export function useStudents() {
  const [students, setStudents] = useState<StudentResponse[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchStudents = async () => {
    try {
      setError(null);
      setIsLoading(true);
      const response = await apiClient.getAllStudents();
      setStudents(response.students || []);
    } catch (error) {
      const apiError = error as ApiError;
      setError(apiError.message || 'Failed to fetch students');
    } finally {
      setIsLoading(false);
    }
  };

  const createStudent = async (student: CreateStudentRequest): Promise<StudentResponse> => {
    try {
      setError(null);
      const newStudent = await apiClient.createStudent(student);
      setStudents(prev => [...prev, newStudent]);
      return newStudent;
    } catch (error) {
      const apiError = error as ApiError;
      setError(apiError.message || 'Failed to create student');
      throw error;
    }
  };

  const updateStudent = async (id: string, student: UpdateStudentRequest): Promise<StudentResponse> => {
    try {
      setError(null);
      const updatedStudent = await apiClient.updateStudent(id, student);
      setStudents(prev => 
        prev.map(s => s.id === id ? updatedStudent : s)
      );
      return updatedStudent;
    } catch (error) {
      const apiError = error as ApiError;
      setError(apiError.message || 'Failed to update student');
      throw error;
    }
  };

  const deleteStudent = async (id: string): Promise<void> => {
    try {
      setError(null);
      await apiClient.deleteStudent(id);
      setStudents(prev => prev.filter(s => s.id !== id));
    } catch (error) {
      const apiError = error as ApiError;
      setError(apiError.message || 'Failed to delete student');
      throw error;
    }
  };

  const clearError = () => {
    setError(null);
  };

  useEffect(() => {
    fetchStudents();
  }, []);

  return {
    students,
    isLoading,
    error,
    createStudent,
    updateStudent,
    deleteStudent,
    refreshStudents: fetchStudents,
    clearError,
  };
}