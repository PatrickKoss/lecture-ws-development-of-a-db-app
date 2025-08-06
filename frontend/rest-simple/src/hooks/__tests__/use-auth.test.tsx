import { renderHook, act, waitFor } from '@testing-library/react';
import { ReactNode } from 'react';
import { AuthProvider, useAuth } from '../use-auth';
import { apiClient } from '@/lib/api-client';
import { appConfig } from '@/lib/config';

// Mock apiClient
jest.mock('@/lib/api-client', () => ({
  apiClient: {
    login: jest.fn(),
    register: jest.fn(),
    logout: jest.fn(),
  },
}));

// Mock localStorage
const mockLocalStorage = {
  getItem: jest.fn(),
  setItem: jest.fn(),
  removeItem: jest.fn(),
  clear: jest.fn(),
};

// Ensure the localStorage mock is properly attached
Object.defineProperty(global, 'localStorage', {
  value: mockLocalStorage,
  writable: true,
});

Object.defineProperty(window, 'localStorage', {
  value: mockLocalStorage,
  writable: true,
});

// Mock Date.now for token expiry tests
const mockDateNow = jest.spyOn(Date, 'now');

describe('useAuth', () => {
  const wrapper = ({ children }: { children: ReactNode }) => (
    <AuthProvider>{children}</AuthProvider>
  );

  beforeEach(() => {
    jest.clearAllMocks();
    mockLocalStorage.getItem.mockReturnValue(null);
    mockDateNow.mockReturnValue(new Date('2023-01-01T10:00:00Z').getTime());
  });

  afterEach(() => {
    mockDateNow.mockRestore();
  });

  it('should throw error when used outside AuthProvider', () => {
    // Capture console.error to avoid test output noise
    const consoleSpy = jest.spyOn(console, 'error').mockImplementation(() => {});
    
    expect(() => {
      renderHook(() => useAuth());
    }).toThrow('useAuth must be used within an AuthProvider');
    
    consoleSpy.mockRestore();
  });

  it('should initialize with correct default values when auth disabled', () => {
    // Temporarily mock appConfig.auth.enabled
    const originalAuthEnabled = appConfig.auth.enabled;
    Object.defineProperty(appConfig.auth, 'enabled', {
      value: false,
      writable: true,
      configurable: true
    });

    const { result } = renderHook(() => useAuth(), { wrapper });

    expect(result.current.user).toBeNull();
    expect(result.current.isAuthenticated).toBe(true); // true when auth disabled
    expect(result.current.isLoading).toBe(false);
    expect(result.current.error).toBeNull();
    
    // Restore original value
    Object.defineProperty(appConfig.auth, 'enabled', {
      value: originalAuthEnabled,
      writable: true,
      configurable: true
    });
  });

  it('should initialize with stored valid token', async () => {
    const validToken = 'header.' + btoa(JSON.stringify({
      sub: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      email: 'test@example.com',
      exp: Math.floor(Date.now() / 1000) + 3600, // 1 hour from now
    })) + '.signature';

    // Mock localStorage to return the valid token
    mockLocalStorage.getItem.mockImplementation((key: string) => {
      if (key === appConfig.auth.tokenStorageKey) {
        return validToken;
      }
      return null;
    });

    const { result } = renderHook(() => useAuth(), { wrapper });

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    expect(result.current.user).toEqual({
      username: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      email: 'test@example.com',
    });
    expect(result.current.isAuthenticated).toBe(true);
  });

  it('should clear expired token', async () => {
    const expiredToken = 'header.' + btoa(JSON.stringify({
      sub: 'testuser',
      exp: Math.floor(Date.now() / 1000) - 3600, // 1 hour ago
    })) + '.signature';

    mockLocalStorage.getItem.mockImplementation((key: string) => {
      if (key === appConfig.auth.tokenStorageKey) {
        return expiredToken;
      }
      return null;
    });

    const { result } = renderHook(() => useAuth(), { wrapper });

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
    });

    expect(mockLocalStorage.removeItem).toHaveBeenCalledWith(appConfig.auth.tokenStorageKey);
    expect(mockLocalStorage.removeItem).toHaveBeenCalledWith(appConfig.auth.refreshTokenStorageKey);
    expect(result.current.user).toBeNull();
    expect(result.current.isAuthenticated).toBe(false);
  });

  it('should handle login successfully', async () => {
    const credentials = { username: 'test', password: 'password' };
    const loginResponse = {
      access_token: 'header.' + btoa(JSON.stringify({
        sub: 'test',
        firstName: 'Test',
        lastName: 'User',
        email: 'test@example.com',
        exp: Math.floor(Date.now() / 1000) + 3600,
      })) + '.signature',
      refresh_token: 'refresh123',
    };

    (apiClient.login as jest.Mock).mockResolvedValue(loginResponse);

    const { result } = renderHook(() => useAuth(), { wrapper });

    await act(async () => {
      await result.current.login(credentials);
    });

    expect(apiClient.login).toHaveBeenCalledWith(credentials);
    expect(result.current.user).toEqual({
      username: 'test',
      firstName: 'Test',
      lastName: 'User',
      email: 'test@example.com',
    });
    expect(result.current.error).toBeNull();
  });

  it('should handle login failure', async () => {
    const credentials = { username: 'test', password: 'wrong' };
    const loginError = new Error('Invalid credentials');
    (loginError as any).message = 'Invalid credentials';

    (apiClient.login as jest.Mock).mockRejectedValue(loginError);

    const { result } = renderHook(() => useAuth(), { wrapper });

    await act(async () => {
      try {
        await result.current.login(credentials);
      } catch (error) {
        // Expected to throw
      }
    });

    expect(result.current.error).toBe('Invalid credentials');
    expect(result.current.user).toBeNull();
  });

  it('should handle register successfully', async () => {
    const userData = {
      username: 'newuser',
      firstName: 'New',
      lastName: 'User',
      email: 'new@example.com',
      password: 'password',
    };

    (apiClient.register as jest.Mock).mockResolvedValue({});

    const { result } = renderHook(() => useAuth(), { wrapper });

    await act(async () => {
      await result.current.register(userData);
    });

    expect(apiClient.register).toHaveBeenCalledWith(userData);
    expect(result.current.error).toBeNull();
  });

  it('should handle register failure', async () => {
    const userData = {
      username: 'newuser',
      firstName: 'New',
      lastName: 'User', 
      email: 'new@example.com',
      password: 'password',
    };
    const registerError = new Error('Username already exists');
    (registerError as any).message = 'Username already exists';

    (apiClient.register as jest.Mock).mockRejectedValue(registerError);

    const { result } = renderHook(() => useAuth(), { wrapper });

    await act(async () => {
      try {
        await result.current.register(userData);
      } catch (error) {
        // Expected to throw
      }
    });

    expect(result.current.error).toBe('Username already exists');
  });

  it('should handle logout', async () => {
    // Set up initial authenticated state
    const validToken = 'header.' + btoa(JSON.stringify({
      sub: 'testuser',
      firstName: 'Test',
      lastName: 'User',
      email: 'test@example.com',
      exp: Math.floor(Date.now() / 1000) + 3600,
    })) + '.signature';

    mockLocalStorage.getItem.mockImplementation((key: string) => {
      if (key === appConfig.auth.tokenStorageKey) {
        return validToken;
      }
      return null;
    });

    const { result } = renderHook(() => useAuth(), { wrapper });

    await waitFor(() => {
      expect(result.current.user).not.toBeNull();
    });

    act(() => {
      result.current.logout();
    });

    expect(apiClient.logout).toHaveBeenCalled();
    expect(result.current.user).toBeNull();
    expect(result.current.error).toBeNull();
  });

  it('should clear error', async () => {
    const { result } = renderHook(() => useAuth(), { wrapper });

    // Set error through failed login
    (apiClient.login as jest.Mock).mockRejectedValue(new Error('Test error'));

    await act(async () => {
      try {
        await result.current.login({ username: 'test', password: 'wrong' });
      } catch (error) {
        // Expected to throw
      }
    });

    expect(result.current.error).toBe('Test error');

    act(() => {
      result.current.clearError();
    });

    expect(result.current.error).toBeNull();
  });
});