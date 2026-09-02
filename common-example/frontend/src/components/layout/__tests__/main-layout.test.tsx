import { render, screen } from '@testing-library/react';
import { MainLayout } from '../main-layout';

jest.mock('../header', () => ({ Header: () => <div data-testid="header" /> }));
jest.mock('../sidebar', () => ({ Sidebar: () => <div data-testid="sidebar" /> }));

describe('MainLayout', () => {
  it('ordnet Navigation und Inhalt an', () => {
    render(<MainLayout><p>Inhalt</p></MainLayout>);
    expect(screen.getByTestId('header')).toBeInTheDocument();
    expect(screen.getByTestId('sidebar')).toBeInTheDocument();
    expect(screen.getByText('Inhalt')).toBeInTheDocument();
  });
});
