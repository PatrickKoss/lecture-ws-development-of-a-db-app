'use client';

import { useEffect, useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { apiClient } from '@/lib/api-client';
import { appConfig } from '@/lib/config';
import { Users, Activity, Settings, AlertCircle } from 'lucide-react';
import Link from 'next/link';

export default function Dashboard() {
  const [healthStatus, setHealthStatus] = useState<'loading' | 'healthy' | 'error'>('loading');
  const [studentCount, setStudentCount] = useState<number | null>(null);

  useEffect(() => {
    const checkHealth = async () => {
      try {
        await apiClient.healthCheck();
        setHealthStatus('healthy');
      } catch (error) {
        setHealthStatus('error');
      }
    };

    const fetchStudentCount = async () => {
      try {
        const response = await apiClient.getAllStudents();
        setStudentCount(response.students?.length || 0);
      } catch (error) {
        // Ignore error for dashboard
      }
    };

    checkHealth();
    fetchStudentCount();
  }, []);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">Dashboard</h1>
        <p className="text-muted-foreground">
          Overview of your REST Simple application
        </p>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">API Status</CardTitle>
            <Activity className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="flex items-center space-x-2">
              <div 
                className={`h-2 w-2 rounded-full ${
                  healthStatus === 'loading' 
                    ? 'bg-yellow-500' 
                    : healthStatus === 'healthy' 
                    ? 'bg-green-500' 
                    : 'bg-red-500'
                }`}
              />
              <span className="text-2xl font-bold">
                {healthStatus === 'loading' 
                  ? 'Checking...' 
                  : healthStatus === 'healthy' 
                  ? 'Healthy' 
                  : 'Error'
                }
              </span>
            </div>
            <p className="text-xs text-muted-foreground">
              {appConfig.api.baseUrl}
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Students</CardTitle>
            <Users className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">
              {studentCount !== null ? studentCount : '--'}
            </div>
            <p className="text-xs text-muted-foreground">
              Students in the system
            </p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Authentication</CardTitle>
            <Settings className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">
              {appConfig.auth.enabled ? 'Enabled' : 'Disabled'}
            </div>
            <p className="text-xs text-muted-foreground">
              Current auth configuration
            </p>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card>
          <CardHeader>
            <CardTitle>Quick Actions</CardTitle>
            <CardDescription>
              Common tasks you can perform
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-2">
            <Link href="/students">
              <Button className="w-full justify-start">
                <Users className="h-4 w-4 mr-2" />
                Manage Students
              </Button>
            </Link>
            <Link href="/health">
              <Button variant="outline" className="w-full justify-start">
                <Activity className="h-4 w-4 mr-2" />
                Check API Health
              </Button>
            </Link>
            {appConfig.auth.enabled && (
              <Link href="/register">
                <Button variant="outline" className="w-full justify-start">
                  <Settings className="h-4 w-4 mr-2" />
                  Register New User
                </Button>
              </Link>
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>System Information</CardTitle>
            <CardDescription>
              Current application configuration
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">API Base URL:</span>
              <span className="text-sm text-muted-foreground font-mono">
                {appConfig.api.baseUrl}
              </span>
            </div>
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Request Timeout:</span>
              <span className="text-sm text-muted-foreground">
                {appConfig.api.timeout / 1000}s
              </span>
            </div>
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium">Authentication:</span>
              <span className={`text-sm ${appConfig.auth.enabled ? 'text-green-600' : 'text-yellow-600'}`}>
                {appConfig.auth.enabled ? 'Enabled' : 'Disabled'}
              </span>
            </div>
            
            {!appConfig.auth.enabled && (
              <div className="flex items-start space-x-2 p-3 bg-yellow-50 border border-yellow-200 rounded-md">
                <AlertCircle className="h-4 w-4 text-yellow-600 mt-0.5 flex-shrink-0" />
                <div className="text-sm text-yellow-800">
                  <p className="font-medium">Authentication is disabled</p>
                  <p className="text-xs mt-1">
                    Set NEXT_PUBLIC_AUTH_ENABLED=true to enable authentication features.
                  </p>
                </div>
              </div>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}