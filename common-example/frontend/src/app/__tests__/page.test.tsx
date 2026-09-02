import { act, render, screen, waitFor } from '@testing-library/react';
import Dashboard from '../page';
import { apiClient } from '@/lib/api-client';

jest.mock('@/lib/api-client', () => ({
  apiClient: { healthCheck: jest.fn(), getAllStudents: jest.fn() },
}));
jest.mock('next/link', () => function MockLink({ children, href }: { children: React.ReactNode; href: string }) {
  return <a href={href}>{children}</a>;
});

const mockedApi = apiClient as jest.Mocked<typeof apiClient>;

describe('Dashboard', () => {
  it('zeigt API-Status und Anzahl', async () => {
    mockedApi.healthCheck.mockResolvedValue({ status: 'UP', service: 'university-backend' });
    mockedApi.getAllStudents.mockResolvedValue([
      { id: 1, firstName: 'Lena', lastName: 'Hoffmann', email: 'lena@stud.example', studentNumber: 'M2023001', enrollmentDate: '2023-10-01' },
    ]);
    await act(async () => { render(<Dashboard />); });
    expect(screen.getByText('Gemeinsames Hochschulbeispiel')).toBeInTheDocument();
    await waitFor(() => expect(screen.getByText('Erreichbar')).toBeInTheDocument());
    expect(screen.getByText('1')).toBeInTheDocument();
  });
});
