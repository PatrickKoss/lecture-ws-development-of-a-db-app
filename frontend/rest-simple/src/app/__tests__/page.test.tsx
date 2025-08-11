import { render, screen, waitFor, act } from '@testing-library/react';
import Dashboard from '../page';
import { apiClient } from '@/lib/api-client';
import { appConfig } from '@/lib/config';

// Mock apiClient
jest.mock('@/lib/api-client', () => ({
  apiClient: {
    healthCheck: jest.fn(),
    getAllStudents: jest.fn(),
  },
}));

// Mock Next.js Link
jest.mock('next/link', () => {
  return ({ children, href }: { children: React.ReactNode; href: string }) => (
    <a href={href}>{children}</a>
  );
});

const mockApiClient = apiClient as jest.Mocked<typeof apiClient>;

describe('Dashboard', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('should render dashboard title and description', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    expect(screen.getByText('Dashboard')).toBeInTheDocument();
    expect(screen.getByText('Overview of your REST Simple application')).toBeInTheDocument();
    
    // Wait for async effects to complete
    await waitFor(() => {
      expect(mockApiClient.healthCheck).toHaveBeenCalled();
      expect(mockApiClient.getAllStudents).toHaveBeenCalled();
    });
  });

  it('should display API status as healthy', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('Healthy')).toBeInTheDocument();
    });
    
    expect(screen.getByText('API Status')).toBeInTheDocument();
  });

  it('should display API status as error when health check fails', async () => {
    mockApiClient.healthCheck.mockRejectedValue(new Error('Network error'));
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('Error')).toBeInTheDocument();
    });
  });

  it('should display loading state initially', async () => {
    mockApiClient.healthCheck.mockImplementation(() => new Promise(() => {})); // Never resolves
    mockApiClient.getAllStudents.mockImplementation(() => new Promise(() => {}));
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    expect(screen.getByText('Checking...')).toBeInTheDocument();
  });

  it('should display student count', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ 
      students: [
        { id: '1', name: 'John', lastName: 'Doe', mnr: 'MNR001', createdOn: '2023-01-01' },
        { id: '2', name: 'Jane', lastName: 'Smith', mnr: 'MNR002', createdOn: '2023-01-01' }
      ] 
    });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('2')).toBeInTheDocument();
    });
    
    expect(screen.getByText('Students in the system')).toBeInTheDocument();
  });

  it('should display zero students when no students exist', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('0')).toBeInTheDocument();
    });
  });

  it('should display -- when student count fails to load', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockRejectedValue(new Error('Failed to fetch'));
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('--')).toBeInTheDocument();
    });
  });

  it('should display authentication status', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('Authentication')).toBeInTheDocument();
      // Look for Enabled/Disabled specifically in the authentication card context
      const enabledElements = screen.getAllByText(appConfig.auth.enabled ? 'Enabled' : 'Disabled');
      expect(enabledElements.length).toBeGreaterThan(0);
    });
  });

  it('should display quick actions', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('Quick Actions')).toBeInTheDocument();
      expect(screen.getByText('Manage Students')).toBeInTheDocument();
      expect(screen.getByText('Check API Health')).toBeInTheDocument();
    });
  });

  it('should display register button when auth is enabled', async () => {
    // Mock auth enabled
    jest.doMock('@/lib/config', () => ({
      appConfig: {
        ...appConfig,
        auth: { ...appConfig.auth, enabled: true },
      },
    }));
    
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      if (appConfig.auth.enabled) {
        expect(screen.getByText('Register New User')).toBeInTheDocument();
      }
    });
  });

  it('should display system information', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      expect(screen.getByText('System Information')).toBeInTheDocument();
      expect(screen.getByText('API Base URL:')).toBeInTheDocument();
      expect(screen.getByText('Request Timeout:')).toBeInTheDocument();
      // Be more specific about the text we're looking for
      const apiUrlElements = screen.getAllByText(appConfig.api.baseUrl);
      expect(apiUrlElements.length).toBeGreaterThan(0);
      expect(screen.getByText('30s')).toBeInTheDocument();
    });
  });

  it('should display auth disabled warning when auth is disabled', async () => {
    // Mock auth disabled
    jest.doMock('@/lib/config', () => ({
      appConfig: {
        ...appConfig,
        auth: { ...appConfig.auth, enabled: false },
      },
    }));
    
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    if (!appConfig.auth.enabled) {
      await waitFor(() => {
        expect(screen.getByText('Authentication is disabled')).toBeInTheDocument();
      });
    }
  });

  it('should have correct navigation links', async () => {
    mockApiClient.healthCheck.mockResolvedValue({ status: 'ok' });
    mockApiClient.getAllStudents.mockResolvedValue({ students: [] });
    
    await act(async () => {
      render(<Dashboard />);
    });
    
    await waitFor(() => {
      const studentsLink = screen.getByRole('link', { name: /manage students/i });
      const healthLink = screen.getByRole('link', { name: /check api health/i });
      
      expect(studentsLink).toHaveAttribute('href', '/students');
      expect(healthLink).toHaveAttribute('href', '/health');
      
      if (appConfig.auth.enabled) {
        const registerLink = screen.getByRole('link', { name: /register new user/i });
        expect(registerLink).toHaveAttribute('href', '/register');
      }
    });
  });
});