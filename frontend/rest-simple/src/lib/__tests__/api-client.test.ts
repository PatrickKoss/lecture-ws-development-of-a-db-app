import { apiClient } from '../api-client';
import { appConfig } from '../config';

// Mock fetch
const mockFetch = jest.fn();
global.fetch = mockFetch;

// Mock window.localStorage
const mockLocalStorage = {
  getItem: jest.fn(),
  setItem: jest.fn(),
  removeItem: jest.fn(),
  clear: jest.fn(),
};

// Mock localStorage on global scope (already mocked in jest.setup.js)
Object.defineProperty(global, 'localStorage', {
  value: mockLocalStorage,
  writable: true,
});

describe('ApiClient', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    mockFetch.mockClear();
    mockLocalStorage.getItem.mockClear();
    mockLocalStorage.setItem.mockClear();
    mockLocalStorage.removeItem.mockClear();
  });

  describe('Authentication methods', () => {
    it('should login successfully and store tokens', async () => {
      const loginData = { username: 'test', password: 'password' };
      const authResponse = { access_token: 'access123', refresh_token: 'refresh123' };

      mockFetch.mockResolvedValueOnce({
        ok: true,
        json: async () => authResponse,
        status: 200,
      });

      const result = await apiClient.login(loginData);

      expect(mockFetch).toHaveBeenCalledWith(
        `${appConfig.api.baseUrl}/auth/login`,
        expect.objectContaining({
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(loginData),
        })
      );

      // Check if auth is enabled before expecting token storage
      if (appConfig.auth.enabled) {
        expect(mockLocalStorage.setItem).toHaveBeenCalledWith('access_token', 'access123');
        expect(mockLocalStorage.setItem).toHaveBeenCalledWith('refresh_token', 'refresh123');
      }
      expect(result).toEqual(authResponse);
    });

    it('should register successfully', async () => {
      const registerData = {
        username: 'test',
        firstName: 'Test',
        lastName: 'User',
        email: 'test@example.com',
        password: 'password',
      };

      mockFetch.mockResolvedValueOnce({
        ok: true,
        json: async () => ({}),
        status: 201,
      });

      await apiClient.register(registerData);

      expect(mockFetch).toHaveBeenCalledWith(
        `${appConfig.api.baseUrl}/auth/register`,
        expect.objectContaining({
          method: 'POST',
          body: JSON.stringify(registerData),
        })
      );
    });

    it('should logout and clear tokens', async () => {
      await apiClient.logout();
      
      expect(mockLocalStorage.removeItem).toHaveBeenCalledWith('access_token');
      expect(mockLocalStorage.removeItem).toHaveBeenCalledWith('refresh_token');
    });
  });

  describe('Student methods', () => {
    beforeEach(() => {
      mockLocalStorage.getItem.mockReturnValue('mock-token');
    });

    it('should get all students', async () => {
      const studentsResponse = {
        students: [
          { id: '1', name: 'John', lastName: 'Doe', mnr: 'MNR001', createdOn: '2023-01-01' }
        ]
      };

      mockFetch.mockResolvedValueOnce({
        ok: true,
        json: async () => studentsResponse,
        status: 200,
      });

      const result = await apiClient.getAllStudents();

      expect(mockFetch).toHaveBeenCalledWith(
        `${appConfig.api.baseUrl}/students`,
        expect.objectContaining({
          headers: expect.objectContaining({
            'Content-Type': 'application/json',
            ...(appConfig.auth.enabled ? { 'Authorization': 'Bearer mock-token' } : {})
          }),
        })
      );
      expect(result).toEqual(studentsResponse);
    });

    it('should create a student', async () => {
      const studentData = { name: 'John', lastName: 'Doe' };
      const createdStudent = {
        id: '1',
        name: 'John',
        lastName: 'Doe',
        mnr: 'MNR001',
        createdOn: '2023-01-01',
      };

      mockFetch.mockResolvedValueOnce({
        ok: true,
        json: async () => createdStudent,
        status: 201,
      });

      const result = await apiClient.createStudent(studentData);

      expect(mockFetch).toHaveBeenCalledWith(
        `${appConfig.api.baseUrl}/students`,
        expect.objectContaining({
          method: 'POST',
          body: JSON.stringify(studentData),
        })
      );
      expect(result).toEqual(createdStudent);
    });

    it('should update a student', async () => {
      const studentId = '1';
      const updateData = { name: 'Jane', lastName: 'Smith' };
      const updatedStudent = {
        id: '1',
        name: 'Jane',
        lastName: 'Smith',
        mnr: 'MNR001',
        createdOn: '2023-01-01',
      };

      mockFetch.mockResolvedValueOnce({
        ok: true,
        json: async () => updatedStudent,
        status: 200,
      });

      const result = await apiClient.updateStudent(studentId, updateData);

      expect(mockFetch).toHaveBeenCalledWith(
        `${appConfig.api.baseUrl}/students/${studentId}`,
        expect.objectContaining({
          method: 'PUT',
          body: JSON.stringify(updateData),
        })
      );
      expect(result).toEqual(updatedStudent);
    });

    it('should delete a student', async () => {
      const studentId = '1';

      mockFetch.mockResolvedValueOnce({
        ok: true,
        status: 204,
      });

      await apiClient.deleteStudent(studentId);

      expect(mockFetch).toHaveBeenCalledWith(
        `${appConfig.api.baseUrl}/students/${studentId}`,
        expect.objectContaining({
          method: 'DELETE',
        })
      );
    });
  });

  describe('Error handling', () => {
    it('should throw error for failed requests', async () => {
      const errorResponse = { message: 'Not found' };
      
      mockFetch.mockResolvedValueOnce({
        ok: false,
        status: 404,
        json: async () => errorResponse,
      });

      await expect(apiClient.getAllStudents()).rejects.toThrow('Not found');
    });

    it('should handle timeout errors', async () => {
      jest.useFakeTimers();
      
      // Mock a long-running request
      let timeoutId: NodeJS.Timeout;
      mockFetch.mockImplementationOnce(() => 
        new Promise((resolve) => {
          timeoutId = setTimeout(() => resolve({
            ok: true,
            json: async () => ({}),
          }), appConfig.api.timeout + 1000);
        })
      );

      const promise = apiClient.getAllStudents();
      
      // Fast-forward time to trigger timeout
      jest.advanceTimersByTime(appConfig.api.timeout);
      
      await expect(promise).rejects.toThrow('Request timeout');
      
      // Clean up
      clearTimeout(timeoutId!);
      jest.useRealTimers();
    }, 10000);

    it('should handle network errors', async () => {
      mockFetch.mockRejectedValueOnce(new Error('Network error'));

      await expect(apiClient.getAllStudents()).rejects.toThrow('Network error');
    });
  });

  describe('Health check', () => {
    it('should perform health check', async () => {
      const healthResponse = { status: 'ok' };

      mockFetch.mockResolvedValueOnce({
        ok: true,
        json: async () => healthResponse,
        status: 200,
      });

      const result = await apiClient.healthCheck();

      expect(mockFetch).toHaveBeenCalledWith(
        `${appConfig.api.baseUrl}/healthz`,
        expect.objectContaining({
          headers: expect.objectContaining({
            'Content-Type': 'application/json',
          }),
        })
      );
      expect(result).toEqual(healthResponse);
    });
  });
});