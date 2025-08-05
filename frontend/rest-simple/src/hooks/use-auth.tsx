'use client';

import { createContext, useContext, useEffect, useState, ReactNode } from 'react';
import { apiClient } from '@/lib/api-client';
import { appConfig } from '@/lib/config';
import { AuthUser } from '@/types/config';
import { LoginRequest, RegisterRequest } from '@/types/api';
import { ApiError } from '@/types/api';

interface AuthContextType {
  user: AuthUser | null;
  isLoading: boolean;
  isAuthenticated: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  register: (userData: RegisterRequest) => Promise<void>;
  logout: () => void;
  error: string | null;
  clearError: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const isAuthenticated = appConfig.auth.enabled ? !!user : true;

  useEffect(() => {
    if (!appConfig.auth.enabled) {
      setIsLoading(false);
      return;
    }

    const token = localStorage.getItem(appConfig.auth.tokenStorageKey);
    if (token) {
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        if (payload.exp * 1000 > Date.now()) {
          setUser({
            username: payload.sub || payload.username || 'user',
            firstName: payload.firstName || '',
            lastName: payload.lastName || '',
            email: payload.email || '',
          });
        } else {
          localStorage.removeItem(appConfig.auth.tokenStorageKey);
          localStorage.removeItem(appConfig.auth.refreshTokenStorageKey);
        }
      } catch (error) {
        console.error('Failed to parse token:', error);
        localStorage.removeItem(appConfig.auth.tokenStorageKey);
        localStorage.removeItem(appConfig.auth.refreshTokenStorageKey);
      }
    }
    setIsLoading(false);
  }, []);

  const login = async (credentials: LoginRequest) => {
    try {
      setError(null);
      setIsLoading(true);
      
      const response = await apiClient.login(credentials);
      
      const token = response.access_token;
      const payload = JSON.parse(atob(token.split('.')[1]));
      
      setUser({
        username: payload.sub || payload.username || credentials.username,
        firstName: payload.firstName || '',
        lastName: payload.lastName || '',
        email: payload.email || '',
      });
    } catch (error) {
      const apiError = error as ApiError;
      setError(apiError.message || 'Login failed');
      throw error;
    } finally {
      setIsLoading(false);
    }
  };

  const register = async (userData: RegisterRequest) => {
    try {
      setError(null);
      setIsLoading(true);
      await apiClient.register(userData);
    } catch (error) {
      const apiError = error as ApiError;
      setError(apiError.message || 'Registration failed');
      throw error;
    } finally {
      setIsLoading(false);
    }
  };

  const logout = () => {
    apiClient.logout();
    setUser(null);
    setError(null);
  };

  const clearError = () => {
    setError(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        isLoading,
        isAuthenticated,
        login,
        register,
        logout,
        error,
        clearError,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}