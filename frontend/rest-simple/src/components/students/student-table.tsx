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
      // Dialog will close itself on success, error handling is now in the dialog
    } catch (error) {
      // Error is now handled by the dialog component, not propagated
      throw error;
    }
  };

  const handleEditClose = () => {
    setEditingStudent(null);
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
          <div key={i} className="h-16 bg-gray-700/50 rounded animate-pulse" />
        ))}
      </div>
    );
  }

  if (students.length === 0) {
    return (
      <div className="text-center py-8 text-gray-400">
        No students found. Create your first student to get started.
      </div>
    );
  }

  return (
    <>
      <div className="rounded-lg border border-gray-700 bg-gray-800/50">
        <Table>
          <TableHeader>
            <TableRow className="border-gray-700 hover:bg-gray-800/50">
              <TableHead className="text-gray-300">MNR</TableHead>
              <TableHead className="text-gray-300">First Name</TableHead>
              <TableHead className="text-gray-300">Last Name</TableHead>
              <TableHead className="text-gray-300">Created On</TableHead>
              <TableHead className="text-right text-gray-300">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {students.map((student) => (
              <TableRow key={student.id} className="border-gray-700 hover:bg-gray-800/30">
                <TableCell className="font-mono text-gray-300">{student.mnr}</TableCell>
                <TableCell className="text-gray-200">{student.name}</TableCell>
                <TableCell className="text-gray-200">{student.lastName}</TableCell>
                <TableCell className="text-gray-400">{formatDate(student.createdOn)}</TableCell>
                <TableCell className="text-right">
                  <div className="flex gap-2 justify-end">
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => setEditingStudent(student)}
                      className="border-gray-600 text-gray-300 hover:bg-gray-700 hover:text-white"
                    >
                      <Edit className="h-4 w-4" />
                    </Button>
                    <Button
                      variant="outline"
                      size="sm"
                      onClick={() => handleDelete(student.id)}
                      disabled={deletingId === student.id}
                      className="border-gray-600 text-gray-300 hover:bg-red-800 hover:text-white hover:border-red-600"
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
      </div>

      <EditStudentDialog
        student={editingStudent}
        isOpen={!!editingStudent}
        onClose={() => setEditingStudent(null)}
        onSave={handleEdit}
      />
    </>
  );
}