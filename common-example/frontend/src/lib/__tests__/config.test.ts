import { appConfig } from '../config';

describe('appConfig', () => {
  it('verwendet den lokalen Backend-Port', () => {
    expect(appConfig.api.baseUrl).toBe('http://localhost:8081');
    expect(appConfig.api.timeout).toBe(30000);
  });
});
