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
          <h1 className="text-3xl font-bold tracking-tight">Students</h1>
          <p className="text-muted-foreground">
            Manage student records in the system
          </p>
        </div>
        <div className="flex gap-2">
          <Button
            variant="outline"
            onClick={refreshStudents}
            disabled={isLoading}
          >
            <RefreshCw className={`h-4 w-4 mr-2 ${isLoading ? 'animate-spin' : ''}`} />
            Refresh
          </Button>
          <CreateStudentDialog onCreate={handleCreateStudent} />
        </div>
      </div>

      {error && (
        <Card className="border-destructive/50 bg-destructive/10">
          <CardHeader>
            <CardTitle className="text-destructive">Error</CardTitle>
          </CardHeader>
          <CardContent>
            <p className="text-sm text-destructive mb-4">{error}</p>
            <Button
              variant="outline"
              size="sm"
              onClick={clearError}
            >
              Dismiss
            </Button>
          </CardContent>
        </Card>
      )}

      <Card>
        <CardHeader>
          <CardTitle>All Students</CardTitle>
          <CardDescription>
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