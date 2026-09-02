'use client';

import { useEffect, useState } from 'react';
import { apiClient } from '@/lib/api-client';
import { appConfig } from '@/lib/config';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

export default function HealthPage() {
  const [data, setData] = useState<object | null>(null);
  const [error, setError] = useState<string | null>(null);

  const check = async () => {
    setError(null);
    try {
      setData(await apiClient.healthCheck());
    } catch (caught) {
      setData(null);
      setError(caught instanceof Error ? caught.message : 'Health Check fehlgeschlagen');
    }
  };

  useEffect(() => { check(); }, []);

  return (
    <div className="space-y-6">
      <Card className="border-gray-700 bg-gray-800/50">
        <CardHeader><CardTitle className="text-white">GET /api/students/health</CardTitle></CardHeader>
        <CardContent className="space-y-4 text-gray-300">
          <p className="font-mono text-sm">{appConfig.api.baseUrl}/api/students/health</p>
          {data && <pre className="rounded bg-gray-950 p-4">{JSON.stringify(data, null, 2)}</pre>}
          {error && <p className="text-red-400">{error}</p>}
          <Button onClick={check}>Erneut prüfen</Button>
        </CardContent>
      </Card>
    </div>
  );
}
