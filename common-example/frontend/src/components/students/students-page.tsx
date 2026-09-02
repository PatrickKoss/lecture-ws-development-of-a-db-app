'use client';

import { RefreshCw } from 'lucide-react';
import { useStudents } from '@/hooks/use-students';
import { StudentTable } from './student-table';
import { CreateStudentDialog } from './create-student-dialog';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';

export function StudentsPage() {
  const { students, isLoading, error, createStudent, refreshStudents, clearError } = useStudents();

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <p className="text-sm text-gray-400">API-Vertrag aus dem gemeinsamen Hochschulbeispiel</p>
        <div className="flex gap-2">
          <Button variant="outline" onClick={refreshStudents} disabled={isLoading}>
            <RefreshCw className={`mr-2 h-4 w-4 ${isLoading ? 'animate-spin' : ''}`} />
            Aktualisieren
          </Button>
          <CreateStudentDialog onCreate={createStudent} />
        </div>
      </div>

      {error && (
        <Card className="border-red-800 bg-red-900/20">
          <CardContent className="flex items-center justify-between pt-6">
            <p className="text-red-300">{error}</p>
            <Button variant="outline" onClick={clearError}>Schließen</Button>
          </CardContent>
        </Card>
      )}

      <Card className="border-gray-700 bg-gray-800/50">
        <CardHeader>
          <CardTitle className="text-white">Studierende</CardTitle>
          <CardDescription className="text-gray-400">{students.length} Datensätze</CardDescription>
        </CardHeader>
        <CardContent>
          <StudentTable students={students} isLoading={isLoading} />
        </CardContent>
      </Card>
    </div>
  );
}
