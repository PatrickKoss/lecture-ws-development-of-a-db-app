import { AppConfig } from '@/types/config';

export const appConfig: AppConfig = {
  api: {
    baseUrl: process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8081',
    timeout: 30000,
  },
  auth: {
    enabled: process.env.NEXT_PUBLIC_AUTH_ENABLED !== 'false',
    tokenStorageKey: 'access_token',
    refreshTokenStorageKey: 'refresh_token',
  },
};