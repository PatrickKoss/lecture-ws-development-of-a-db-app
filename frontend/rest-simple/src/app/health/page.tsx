'use client';

import { useEffect, useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { apiClient } from '@/lib/api-client';
import { appConfig } from '@/lib/config';
import { Activity, RefreshCw, CheckCircle, XCircle, Clock } from 'lucide-react';

interface HealthCheck {
  status: 'loading' | 'success' | 'error';
  timestamp: Date;
  responseTime?: number;
  error?: string;
  data?: any;
}

export default function HealthPage() {
  const [healthCheck, setHealthCheck] = useState<HealthCheck>({
    status: 'loading',
    timestamp: new Date(),
  });

  const performHealthCheck = async () => {
    const startTime = Date.now();
    setHealthCheck({
      status: 'loading',
      timestamp: new Date(),
    });

    try {
      const response = await apiClient.healthCheck();
      const responseTime = Date.now() - startTime;
      
      setHealthCheck({
        status: 'success',
        timestamp: new Date(),
        responseTime,
        data: response,
      });
    } catch (error: any) {
      const responseTime = Date.now() - startTime;
      
      setHealthCheck({
        status: 'error',
        timestamp: new Date(),
        responseTime,
        error: error.message || 'Health check failed',
      });
    }
  };

  useEffect(() => {
    performHealthCheck();
  }, []);

  const getStatusIcon = () => {
    switch (healthCheck.status) {
      case 'loading':
        return <Clock className="h-6 w-6 text-yellow-500" />;
      case 'success':
        return <CheckCircle className="h-6 w-6 text-green-500" />;
      case 'error':
        return <XCircle className="h-6 w-6 text-red-500" />;
    }
  };

  const getStatusText = () => {
    switch (healthCheck.status) {
      case 'loading':
        return 'Checking...';
      case 'success':
        return 'Healthy';
      case 'error':
        return 'Unhealthy';
    }
  };

  const getStatusColor = () => {
    switch (healthCheck.status) {
      case 'loading':
        return 'text-yellow-600';
      case 'success':
        return 'text-green-600';
      case 'error':
        return 'text-red-600';
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold tracking-tight">API Health Check</h1>
          <p className="text-muted-foreground">
            Monitor the status and performance of your REST API
          </p>
        </div>
        <Button
          onClick={performHealthCheck}
          disabled={healthCheck.status === 'loading'}
        >
          <RefreshCw className={`h-4 w-4 mr-2 ${healthCheck.status === 'loading' ? 'animate-spin' : ''}`} />
          Check Health
        </Button>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Activity className="h-5 w-5" />
              Current Status
            </CardTitle>
            <CardDescription>
              Real-time health status of the API endpoint
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex items-center gap-4">
              {getStatusIcon()}
              <div>
                <div className={`text-2xl font-bold ${getStatusColor()}`}>
                  {getStatusText()}
                </div>
                <div className="text-sm text-muted-foreground">
                  Last checked: {healthCheck.timestamp.toLocaleTimeString()}
                </div>
              </div>
            </div>

            {healthCheck.responseTime && (
              <div className="mt-4 pt-4 border-t">
                <div className="text-sm font-medium">Response Time</div>
                <div className="text-2xl font-bold">
                  {healthCheck.responseTime}ms
                </div>
              </div>
            )}

            {healthCheck.error && (
              <div className="mt-4 p-3 bg-red-50 border border-red-200 rounded-md">
                <div className="text-sm font-medium text-red-800">Error Details</div>
                <div className="text-sm text-red-700 mt-1">
                  {healthCheck.error}
                </div>
              </div>
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Configuration</CardTitle>
            <CardDescription>
              Current API configuration settings
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Base URL:</span>
              <span className="text-sm text-muted-foreground font-mono">
                {appConfig.api.baseUrl}
              </span>
            </div>
            
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Timeout:</span>
              <span className="text-sm text-muted-foreground">
                {appConfig.api.timeout / 1000}s
              </span>
            </div>
            
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Health Endpoint:</span>
              <span className="text-sm text-muted-foreground font-mono">
                /healthz
              </span>
            </div>

            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Authentication:</span>
              <span className={`text-sm ${appConfig.auth.enabled ? 'text-green-600' : 'text-yellow-600'}`}>
                {appConfig.auth.enabled ? 'Enabled' : 'Disabled'}
              </span>
            </div>
          </CardContent>
        </Card>
      </div>

      {healthCheck.data && (
        <Card>
          <CardHeader>
            <CardTitle>Response Data</CardTitle>
            <CardDescription>
              Raw response from the health endpoint
            </CardDescription>
          </CardHeader>
          <CardContent>
            <pre className="bg-muted p-4 rounded-md text-sm overflow-auto">
              {JSON.stringify(healthCheck.data, null, 2)}
            </pre>
          </CardContent>
        </Card>
      )}
    </div>
  );
}