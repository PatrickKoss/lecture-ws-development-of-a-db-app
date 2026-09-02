'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { Activity, Users } from 'lucide-react';
import { apiClient } from '@/lib/api-client';
import { appConfig } from '@/lib/config';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

export default function Dashboard() {
  const [healthy, setHealthy] = useState<boolean | null>(null);
  const [studentCount, setStudentCount] = useState<number | null>(null);

  useEffect(() => {
    apiClient.healthCheck().then(() => setHealthy(true)).catch(() => setHealthy(false));
    apiClient.getAllStudents().then((students) => setStudentCount(students.length)).catch(() => setStudentCount(null));
  }, []);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-white">Gemeinsames Hochschulbeispiel</h1>
        <p className="text-gray-400">Das Frontend verwendet den API-Vertrag aus der Vorlesung.</p>
      </div>
      <div className="grid gap-6 md:grid-cols-2">
        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader><CardTitle className="flex items-center gap-2 text-white"><Activity className="h-5 w-5" />API</CardTitle></CardHeader>
          <CardContent className="text-gray-300">
            <p>{healthy === null ? 'Wird geprüft' : healthy ? 'Erreichbar' : 'Nicht erreichbar'}</p>
            <p className="font-mono text-sm text-gray-400">{appConfig.api.baseUrl}</p>
          </CardContent>
        </Card>
        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader><CardTitle className="flex items-center gap-2 text-white"><Users className="h-5 w-5" />Studierende</CardTitle></CardHeader>
          <CardContent className="text-3xl font-bold text-white">{studentCount ?? '--'}</CardContent>
        </Card>
      </div>
      <div className="flex gap-3">
        <Link href="/students"><Button>Studierende öffnen</Button></Link>
        <Link href="/health"><Button variant="outline">Health Check öffnen</Button></Link>
      </div>
    </div>
  );
}
