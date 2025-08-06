import { renderHook, act, waitFor } from '@testing-library/react';
import { useStudents } from '../use-students';
import { apiClient } from '@/lib/api-client';
import { StudentResponse } from '@/types/api';

// Mock apiClient
jest.mock('@/lib/api-client', () => ({
  apiClient: {
    getAllStudents: jest.fn(),
    createStudent: jest.fn(),
    updateStudent: jest.fn(),
    deleteStudent: jest.fn(),
  },
}));

const mockApiClient = apiClient as jest.Mocked<typeof apiClient>;

describe('useStudents', () => {
  const mockStudents: StudentResponse[] = [
    {
      id: '1',
      name: 'John',
      lastName: 'Doe',
      mnr: 'MNR001',
      createdOn: '2023-01-01T00:00:00Z',
    },
    {
      id: '2',
      name: 'Jane',
      lastName: 'Smith',
      mnr: 'MNR002',
      createdOn: '2023-01-02T00:00:00Z',
    },
  ];

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should fetch students on initialization', async () => {
    mockApiClient.getAllStudents.mockResolvedValue({ students: mockStudents });

    const { result } = renderHook(() => useStudents());

    expect(result.current.isLoading).toBe(true);
    expect(result.current.students).toEqual([]);

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    expect(mockApiClient.getAllStudents).toHaveBeenCalledTimes(1);
    expect(result.current.students).toEqual(mockStudents);
    expect(result.current.error).toBeNull();
  });

  it('should handle fetch error', async () => {
    const error = new Error('Network error');
    (error as any).message = 'Network error';
    mockApiClient.getAllStudents.mockRejectedValue(error);

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    expect(result.current.error).toBe('Network error');
    expect(result.current.students).toEqual([]);
  });

  it('should create student successfully', async () => {
    const newStudentData = { name: 'Alice', lastName: 'Johnson' };
    const createdStudent: StudentResponse = {
      id: '3',
      name: 'Alice',
      lastName: 'Johnson',
      mnr: 'MNR003',
      createdOn: '2023-01-03T00:00:00Z',
    };

    mockApiClient.getAllStudents.mockResolvedValue({ students: mockStudents });
    mockApiClient.createStudent.mockResolvedValue(createdStudent);

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    let createdStudentResult: StudentResponse | undefined;

    await act(async () => {
      createdStudentResult = await result.current.createStudent(newStudentData);
    });

    expect(mockApiClient.createStudent).toHaveBeenCalledWith(newStudentData);
    expect(createdStudentResult).toEqual(createdStudent);
    expect(result.current.students).toHaveLength(3);
    expect(result.current.students[2]).toEqual(createdStudent);
    expect(result.current.error).toBeNull();
  });

  it('should handle create student error', async () => {
    const newStudentData = { name: 'Alice', lastName: 'Johnson' };
    const error = new Error('Validation failed');
    (error as any).message = 'Validation failed';

    mockApiClient.getAllStudents.mockResolvedValue({ students: mockStudents });
    mockApiClient.createStudent.mockRejectedValue(error);

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    await act(async () => {
      try {
        await result.current.createStudent(newStudentData);
      } catch (e) {
        // Expected to throw
      }
    });

    expect(result.current.error).toBe('Validation failed');
    expect(result.current.students).toHaveLength(2); // Unchanged
  });

  it('should update student successfully', async () => {
    const updateData = { name: 'John Updated', lastName: 'Doe Updated' };
    const updatedStudent: StudentResponse = {
      id: '1',
      name: 'John Updated',
      lastName: 'Doe Updated',
      mnr: 'MNR001',
      createdOn: '2023-01-01T00:00:00Z',
    };

    mockApiClient.getAllStudents.mockResolvedValue({ students: mockStudents });
    mockApiClient.updateStudent.mockResolvedValue(updatedStudent);

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    let updatedStudentResult: StudentResponse | undefined;

    await act(async () => {
      updatedStudentResult = await result.current.updateStudent('1', updateData);
    });

    expect(mockApiClient.updateStudent).toHaveBeenCalledWith('1', updateData);
    expect(updatedStudentResult).toEqual(updatedStudent);
    expect(result.current.students[0]).toEqual(updatedStudent);
    expect(result.current.error).toBeNull();
  });

  it('should handle update student error', async () => {
    const updateData = { name: 'John Updated', lastName: 'Doe Updated' };
    const error = new Error('Not found');
    (error as any).message = 'Not found';

    mockApiClient.getAllStudents.mockResolvedValue({ students: mockStudents });
    mockApiClient.updateStudent.mockRejectedValue(error);

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    await act(async () => {
      try {
        await result.current.updateStudent('1', updateData);
      } catch (e) {
        // Expected to throw
      }
    });

    expect(result.current.error).toBe('Not found');
    expect(result.current.students[0]).toEqual(mockStudents[0]); // Unchanged
  });

  it('should delete student successfully', async () => {
    mockApiClient.getAllStudents.mockResolvedValue({ students: mockStudents });
    mockApiClient.deleteStudent.mockResolvedValue();

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    expect(result.current.students).toHaveLength(2);

    await act(async () => {
      await result.current.deleteStudent('1');
    });

    expect(mockApiClient.deleteStudent).toHaveBeenCalledWith('1');
    expect(result.current.students).toHaveLength(1);
    expect(result.current.students[0].id).toBe('2');
    expect(result.current.error).toBeNull();
  });

  it('should handle delete student error', async () => {
    const error = new Error('Cannot delete');
    (error as any).message = 'Cannot delete';

    mockApiClient.getAllStudents.mockResolvedValue({ students: mockStudents });
    mockApiClient.deleteStudent.mockRejectedValue(error);

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    await act(async () => {
      try {
        await result.current.deleteStudent('1');
      } catch (e) {
        // Expected to throw
      }
    });

    expect(result.current.error).toBe('Cannot delete');
    expect(result.current.students).toHaveLength(2); // Unchanged
  });

  it('should refresh students', async () => {
    const newMockStudents = [...mockStudents, {
      id: '3',
      name: 'Bob',
      lastName: 'Wilson',
      mnr: 'MNR003',
      createdOn: '2023-01-03T00:00:00Z',
    }];

    mockApiClient.getAllStudents
      .mockResolvedValueOnce({ students: mockStudents })
      .mockResolvedValueOnce({ students: newMockStudents });

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    expect(result.current.students).toHaveLength(2);

    await act(async () => {
      await result.current.refreshStudents();
    });

    expect(result.current.students).toHaveLength(3);
    expect(result.current.students[2].name).toBe('Bob');
  });

  it('should clear error', async () => {
    const error = new Error('Test error');
    mockApiClient.getAllStudents.mockRejectedValue(error);

    const { result } = renderHook(() => useStudents());

    await waitFor(() => {
      expect(result.current.error).toBe('Test error');
    });

    act(() => {
      result.current.clearError();
    });

    expect(result.current.error).toBeNull();
  });
});