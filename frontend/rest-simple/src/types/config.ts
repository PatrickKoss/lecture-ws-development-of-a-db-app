export interface AppConfig {
  api: {
    baseUrl: string;
    timeout: number;
  };
  auth: {
    enabled: boolean;
    tokenStorageKey: string;
    refreshTokenStorageKey: string;
  };
}

export interface AuthUser {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
}