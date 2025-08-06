import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CreateStudentDialog } from '../create-student-dialog';

describe('CreateStudentDialog', () => {
  const mockOnCreate = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render trigger button', () => {
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    expect(screen.getByText('Add Student')).toBeInTheDocument();
  });

  it('should open dialog when trigger button is clicked', async () => {
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    
    expect(screen.getByText('Create New Student')).toBeInTheDocument();
    expect(screen.getByLabelText('First Name')).toBeInTheDocument();
    expect(screen.getByLabelText('Last Name')).toBeInTheDocument();
  });

  it('should validate form fields', async () => {
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    await user.click(screen.getByText('Create Student'));
    
    expect(screen.getByText('First name is required')).toBeInTheDocument();
    expect(screen.getByText('Last name is required')).toBeInTheDocument();
    expect(mockOnCreate).not.toHaveBeenCalled();
  });

  it('should validate first name length', async () => {
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    
    // Since maxLength prevents typing more than 200 chars, we need to simulate it differently
    // We'll directly test the validation function by triggering it with programmatic value
    const firstNameInput = screen.getByLabelText('First Name') as HTMLInputElement;
    
    // Clear the maxLength temporarily to allow typing more
    firstNameInput.removeAttribute('maxLength');
    await user.type(firstNameInput, 'a'.repeat(201));
    await user.click(screen.getByText('Create Student'));
    
    await waitFor(() => {
      expect(screen.getByText('First name must be 200 characters or less')).toBeInTheDocument();
    });
  });

  it('should validate character restrictions', async () => {
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    
    const firstNameInput = screen.getByLabelText('First Name');
    const lastNameInput = screen.getByLabelText('Last Name');
    
    await user.type(firstNameInput, 'John123'); // Invalid characters
    await user.type(lastNameInput, 'Doe!'); // Invalid characters
    await user.click(screen.getByText('Create Student'));
    
    await waitFor(() => {
      expect(screen.getByText('First name contains invalid characters. Only letters, spaces, hyphens and apostrophes are allowed')).toBeInTheDocument();
      expect(screen.getByText('Last name contains invalid characters. Only letters, spaces, hyphens and apostrophes are allowed')).toBeInTheDocument();
    });
  });

  it('should allow valid characters', async () => {
    const user = userEvent.setup();
    mockOnCreate.mockResolvedValue({});
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    
    const firstNameInput = screen.getByLabelText('First Name');
    const lastNameInput = screen.getByLabelText('Last Name');
    
    await user.type(firstNameInput, "Jean-Pierre O'Connor"); // Valid characters
    await user.type(lastNameInput, "Van Der Berg-Smith"); // Valid characters
    await user.click(screen.getByText('Create Student'));
    
    expect(mockOnCreate).toHaveBeenCalledWith({
      name: "Jean-Pierre O'Connor",
      lastName: "Van Der Berg-Smith",
    });
  });

  it('should submit form with valid data', async () => {
    const user = userEvent.setup();
    mockOnCreate.mockResolvedValue({});
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    
    await user.type(screen.getByLabelText('First Name'), 'John');
    await user.type(screen.getByLabelText('Last Name'), 'Doe');
    await user.click(screen.getByText('Create Student'));
    
    await waitFor(() => {
      expect(mockOnCreate).toHaveBeenCalledWith({
        name: 'John',
        lastName: 'Doe',
      });
    });
  });

  it('should show loading state during submission', async () => {
    const user = userEvent.setup();
    let resolveCreate: (value: any) => void;
    mockOnCreate.mockImplementation(() => new Promise(resolve => {
      resolveCreate = resolve;
    }));
    
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    await user.type(screen.getByLabelText('First Name'), 'John');
    await user.type(screen.getByLabelText('Last Name'), 'Doe');
    await user.click(screen.getByText('Create Student'));
    
    expect(screen.getByText('Creating...')).toBeInTheDocument();
    
    // Resolve the promise to complete the test
    resolveCreate!({});
  });

  it('should display API error in dialog', async () => {
    const user = userEvent.setup();
    mockOnCreate.mockRejectedValue(new Error('Server error'));
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    await user.type(screen.getByLabelText('First Name'), 'John');
    await user.type(screen.getByLabelText('Last Name'), 'Doe');
    await user.click(screen.getByText('Create Student'));
    
    await waitFor(() => {
      expect(screen.getByText('Server error')).toBeInTheDocument();
    });
    
    // Dialog should remain open
    expect(screen.getByText('Create New Student')).toBeInTheDocument();
  });

  it('should clear validation errors when typing', async () => {
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    
    // Trigger validation error
    await user.click(screen.getByText('Create Student'));
    expect(screen.getByText('First name is required')).toBeInTheDocument();
    
    // Start typing to clear error
    await user.type(screen.getByLabelText('First Name'), 'J');
    expect(screen.queryByText('First name is required')).not.toBeInTheDocument();
  });

  it('should clear API error when typing', async () => {
    const user = userEvent.setup();
    mockOnCreate.mockRejectedValue(new Error('Server error'));
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    await user.type(screen.getByLabelText('First Name'), 'John');
    await user.type(screen.getByLabelText('Last Name'), 'Doe');
    await user.click(screen.getByText('Create Student'));
    
    await waitFor(() => {
      expect(screen.getByText('Server error')).toBeInTheDocument();
    });
    
    // Start typing to clear error
    await user.type(screen.getByLabelText('First Name'), ' Updated');
    expect(screen.queryByText('Server error')).not.toBeInTheDocument();
  });

  it('should close dialog and reset form on successful submission', async () => {
    const user = userEvent.setup();
    mockOnCreate.mockResolvedValue({});
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    await user.type(screen.getByLabelText('First Name'), 'John');
    await user.type(screen.getByLabelText('Last Name'), 'Doe');
    await user.click(screen.getByText('Create Student'));
    
    await waitFor(() => {
      expect(screen.queryByText('Create New Student')).not.toBeInTheDocument();
    });
    
    // Reopen dialog to check if form is reset
    await user.click(screen.getByText('Add Student'));
    expect(screen.getByLabelText('First Name')).toHaveValue('');
    expect(screen.getByLabelText('Last Name')).toHaveValue('');
  });

  it('should close dialog when cancel button is clicked', async () => {
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={mockOnCreate} />);
    
    await user.click(screen.getByText('Add Student'));
    expect(screen.getByText('Create New Student')).toBeInTheDocument();
    
    await user.click(screen.getByText('Cancel'));
    expect(screen.queryByText('Create New Student')).not.toBeInTheDocument();
  });
});