'use client';

import { useStudents } from '@/hooks/use-students';
import { StudentTable } from './student-table';
import { CreateStudentDialog } from './create-student-dialog';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { RefreshCw } from 'lucide-react';

export function StudentsPage() {
  const {
    students,
    isLoading,
    error,
    createStudent,
    updateStudent,
    deleteStudent,
    refreshStudents,
    clearError,
  } = useStudents();

  const handleCreateStudent = async (student: { name: string; lastName: string }) => {
    await createStudent(student);
  };

  const handleUpdateStudent = async (id: string, student: { name: string; lastName: string }) => {
    await updateStudent(id, student);
  };

  const handleDeleteStudent = async (id: string) => {
    await deleteStudent(id);
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-gray-400 text-sm">
            Manage student records in the system
          </p>
        </div>
        <div className="flex gap-2">
          <Button
            variant="outline"
            onClick={refreshStudents}
            disabled={isLoading}
            className="border-gray-700 bg-gray-800 text-gray-200 hover:bg-gray-700 hover:text-white"
          >
            <RefreshCw className={`h-4 w-4 mr-2 ${isLoading ? 'animate-spin' : ''}`} />
            Refresh
          </Button>
          <CreateStudentDialog onCreate={handleCreateStudent} />
        </div>
      </div>

      {error && (
        <Card className="border-red-800 bg-red-900/20">
          <CardHeader>
            <CardTitle className="text-red-400">Error</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-sm text-red-300 mb-4">{error}</p>
            <Button
              variant="outline"
              size="sm"
              onClick={clearError}
              className="border-red-700 text-red-300 hover:bg-red-800"
            >
              Dismiss
            </Button>
          </CardContent>
        </Card>
      )}

      <Card className="border-gray-700 bg-gray-800/50">
        <CardHeader>
          <CardTitle className="text-white">All Students</CardTitle>
          <CardDescription className="text-gray-400">
            {students.length} student{students.length !== 1 ? 's' : ''} in the system
          </CardDescription>
        </CardHeader>
        <CardContent>
          <StudentTable
            students={students}
            onEdit={handleUpdateStudent}
            onDelete={handleDeleteStudent}
            isLoading={isLoading}
          />
        </CardContent>
      </Card>
    </div>
  );
}