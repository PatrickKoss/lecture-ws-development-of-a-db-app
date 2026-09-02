import { act, renderHook, waitFor } from '@testing-library/react';
import { useStudents } from '../use-students';
import { apiClient } from '@/lib/api-client';

jest.mock('@/lib/api-client', () => ({
  apiClient: {
    getAllStudents: jest.fn(),
    createStudent: jest.fn(),
  },
}));

const mockedApi = apiClient as jest.Mocked<typeof apiClient>;
const lena = {
  id: 1,
  firstName: 'Lena',
  lastName: 'Hoffmann',
  email: 'lena@stud.example',
  studentNumber: 'M2023001',
  enrollmentDate: '2023-10-01',
};

describe('useStudents', () => {
  beforeEach(() => jest.clearAllMocks());

  it('lädt das JSON-Array', async () => {
    mockedApi.getAllStudents.mockResolvedValue([lena]);
    const { result } = renderHook(() => useStudents());
    await waitFor(() => expect(result.current.isLoading).toBe(false));
    expect(result.current.students).toEqual([lena]);
  });

  it('hängt einen angelegten Datensatz an', async () => {
    const ada = { ...lena, id: 2, firstName: 'Ada', studentNumber: 'M2026001' };
    mockedApi.getAllStudents.mockResolvedValue([lena]);
    mockedApi.createStudent.mockResolvedValue(ada);
    const { result } = renderHook(() => useStudents());
    await waitFor(() => expect(result.current.isLoading).toBe(false));

    await act(async () => {
      await result.current.createStudent({
        firstName: 'Ada',
        lastName: 'Lovelace',
        email: 'ada@stud.example',
        studentNumber: 'M2026001',
      });
    });

    expect(result.current.students).toHaveLength(2);
  });
});
