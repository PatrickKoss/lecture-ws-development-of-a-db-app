import { appConfig } from '../config';

describe('appConfig', () => {
  beforeEach(() => {
    // Clear environment variables
    delete process.env.NEXT_PUBLIC_API_BASE_URL;
    delete process.env.NEXT_PUBLIC_AUTH_ENABLED;
    jest.resetModules();
  });

  it('should use default API base URL when env var is not set', () => {
    expect(appConfig.api.baseUrl).toBe('http://localhost:8081');
  });

  it('should use environment API base URL when set', () => {
    process.env.NEXT_PUBLIC_API_BASE_URL = 'https://api.example.com';
    const { appConfig: configWithEnv } = require('../config');
    expect(configWithEnv.api.baseUrl).toBe('https://api.example.com');
  });

  it('should have default API timeout', () => {
    expect(appConfig.api.timeout).toBe(30000);
  });

  it('should enable auth by default', () => {
    expect(appConfig.auth.enabled).toBe(true);
  });

  it('should disable auth when NEXT_PUBLIC_AUTH_ENABLED is false', () => {
    process.env.NEXT_PUBLIC_AUTH_ENABLED = 'false';
    const { appConfig: configWithAuth } = require('../config');
    expect(configWithAuth.auth.enabled).toBe(false);
  });

  it('should have correct token storage keys', () => {
    expect(appConfig.auth.tokenStorageKey).toBe('access_token');
    expect(appConfig.auth.refreshTokenStorageKey).toBe('refresh_token');
  });
});