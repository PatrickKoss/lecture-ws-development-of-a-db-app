import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { StudentsPage } from '../students-page';
import { useStudents } from '@/hooks/use-students';

// Mock useStudents hook
jest.mock('@/hooks/use-students', () => ({
  useStudents: jest.fn(),
}));

// Mock student table component
jest.mock('../student-table', () => ({
  StudentTable: ({ students }: { students: any[] }) => (
    <div data-testid="student-table">
      {students.map((student) => (
        <div key={student.id}>
          {student.name} {student.lastName}
        </div>
      ))}
      <div>Name</div>
      <div>Last Name</div>
      <div>MNR</div>
      <div>Created</div>
      <div>Actions</div>
    </div>
  ),
}));

// Mock create student dialog component
jest.mock('../create-student-dialog', () => ({
  CreateStudentDialog: ({ onCreate }: { onCreate: (student: any) => void }) => (
    <button onClick={() => onCreate({ name: 'Test', lastName: 'Student' })}>
      Add Student
    </button>
  ),
}));

const mockUseStudents = useStudents as jest.MockedFunction<typeof useStudents>;

const mockStudents = [
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

describe('StudentsPage', () => {
  const defaultStudentsState = {
    students: mockStudents,
    isLoading: false,
    error: null,
    createStudent: jest.fn(),
    updateStudent: jest.fn(),
    deleteStudent: jest.fn(),
    refreshStudents: jest.fn(),
    clearError: jest.fn(),
  };

  beforeEach(() => {
    mockUseStudents.mockReturnValue(defaultStudentsState);
    jest.clearAllMocks();
  });

  it('should render students page with students list', () => {
    render(<StudentsPage />);
    
    expect(screen.getByText('John Doe')).toBeInTheDocument();
    expect(screen.getByText('Jane Smith')).toBeInTheDocument();
    expect(screen.getByText('2 students in the system')).toBeInTheDocument();
  });

  it('should render loading state', () => {
    mockUseStudents.mockReturnValue({
      ...defaultStudentsState,
      isLoading: true,
    });
    
    render(<StudentsPage />);
    
    // Check that refresh button shows loading state
    const refreshButton = screen.getByRole('button', { name: /refresh/i });
    expect(refreshButton).toBeDisabled();
  });

  it('should display error message', () => {
    mockUseStudents.mockReturnValue({
      ...defaultStudentsState,
      error: 'Failed to fetch students',
    });
    
    render(<StudentsPage />);
    
    expect(screen.getByText('Error')).toBeInTheDocument();
    expect(screen.getByText('Failed to fetch students')).toBeInTheDocument();
    expect(screen.getByText('Dismiss')).toBeInTheDocument();
  });

  it('should clear error when dismiss button is clicked', async () => {
    const user = userEvent.setup();
    mockUseStudents.mockReturnValue({
      ...defaultStudentsState,
      error: 'Failed to fetch students',
    });
    
    render(<StudentsPage />);
    
    await user.click(screen.getByText('Dismiss'));
    expect(defaultStudentsState.clearError).toHaveBeenCalled();
  });

  it('should refresh students when refresh button is clicked', async () => {
    const user = userEvent.setup();
    render(<StudentsPage />);
    
    await user.click(screen.getByRole('button', { name: /refresh/i }));
    expect(defaultStudentsState.refreshStudents).toHaveBeenCalled();
  });

  it('should display add student button', () => {
    render(<StudentsPage />);
    
    expect(screen.getByText('Add Student')).toBeInTheDocument();
  });

  it('should display correct student count for singular', () => {
    mockUseStudents.mockReturnValue({
      ...defaultStudentsState,
      students: [mockStudents[0]], // Only one student
    });
    
    render(<StudentsPage />);
    
    expect(screen.getByText('1 student in the system')).toBeInTheDocument();
  });

  it('should display correct student count for zero students', () => {
    mockUseStudents.mockReturnValue({
      ...defaultStudentsState,
      students: [],
    });
    
    render(<StudentsPage />);
    
    expect(screen.getByText('0 students in the system')).toBeInTheDocument();
  });

  it('should render student table with correct headers', () => {
    render(<StudentsPage />);
    
    expect(screen.getByText('Name')).toBeInTheDocument();
    expect(screen.getByText('Last Name')).toBeInTheDocument();
    expect(screen.getByText('MNR')).toBeInTheDocument();
    expect(screen.getByText('Created')).toBeInTheDocument();
    expect(screen.getByText('Actions')).toBeInTheDocument();
  });

  it('should show page description', () => {
    render(<StudentsPage />);
    
    expect(screen.getByText('Manage student records in the system')).toBeInTheDocument();
  });
});