import { apiClient } from '../api-client';
import { appConfig } from '../config';

const fetchMock = jest.fn();
global.fetch = fetchMock;

describe('ApiClient', () => {
  beforeEach(() => fetchMock.mockReset());

  it('liest das Student-Array', async () => {
    fetchMock.mockResolvedValue({ ok: true, status: 200, json: async () => [] });
    await apiClient.getAllStudents();
    expect(fetchMock).toHaveBeenCalledWith(
      `${appConfig.api.baseUrl}/api/students`,
      expect.objectContaining({ headers: { 'Content-Type': 'application/json' } }),
    );
  });

  it('sendet POST an den Kursendpunkt', async () => {
    fetchMock.mockResolvedValue({ ok: true, status: 201, json: async () => ({ id: 1 }) });
    const request = {
      firstName: 'Ada',
      lastName: 'Lovelace',
      email: 'ada@stud.example',
      studentNumber: 'M2026001',
    };
    await apiClient.createStudent(request);
    expect(fetchMock).toHaveBeenCalledWith(
      `${appConfig.api.baseUrl}/api/students`,
      expect.objectContaining({ method: 'POST', body: JSON.stringify(request) }),
    );
  });

  it('prüft den vorbereiteten Health-Endpunkt', async () => {
    fetchMock.mockResolvedValue({ ok: true, status: 200, json: async () => ({ status: 'UP' }) });
    await apiClient.healthCheck();
    expect(fetchMock).toHaveBeenCalledWith(
      `${appConfig.api.baseUrl}/api/students/health`,
      expect.any(Object),
    );
  });
});
