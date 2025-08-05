'use client';

import { useState } from 'react';
import { StudentResponse } from '@/types/api';
import { Button } from '@/components/ui/button';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { Edit, Trash2 } from 'lucide-react';
import { EditStudentDialog } from './edit-student-dialog';

interface StudentTableProps {
  students: StudentResponse[];
  onEdit: (id: string, student: { name: string; lastName: string }) => Promise<void>;
  onDelete: (id: string) => Promise<void>;
  isLoading?: boolean;
}

export function StudentTable({ students, onEdit, onDelete, isLoading }: StudentTableProps) {
  const [editingStudent, setEditingStudent] = useState<StudentResponse | null>(null);
  const [deletingId, setDeletingId] = useState<string | null>(null);

  const handleEdit = async (student: { name: string; lastName: string }) => {
    if (!editingStudent) return;
    
    try {
      await onEdit(editingStudent.id, student);
      setEditingStudent(null);
    } catch (error) {
      // Error handled by parent component
    }
  };

  const handleDelete = async (id: string) => {
    setDeletingId(id);
    try {
      await onDelete(id);
    } catch (error) {
      // Error handled by parent component
    } finally {
      setDeletingId(null);
    }
  };

  const formatDate = (dateString: string) => {
    try {
      return new Date(dateString).toLocaleDateString();
    } catch {
      return dateString;
    }
  };

  if (isLoading) {
    return (
      <div className="space-y-4">
        {[...Array(3)].map((_, i) => (
          <div key={i} className="h-16 bg-muted/50 rounded animate-pulse" />
        ))}
      </div>
    );
  }

  if (students.length === 0) {
    return (
      <div className="text-center py-8 text-muted-foreground">
        No students found. Create your first student to get started.
      </div>
    );
  }

  return (
    <>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>MNR</TableHead>
            <TableHead>First Name</TableHead>
            <TableHead>Last Name</TableHead>
            <TableHead>Created On</TableHead>
            <TableHead className="text-right">Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {students.map((student) => (
            <TableRow key={student.id}>
              <TableCell className="font-mono">{student.mnr}</TableCell>
              <TableCell>{student.name}</TableCell>
              <TableCell>{student.lastName}</TableCell>
              <TableCell>{formatDate(student.createdOn)}</TableCell>
              <TableCell className="text-right">
                <div className="flex gap-2 justify-end">
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => setEditingStudent(student)}
                  >
                    <Edit className="h-4 w-4" />
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => handleDelete(student.id)}
                    disabled={deletingId === student.id}
                  >
                    {deletingId === student.id ? (
                      <div className="h-4 w-4 border-2 border-current border-t-transparent rounded-full animate-spin" />
                    ) : (
                      <Trash2 className="h-4 w-4" />
                    )}
                  </Button>
                </div>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <EditStudentDialog
        student={editingStudent}
        isOpen={!!editingStudent}
        onClose={() => setEditingStudent(null)}
        onSave={handleEdit}
      />
    </>
  );
}