import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CreateStudentDialog } from '../create-student-dialog';

describe('CreateStudentDialog', () => {
  it('sendet den vereinbarten Request', async () => {
    const onCreate = jest.fn().mockResolvedValue({});
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={onCreate} />);

    await user.click(screen.getByText('Student anlegen'));
    await user.type(screen.getByLabelText('Vorname'), 'Ada');
    await user.type(screen.getByLabelText('Nachname'), 'Lovelace');
    await user.type(screen.getByLabelText('E-Mail'), 'ada@stud.example');
    await user.type(screen.getByLabelText('Matrikelnummer'), 'M2026001');
    await user.click(screen.getByText('Speichern'));

    expect(onCreate).toHaveBeenCalledWith({
      firstName: 'Ada',
      lastName: 'Lovelace',
      email: 'ada@stud.example',
      studentNumber: 'M2026001',
    });
  });

  it('verlangt alle Felder', async () => {
    const onCreate = jest.fn();
    const user = userEvent.setup();
    render(<CreateStudentDialog onCreate={onCreate} />);

    await user.click(screen.getByText('Student anlegen'));
    await user.click(screen.getByText('Speichern'));

    expect(screen.getByText('Alle Felder sind erforderlich')).toBeInTheDocument();
    expect(onCreate).not.toHaveBeenCalled();
  });
});
