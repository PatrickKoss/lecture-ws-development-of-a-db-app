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
          <p className="text-gray-400 text-sm">
            Monitor the status and performance of your REST API
          </p>
        </div>
        <Button
          onClick={performHealthCheck}
          disabled={healthCheck.status === 'loading'}
          className="border-gray-700 bg-gray-800 text-gray-200 hover:bg-gray-700 hover:text-white"
        >
          <RefreshCw className={`h-4 w-4 mr-2 ${healthCheck.status === 'loading' ? 'animate-spin' : ''}`} />
          Check Health
        </Button>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader>
            <CardTitle className="flex items-center gap-2 text-white">
              <Activity className="h-5 w-5" />
              Current Status
            </CardTitle>
            <CardDescription className="text-gray-400">
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
                <div className="text-sm text-gray-400">
                  Last checked: {healthCheck.timestamp.toLocaleTimeString()}
                </div>
              </div>
            </div>

            {healthCheck.responseTime && (
              <div className="mt-4 pt-4 border-t border-gray-700">
                <div className="text-sm font-medium text-gray-200">Response Time</div>
                <div className="text-2xl font-bold text-white">
                  {healthCheck.responseTime}ms
                </div>
              </div>
            )}

            {healthCheck.error && (
              <div className="mt-4 p-3 bg-red-900/20 border border-red-800 rounded-md">
                <div className="text-sm font-medium text-red-400">Error Details</div>
                <div className="text-sm text-red-300 mt-1">
                  {healthCheck.error}
                </div>
              </div>
            )}
          </CardContent>
        </Card>

        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader>
            <CardTitle className="text-white">Configuration</CardTitle>
            <CardDescription className="text-gray-400">
              Current API configuration settings
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium text-gray-200">Base URL:</span>
              <span className="text-sm text-gray-400 font-mono">
                {appConfig.api.baseUrl}
              </span>
            </div>
            
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium text-gray-200">Timeout:</span>
              <span className="text-sm text-gray-400">
                {appConfig.api.timeout / 1000}s
              </span>
            </div>
            
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium text-gray-200">Health Endpoint:</span>
              <span className="text-sm text-gray-400 font-mono">
                /healthz
              </span>
            </div>

            <div className="flex justify-between items-center">
              <span className="text-sm font-medium text-gray-200">Authentication:</span>
              <span className={`text-sm ${appConfig.auth.enabled ? 'text-green-400' : 'text-yellow-400'}`}>
                {appConfig.auth.enabled ? 'Enabled' : 'Disabled'}
              </span>
            </div>
          </CardContent>
        </Card>
      </div>

      {healthCheck.data && (
        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader>
            <CardTitle className="text-white">Response Data</CardTitle>
            <CardDescription className="text-gray-400">
              Raw response from the health endpoint
            </CardDescription>
          </CardHeader>
          <CardContent>
            <pre className="bg-gray-900 p-4 rounded-md text-sm overflow-auto text-gray-200 border border-gray-700">
              {JSON.stringify(healthCheck.data, null, 2)}
            </pre>
          </CardContent>
        </Card>
      )}
    </div>
  );
}