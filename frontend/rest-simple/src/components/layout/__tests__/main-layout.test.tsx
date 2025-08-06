import { render, screen } from '@testing-library/react';
import { MainLayout } from '../main-layout';
import { useAuth } from '@/hooks/use-auth';

// Mock useAuth hook
jest.mock('@/hooks/use-auth', () => ({
  useAuth: jest.fn(),
}));

// Mock child components
jest.mock('../header', () => ({
  Header: () => {
    const { useAuth } = require('@/hooks/use-auth');
    const { user } = useAuth();
    return <div data-testid="header">Header - {user?.username || 'No user'}</div>;
  },
}));

jest.mock('../sidebar', () => ({
  Sidebar: () => (
    <div data-testid="sidebar">Sidebar - Closed</div>
  ),
}));

const mockUseAuth = useAuth as jest.MockedFunction<typeof useAuth>;

describe('MainLayout', () => {
  const mockUser = {
    username: 'testuser',
    firstName: 'Test',
    lastName: 'User',
    email: 'test@example.com',
  };

  const defaultAuthState = {
    user: mockUser,
    isLoading: false,
    isAuthenticated: true,
    login: jest.fn(),
    register: jest.fn(),
    logout: jest.fn(),
    error: null,
    clearError: jest.fn(),
  };

  beforeEach(() => {
    mockUseAuth.mockReturnValue(defaultAuthState);
    jest.clearAllMocks();
  });

  it('should render main layout with header and sidebar', () => {
    render(
      <MainLayout>
        <div>Test Content</div>
      </MainLayout>
    );
    
    expect(screen.getByTestId('header')).toBeInTheDocument();
    expect(screen.getByTestId('sidebar')).toBeInTheDocument();
    expect(screen.getByText('Test Content')).toBeInTheDocument();
  });

  it('should pass user to header component', () => {
    render(
      <MainLayout>
        <div>Test Content</div>
      </MainLayout>
    );
    
    expect(screen.getByText('Header - testuser')).toBeInTheDocument();
  });

  it('should handle user being null', () => {
    mockUseAuth.mockReturnValue({
      ...defaultAuthState,
      user: null,
    });
    
    render(
      <MainLayout>
        <div>Test Content</div>
      </MainLayout>
    );
    
    expect(screen.getByText('Header - No user')).toBeInTheDocument();
  });

  it('should render children content in main area', () => {
    render(
      <MainLayout>
        <div data-testid="test-content">Custom Test Content</div>
      </MainLayout>
    );
    
    expect(screen.getByTestId('test-content')).toBeInTheDocument();
    expect(screen.getByText('Custom Test Content')).toBeInTheDocument();
  });

  it('should apply correct styling classes', () => {
    const { container } = render(
      <MainLayout>
        <div>Test Content</div>
      </MainLayout>
    );
    
    // Check for main container classes
    const mainContainer = container.querySelector('.min-h-screen.bg-gray-950');
    expect(mainContainer).toBeInTheDocument();
  });

  it('should have responsive layout structure', () => {
    const { container } = render(
      <MainLayout>
        <div>Test Content</div>
      </MainLayout>
    );
    
    // Check for responsive flex layout
    const flexContainer = container.querySelector('.flex');
    expect(flexContainer).toBeInTheDocument();
  });

  it('should render multiple children', () => {
    render(
      <MainLayout>
        <div>First Child</div>
        <div>Second Child</div>
        <span>Third Child</span>
      </MainLayout>
    );
    
    expect(screen.getByText('First Child')).toBeInTheDocument();
    expect(screen.getByText('Second Child')).toBeInTheDocument();
    expect(screen.getByText('Third Child')).toBeInTheDocument();
  });
});