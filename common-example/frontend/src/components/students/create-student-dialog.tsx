'use client';

import { FormEvent, useState } from 'react';
import { Plus } from 'lucide-react';
import { CreateStudentRequest } from '@/types/api';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';

interface CreateStudentDialogProps {
  onCreate: (student: CreateStudentRequest) => Promise<unknown>;
}

const EMPTY_FORM: CreateStudentRequest = {
  firstName: '',
  lastName: '',
  email: '',
  studentNumber: '',
};

export function CreateStudentDialog({ onCreate }: CreateStudentDialogProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [form, setForm] = useState(EMPTY_FORM);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const update = (field: keyof CreateStudentRequest, value: string) => {
    setForm((current) => ({ ...current, [field]: value }));
    setError(null);
  };

  const submit = async (event: FormEvent) => {
    event.preventDefault();
    if (Object.values(form).some((value) => !value.trim())) {
      setError('Alle Felder sind erforderlich');
      return;
    }

    setIsLoading(true);
    try {
      await onCreate({
        firstName: form.firstName.trim(),
        lastName: form.lastName.trim(),
        email: form.email.trim(),
        studentNumber: form.studentNumber.trim(),
      });
      setForm(EMPTY_FORM);
      setIsOpen(false);
    } catch (caught) {
      setError(caught instanceof Error ? caught.message : 'Anlegen fehlgeschlagen');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={setIsOpen}>
      <DialogTrigger asChild>
        <Button className="bg-blue-600 text-white hover:bg-blue-700">
          <Plus className="mr-2 h-4 w-4" />
          Student anlegen
        </Button>
      </DialogTrigger>
      <DialogContent className="border-gray-700 bg-gray-800 sm:max-w-[520px]">
        <DialogHeader>
          <DialogTitle className="text-white">Student anlegen</DialogTitle>
          <DialogDescription className="text-gray-400">
            Dieser Request wird an POST /api/students gesendet.
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={submit}>
          {error && <p className="mb-3 text-sm text-red-400">{error}</p>}
          <div className="grid gap-4 py-4">
            <InputField label="Vorname" value={form.firstName} onChange={(value) => update('firstName', value)} />
            <InputField label="Nachname" value={form.lastName} onChange={(value) => update('lastName', value)} />
            <InputField label="E-Mail" type="email" value={form.email} onChange={(value) => update('email', value)} />
            <InputField label="Matrikelnummer" placeholder="M2026001" value={form.studentNumber} onChange={(value) => update('studentNumber', value)} />
          </div>
          <DialogFooter>
            <Button type="button" variant="outline" onClick={() => setIsOpen(false)}>
              Abbrechen
            </Button>
            <Button type="submit" disabled={isLoading}>
              {isLoading ? 'Speichert...' : 'Speichern'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}

function InputField({
  label,
  value,
  onChange,
  type = 'text',
  placeholder,
}: {
  label: string;
  value: string;
  onChange: (value: string) => void;
  type?: string;
  placeholder?: string;
}) {
  const id = label.toLowerCase().replace(/[^a-z0-9]+/g, '-');
  return (
    <div className="space-y-2">
      <label htmlFor={id} className="text-sm font-medium text-gray-200">
        {label}
      </label>
      <Input
        id={id}
        type={type}
        value={value}
        placeholder={placeholder}
        onChange={(event) => onChange(event.target.value)}
        className="border-gray-600 bg-gray-700 text-white"
      />
    </div>
  );
}
