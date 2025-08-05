'use client';

import { useState, useEffect } from 'react';
import { StudentResponse } from '@/types/api';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';

interface EditStudentDialogProps {
  student: StudentResponse | null;
  isOpen: boolean;
  onClose: () => void;
  onSave: (student: { name: string; lastName: string }) => Promise<void>;
}

export function EditStudentDialog({ student, isOpen, onClose, onSave }: EditStudentDialogProps) {
  const [formData, setFormData] = useState({
    name: '',
    lastName: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const [validationErrors, setValidationErrors] = useState<Record<string, string>>({});

  useEffect(() => {
    if (student) {
      setFormData({
        name: student.name,
        lastName: student.lastName,
      });
    } else {
      setFormData({
        name: '',
        lastName: '',
      });
    }
    setValidationErrors({});
  }, [student]);

  const validateForm = () => {
    const errors: Record<string, string> = {};

    if (formData.name.trim().length === 0) {
      errors.name = 'First name is required';
    } else if (formData.name.length > 200) {
      errors.name = 'First name must be 200 characters or less';
    }

    if (formData.lastName.trim().length === 0) {
      errors.lastName = 'Last name is required';
    }

    setValidationErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) {
      return;
    }

    setIsLoading(true);
    try {
      await onSave({
        name: formData.name.trim(),
        lastName: formData.lastName.trim(),
      });
    } catch (error) {
      // Error handled by parent component
    } finally {
      setIsLoading(false);
    }
  };

  const handleInputChange = (field: string, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    // Clear validation error when user starts typing
    if (validationErrors[field]) {
      setValidationErrors(prev => ({ ...prev, [field]: '' }));
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>Edit Student</DialogTitle>
          <DialogDescription>
            Make changes to the student information. Click save when you're done.
          </DialogDescription>
        </DialogHeader>
        
        <form onSubmit={handleSubmit}>
          <div className="grid gap-4 py-4">
            <div className="space-y-2">
              <label htmlFor="name" className="text-sm font-medium">
                First Name
              </label>
              <Input
                id="name"
                value={formData.name}
                onChange={(e) => handleInputChange('name', e.target.value)}
                disabled={isLoading}
                maxLength={200}
              />
              {validationErrors.name && (
                <div className="text-sm text-destructive">{validationErrors.name}</div>
              )}
            </div>
            
            <div className="space-y-2">
              <label htmlFor="lastName" className="text-sm font-medium">
                Last Name
              </label>
              <Input
                id="lastName"
                value={formData.lastName}
                onChange={(e) => handleInputChange('lastName', e.target.value)}
                disabled={isLoading}
              />
              {validationErrors.lastName && (
                <div className="text-sm text-destructive">{validationErrors.lastName}</div>
              )}
            </div>
          </div>
          
          <DialogFooter>
            <Button type="button" variant="outline" onClick={onClose} disabled={isLoading}>
              Cancel
            </Button>
            <Button type="submit" disabled={isLoading}>
              {isLoading ? 'Saving...' : 'Save changes'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}