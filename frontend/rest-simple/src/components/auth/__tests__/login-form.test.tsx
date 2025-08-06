import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { LoginForm } from '../login-form';
import { useAuth } from '@/hooks/use-auth';

// Mock useAuth hook
jest.mock('@/hooks/use-auth', () => ({
  useAuth: jest.fn(),
}));

const mockUseAuth = useAuth as jest.MockedFunction<typeof useAuth>;

describe('LoginForm', () => {
  const defaultAuthState = {
    login: jest.fn(),
    register: jest.fn(),
    isLoading: false,
    error: null,
    clearError: jest.fn(),
    user: null,
    isAuthenticated: false,
    logout: jest.fn(),
  };

  beforeEach(() => {
    mockUseAuth.mockReturnValue(defaultAuthState);
    jest.clearAllMocks();
  });

  it('should render login form by default', () => {
    render(<LoginForm />);
    
    expect(screen.getByRole('heading', { name: 'Login' })).toBeInTheDocument();
    expect(screen.getByLabelText('Username')).toBeInTheDocument();
    expect(screen.getByLabelText('Password')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Login' })).toBeInTheDocument();
  });

  it('should toggle to register mode', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    const toggleButton = screen.getByText('Need an account? Sign up');
    await user.click(toggleButton);
    
    expect(screen.getByText('Register')).toBeInTheDocument();
    expect(screen.getByLabelText('First Name')).toBeInTheDocument();
    expect(screen.getByLabelText('Last Name')).toBeInTheDocument();
    expect(screen.getByLabelText('Email')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Register' })).toBeInTheDocument();
  });

  it('should validate login form', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    const submitButton = screen.getByRole('button', { name: 'Login' });
    await user.click(submitButton);
    
    expect(screen.getByText('Username is required')).toBeInTheDocument();
    expect(screen.getByText('Password is required')).toBeInTheDocument();
    expect(defaultAuthState.login).not.toHaveBeenCalled();
  });

  it('should validate register form', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    // Switch to register mode
    await user.click(screen.getByText('Need an account? Sign up'));
    
    const submitButton = screen.getByRole('button', { name: 'Register' });
    await user.click(submitButton);
    
    expect(screen.getByText('Username is required')).toBeInTheDocument();
    expect(screen.getByText('First name is required')).toBeInTheDocument();
    expect(screen.getByText('Last name is required')).toBeInTheDocument();
    expect(screen.getByText('Email is required')).toBeInTheDocument();
    expect(screen.getByText('Password is required')).toBeInTheDocument();
    expect(defaultAuthState.register).not.toHaveBeenCalled();
  });

  it('should validate username length in register mode', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    // Switch to register mode
    await user.click(screen.getByText('Need an account? Sign up'));
    
    const usernameInput = screen.getByLabelText('Username');
    await user.type(usernameInput, 'ab'); // Too short
    await user.click(screen.getByRole('button', { name: 'Register' }));
    
    expect(screen.getByText('Username must be at least 3 characters')).toBeInTheDocument();
  });

  it('should validate password length in register mode', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    // Switch to register mode
    await user.click(screen.getByText('Need an account? Sign up'));
    
    const passwordInput = screen.getByLabelText('Password');
    await user.type(passwordInput, '123'); // Too short
    await user.click(screen.getByRole('button', { name: 'Register' }));
    
    expect(screen.getByText('Password must be at least 6 characters')).toBeInTheDocument();
  });

  it('should validate email format in register mode', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    // Switch to register mode
    await user.click(screen.getByText('Need an account? Sign up'));
    
    const emailInput = screen.getByLabelText('Email');
    await user.type(emailInput, 'invalid-email');
    await user.click(screen.getByRole('button', { name: 'Register' }));
    
    expect(screen.getByText('Please enter a valid email address')).toBeInTheDocument();
  });

  it('should submit login form with valid data', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    await user.type(screen.getByLabelText('Username'), 'testuser');
    await user.type(screen.getByLabelText('Password'), 'password123');
    await user.click(screen.getByRole('button', { name: 'Login' }));
    
    expect(defaultAuthState.login).toHaveBeenCalledWith({
      username: 'testuser',
      password: 'password123',
    });
  });

  it('should submit register form with valid data', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    // Switch to register mode
    await user.click(screen.getByText('Need an account? Sign up'));
    
    await user.type(screen.getByLabelText('Username'), 'newuser');
    await user.type(screen.getByLabelText('First Name'), 'John');
    await user.type(screen.getByLabelText('Last Name'), 'Doe');
    await user.type(screen.getByLabelText('Email'), 'john@example.com');
    await user.type(screen.getByLabelText('Password'), 'password123');
    await user.click(screen.getByRole('button', { name: 'Register' }));
    
    expect(defaultAuthState.register).toHaveBeenCalledWith({
      username: 'newuser',
      firstName: 'John',
      lastName: 'Doe',
      email: 'john@example.com',
      password: 'password123',
    });
  });

  it('should display loading state', () => {
    mockUseAuth.mockReturnValue({
      ...defaultAuthState,
      isLoading: true,
    });
    
    render(<LoginForm />);
    
    expect(screen.getByText('Logging in...')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Logging in...' })).toBeDisabled();
  });

  it('should display error message', () => {
    mockUseAuth.mockReturnValue({
      ...defaultAuthState,
      error: 'Invalid credentials',
    });
    
    render(<LoginForm />);
    
    expect(screen.getByText('Invalid credentials')).toBeInTheDocument();
  });

  it('should clear validation errors on input change', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    // Trigger validation error
    await user.click(screen.getByRole('button', { name: 'Login' }));
    expect(screen.getByText('Username is required')).toBeInTheDocument();
    
    // Start typing to clear error
    await user.type(screen.getByLabelText('Username'), 'test');
    expect(screen.queryByText('Username is required')).not.toBeInTheDocument();
  });

  it('should reset form when toggling modes', async () => {
    const user = userEvent.setup();
    render(<LoginForm />);
    
    // Fill login form
    await user.type(screen.getByLabelText('Username'), 'testuser');
    await user.type(screen.getByLabelText('Password'), 'password');
    
    // Switch to register mode
    await user.click(screen.getByText('Need an account? Sign up'));
    
    // Switch back to login mode
    await user.click(screen.getByText('Already have an account? Sign in'));
    
    // Check that form is reset
    expect(screen.getByLabelText('Username')).toHaveValue('');
    expect(screen.getByLabelText('Password')).toHaveValue('');
  });
});