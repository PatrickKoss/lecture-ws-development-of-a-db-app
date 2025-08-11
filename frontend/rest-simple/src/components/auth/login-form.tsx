'use client';

import { useState } from 'react';
import { useAuth } from '@/hooks/use-auth';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';

export function LoginForm() {
  const [isRegisterMode, setIsRegisterMode] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [validationErrors, setValidationErrors] = useState<Record<string, string>>({});
  const { login, register, isLoading, error, clearError } = useAuth();

  const validateLoginForm = () => {
    const errors: Record<string, string> = {};

    if (username.trim().length === 0) {
      errors.username = 'Username is required';
    }

    if (password.trim().length === 0) {
      errors.password = 'Password is required';
    }

    setValidationErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const validateRegisterForm = () => {
    const errors: Record<string, string> = {};

    // Username validation (3-50 characters)
    if (username.trim().length === 0) {
      errors.username = 'Username is required';
    } else if (username.length < 3) {
      errors.username = 'Username must be at least 3 characters';
    } else if (username.length > 50) {
      errors.username = 'Username must be 50 characters or less';
    }

    // First name validation (0-100 characters, required)
    if (firstName.trim().length === 0) {
      errors.firstName = 'First name is required';
    } else if (firstName.length > 100) {
      errors.firstName = 'First name must be 100 characters or less';
    }

    // Last name validation (0-100 characters, required)
    if (lastName.trim().length === 0) {
      errors.lastName = 'Last name is required';
    } else if (lastName.length > 100) {
      errors.lastName = 'Last name must be 100 characters or less';
    }

    // Email validation (required, basic email format)
    if (email.trim().length === 0) {
      errors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      errors.email = 'Please enter a valid email address';
    }

    // Password validation (6+ characters)
    if (password.trim().length === 0) {
      errors.password = 'Password is required';
    } else if (password.length < 6) {
      errors.password = 'Password must be at least 6 characters';
    }

    setValidationErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    const isValid = isRegisterMode ? validateRegisterForm() : validateLoginForm();
    if (!isValid) {
      return;
    }

    try {
      if (isRegisterMode) {
        await register({ username, firstName, lastName, email, password });
        setIsRegisterMode(false);
        resetForm();
      } else {
        await login({ username, password });
      }
    } catch (error) {
      // Error is handled by the auth context
    }
  };

  const resetForm = () => {
    setUsername('');
    setPassword('');
    setFirstName('');
    setLastName('');
    setEmail('');
    setValidationErrors({});
  };

  const handleInputChange = (field: string, value: string) => {
    switch (field) {
      case 'username':
        setUsername(value);
        break;
      case 'password':
        setPassword(value);
        break;
      case 'firstName':
        setFirstName(value);
        break;
      case 'lastName':
        setLastName(value);
        break;
      case 'email':
        setEmail(value);
        break;
    }
    // Clear validation error when user starts typing
    if (validationErrors[field]) {
      setValidationErrors(prev => ({ ...prev, [field]: '' }));
    }
  };

  const toggleMode = () => {
    setIsRegisterMode(!isRegisterMode);
    resetForm();
    clearError();
  };

  return (
    <Card className="w-full max-w-md mx-auto border-gray-700 bg-gray-800/50">
      <CardHeader>
        <CardTitle className="text-white">{isRegisterMode ? 'Register' : 'Login'}</CardTitle>
        <CardDescription className="text-gray-400">
          {isRegisterMode 
            ? 'Create a new account to access the application'
            : 'Enter your credentials to access the application'
          }
        </CardDescription>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-4">
          {error && (
            <div className="text-sm text-red-300 bg-red-900/20 p-3 rounded-md border border-red-800">
              {error}
            </div>
          )}
          
          {isRegisterMode && (
            <>
              <div className="space-y-2">
                <label htmlFor="firstName" className="text-sm font-medium text-gray-200">
                  First Name
                </label>
                <Input
                  id="firstName"
                  type="text"
                  value={firstName}
                  onChange={(e) => handleInputChange('firstName', e.target.value)}
                  disabled={isLoading}
                  maxLength={100}
                  placeholder="Enter first name"
                  className="bg-gray-700 border-gray-600 text-white placeholder-gray-400 focus:border-gray-500"
                />
                {validationErrors.firstName && (
                  <div className="text-sm text-red-400">{validationErrors.firstName}</div>
                )}
              </div>
              
              <div className="space-y-2">
                <label htmlFor="lastName" className="text-sm font-medium text-gray-200">
                  Last Name
                </label>
                <Input
                  id="lastName"
                  type="text"
                  value={lastName}
                  onChange={(e) => handleInputChange('lastName', e.target.value)}
                  disabled={isLoading}
                  maxLength={100}
                  placeholder="Enter last name"
                  className="bg-gray-700 border-gray-600 text-white placeholder-gray-400 focus:border-gray-500"
                />
                {validationErrors.lastName && (
                  <div className="text-sm text-red-400">{validationErrors.lastName}</div>
                )}
              </div>
              
              <div className="space-y-2">
                <label htmlFor="email" className="text-sm font-medium text-gray-200">
                  Email
                </label>
                <Input
                  id="email"
                  type="email"
                  value={email}
                  onChange={(e) => handleInputChange('email', e.target.value)}
                  disabled={isLoading}
                  placeholder="Enter email address"
                  className="bg-gray-700 border-gray-600 text-white placeholder-gray-400 focus:border-gray-500"
                />
                {validationErrors.email && (
                  <div className="text-sm text-red-400">{validationErrors.email}</div>
                )}
              </div>
            </>
          )}
          
          <div className="space-y-2">
            <label htmlFor="username" className="text-sm font-medium text-gray-200">
              Username
            </label>
            <Input
              id="username"
              type="text"
              value={username}
              onChange={(e) => handleInputChange('username', e.target.value)}
              disabled={isLoading}
              maxLength={50}
              placeholder="Enter username"
              className="bg-gray-700 border-gray-600 text-white placeholder-gray-400 focus:border-gray-500"
            />
            {validationErrors.username && (
              <div className="text-sm text-red-400">{validationErrors.username}</div>
            )}
          </div>
          
          <div className="space-y-2">
            <label htmlFor="password" className="text-sm font-medium text-gray-200">
              Password
            </label>
            <Input
              id="password"
              type="password"
              value={password}
              onChange={(e) => handleInputChange('password', e.target.value)}
              disabled={isLoading}
              placeholder="Enter password"
              className="bg-gray-700 border-gray-600 text-white placeholder-gray-400 focus:border-gray-500"
            />
            {validationErrors.password && (
              <div className="text-sm text-red-400">{validationErrors.password}</div>
            )}
          </div>
          
          <Button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 text-white" disabled={isLoading}>
            {isLoading 
              ? (isRegisterMode ? 'Registering...' : 'Logging in...') 
              : (isRegisterMode ? 'Register' : 'Login')
            }
          </Button>
          
          <div className="text-center">
            <Button 
              type="button" 
              variant="link" 
              onClick={toggleMode}
              disabled={isLoading}
              className="text-sm text-gray-400 hover:text-white"
            >
              {isRegisterMode 
                ? 'Already have an account? Sign in'
                : 'Need an account? Sign up'
              }
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
}