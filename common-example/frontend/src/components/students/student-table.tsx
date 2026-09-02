import { StudentResponse } from '@/types/api';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';

export function StudentTable({ students, isLoading = false }: { students: StudentResponse[]; isLoading?: boolean }) {
  if (isLoading) return <p className="text-gray-400">Lädt...</p>;
  if (students.length === 0) return <p className="text-gray-400">Noch keine Studierenden vorhanden.</p>;

  return (
    <div className="rounded-lg border border-gray-700">
      <Table>
        <TableHeader>
          <TableRow className="border-gray-700">
            <TableHead>Matrikelnummer</TableHead>
            <TableHead>Name</TableHead>
            <TableHead>E-Mail</TableHead>
            <TableHead>Einschreibedatum</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {students.map((student) => (
            <TableRow key={student.id} className="border-gray-700">
              <TableCell className="font-mono">{student.studentNumber}</TableCell>
              <TableCell>{student.firstName} {student.lastName}</TableCell>
              <TableCell>{student.email}</TableCell>
              <TableCell>{student.enrollmentDate}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}
