import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { StudentsPage } from '../students-page';
import { useStudents } from '@/hooks/use-students';

jest.mock('@/hooks/use-students', () => ({ useStudents: jest.fn() }));

const mockedUseStudents = useStudents as jest.MockedFunction<typeof useStudents>;
const state = {
  students: [
    {
      id: 1,
      firstName: 'Lena',
      lastName: 'Hoffmann',
      email: 'lena@stud.example',
      studentNumber: 'M2023001',
      enrollmentDate: '2023-10-01',
    },
  ],
  isLoading: false,
  error: null,
  createStudent: jest.fn(),
  refreshStudents: jest.fn(),
  clearError: jest.fn(),
};

describe('StudentsPage', () => {
  beforeEach(() => mockedUseStudents.mockReturnValue(state));

  it('zeigt den Hochschulvertrag', () => {
    render(<StudentsPage />);
    expect(screen.getByText('Lena Hoffmann')).toBeInTheDocument();
    expect(screen.getByText('M2023001')).toBeInTheDocument();
    expect(screen.getByText('1 Datensätze')).toBeInTheDocument();
  });

  it('lädt die Liste neu', async () => {
    const user = userEvent.setup();
    render(<StudentsPage />);
    await user.click(screen.getByText('Aktualisieren'));
    expect(state.refreshStudents).toHaveBeenCalled();
  });
});
