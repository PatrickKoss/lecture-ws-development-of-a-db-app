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
  const [apiError, setApiError] = useState<string | null>(null);

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
    setApiError(null);
  }, [student]);

  const validateForm = () => {
    const errors: Record<string, string> = {};

    // Regex pattern for allowed characters: letters, spaces, hyphens, apostrophes
    const namePattern = /^[a-zA-ZÀ-ÿ\s'-]*$/;

    // Name validation: required, 0-200 characters, valid characters
    if (formData.name.trim().length === 0) {
      errors.name = 'First name is required';
    } else if (formData.name.length > 200) {
      errors.name = 'First name must be 200 characters or less';
    } else if (!namePattern.test(formData.name)) {
      errors.name = 'First name contains invalid characters. Only letters, spaces, hyphens and apostrophes are allowed';
    }

    // Last name validation: required, minLength 1, valid characters
    if (formData.lastName.trim().length === 0) {
      errors.lastName = 'Last name is required';
    } else if (!namePattern.test(formData.lastName)) {
      errors.lastName = 'Last name contains invalid characters. Only letters, spaces, hyphens and apostrophes are allowed';
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
    setApiError(null);
    
    try {
      await onSave({
        name: formData.name.trim(),
        lastName: formData.lastName.trim(),
      });
      // Only close the dialog if the save was successful
      onClose();
    } catch (error: any) {
      // Display error in the dialog instead of propagating to parent
      const errorMessage = error?.message || 'Failed to update student. Please try again.';
      setApiError(errorMessage);
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
    // Clear API error when user starts typing
    if (apiError) {
      setApiError(null);
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-[425px] bg-gray-800 border-gray-700">
        <DialogHeader>
          <DialogTitle className="text-white">Edit Student</DialogTitle>
          <DialogDescription className="text-gray-400">
            Make changes to the student information. Click save when you&apos;re done.
          </DialogDescription>
        </DialogHeader>
        
        <form onSubmit={handleSubmit}>
          {apiError && (
            <div className="text-sm text-red-400 bg-red-900/20 p-3 rounded-md mb-4 border border-red-800">
              {apiError}
            </div>
          )}
          
          <div className="grid gap-4 py-4">
            <div className="space-y-2">
              <label htmlFor="name" className="text-sm font-medium text-gray-200">
                First Name
              </label>
              <Input
                id="name"
                value={formData.name}
                onChange={(e) => handleInputChange('name', e.target.value)}
                disabled={isLoading}
                maxLength={200}
                placeholder="Enter first name"
                className="bg-gray-700 border-gray-600 text-white placeholder:text-gray-400"
              />
              {validationErrors.name && (
                <div className="text-sm text-red-400">{validationErrors.name}</div>
              )}
            </div>
            
            <div className="space-y-2">
              <label htmlFor="lastName" className="text-sm font-medium text-gray-200">
                Last Name
              </label>
              <Input
                id="lastName"
                value={formData.lastName}
                onChange={(e) => handleInputChange('lastName', e.target.value)}
                disabled={isLoading}
                placeholder="Enter last name"
                className="bg-gray-700 border-gray-600 text-white placeholder:text-gray-400"
              />
              {validationErrors.lastName && (
                <div className="text-sm text-red-400">{validationErrors.lastName}</div>
              )}
            </div>
          </div>
          
          <DialogFooter>
            <Button 
              type="button" 
              variant="outline" 
              onClick={onClose} 
              disabled={isLoading}
              className="border-gray-600 text-gray-300 hover:bg-gray-700"
            >
              Cancel
            </Button>
            <Button type="submit" disabled={isLoading} className="bg-blue-600 hover:bg-blue-700">
              {isLoading ? 'Saving...' : 'Save changes'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}