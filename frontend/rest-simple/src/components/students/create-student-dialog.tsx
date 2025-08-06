'use client';

import { useState } from 'react';
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
import { Plus } from 'lucide-react';

interface CreateStudentDialogProps {
  onCreate: (student: { name: string; lastName: string }) => Promise<void>;
}

export function CreateStudentDialog({ onCreate }: CreateStudentDialogProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    lastName: '',
  });
  const [isLoading, setIsLoading] = useState(false);
  const [validationErrors, setValidationErrors] = useState<Record<string, string>>({});

  const resetForm = () => {
    setFormData({
      name: '',
      lastName: '',
    });
    setValidationErrors({});
  };

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
      await onCreate({
        name: formData.name.trim(),
        lastName: formData.lastName.trim(),
      });
      setIsOpen(false);
      resetForm();
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

  const handleOpenChange = (open: boolean) => {
    setIsOpen(open);
    if (!open) {
      resetForm();
    }
  };

  return (
    <Dialog open={isOpen} onOpenChange={handleOpenChange}>
      <DialogTrigger asChild>
        <Button className="bg-blue-600 hover:bg-blue-700 text-white">
          <Plus className="h-4 w-4 mr-2" />
          Add Student
        </Button>
      </DialogTrigger>
      
      <DialogContent className="sm:max-w-[425px] bg-gray-800 border-gray-700">
        <DialogHeader>
          <DialogTitle className="text-white">Create New Student</DialogTitle>
          <DialogDescription className="text-gray-400">
            Add a new student to the system. Click save when you're done.
          </DialogDescription>
        </DialogHeader>
        
        <form onSubmit={handleSubmit}>
          <div className="grid gap-4 py-4">
            <div className="space-y-2">
              <label htmlFor="create-name" className="text-sm font-medium text-gray-200">
                First Name
              </label>
              <Input
                id="create-name"
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
              <label htmlFor="create-lastName" className="text-sm font-medium text-gray-200">
                Last Name
              </label>
              <Input
                id="create-lastName"
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
              onClick={() => setIsOpen(false)} 
              disabled={isLoading}
              className="border-gray-600 text-gray-300 hover:bg-gray-700"
            >
              Cancel
            </Button>
            <Button type="submit" disabled={isLoading} className="bg-blue-600 hover:bg-blue-700">
              {isLoading ? 'Creating...' : 'Create Student'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}