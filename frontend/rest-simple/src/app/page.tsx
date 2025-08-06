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
        <h1 className="text-3xl font-bold tracking-tight text-white">Dashboard</h1>
        <p className="text-gray-400">
          Overview of your REST Simple application
        </p>
      </div>

      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium text-white">API Status</CardTitle>
            <Activity className="h-4 w-4 text-gray-400" />
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
              <span className="text-2xl font-bold text-white">
                {healthStatus === 'loading' 
                  ? 'Checking...' 
                  : healthStatus === 'healthy' 
                  ? 'Healthy' 
                  : 'Error'
                }
              </span>
            </div>
            <p className="text-xs text-gray-400">
              {appConfig.api.baseUrl}
            </p>
          </CardContent>
        </Card>

        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium text-white">Total Students</CardTitle>
            <Users className="h-4 w-4 text-gray-400" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-white">
              {studentCount !== null ? studentCount : '--'}
            </div>
            <p className="text-xs text-gray-400">
              Students in the system
            </p>
          </CardContent>
        </Card>

        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium text-white">Authentication</CardTitle>
            <Settings className="h-4 w-4 text-gray-400" />
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold text-white">
              {appConfig.auth.enabled ? 'Enabled' : 'Disabled'}
            </div>
            <p className="text-xs text-gray-400">
              Current auth configuration
            </p>
          </CardContent>
        </Card>
      </div>

      <div className="grid gap-6 md:grid-cols-2">
        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader>
            <CardTitle className="text-white">Quick Actions</CardTitle>
            <CardDescription className="text-gray-400">
              Common tasks you can perform
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-2">
            <Link href="/students" className="block">
              <Button className="w-full justify-start bg-blue-600 hover:bg-blue-700 text-white focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 focus:ring-offset-gray-800">
                <Users className="h-4 w-4 mr-2" />
                Manage Students
              </Button>
            </Link>
            <Link href="/health" className="block">
              <Button className="w-full justify-start bg-gray-700 border border-gray-600 text-gray-300 hover:bg-gray-600 hover:text-white focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 focus:ring-offset-gray-800">
                <Activity className="h-4 w-4 mr-2" />
                Check API Health
              </Button>
            </Link>
            {appConfig.auth.enabled && (
              <Link href="/register" className="block">
                <Button className="w-full justify-start bg-gray-700 border border-gray-600 text-gray-300 hover:bg-gray-600 hover:text-white focus:ring-2 focus:ring-gray-500 focus:ring-offset-2 focus:ring-offset-gray-800">
                  <Settings className="h-4 w-4 mr-2" />
                  Register New User
                </Button>
              </Link>
            )}
          </CardContent>
        </Card>

        <Card className="border-gray-700 bg-gray-800/50">
          <CardHeader>
            <CardTitle className="text-white">System Information</CardTitle>
            <CardDescription className="text-gray-400">
              Current application configuration
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium text-gray-200">API Base URL:</span>
              <span className="text-sm text-gray-400 font-mono">
                {appConfig.api.baseUrl}
              </span>
            </div>
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium text-gray-200">Request Timeout:</span>
              <span className="text-sm text-gray-400">
                {appConfig.api.timeout / 1000}s
              </span>
            </div>
            <div className="flex justify-between items-center">
              <span className="text-sm font-medium text-gray-200">Authentication:</span>
              <span className={`text-sm ${appConfig.auth.enabled ? 'text-green-400' : 'text-yellow-400'}`}>
                {appConfig.auth.enabled ? 'Enabled' : 'Disabled'}
              </span>
            </div>
            
            {!appConfig.auth.enabled && (
              <div className="flex items-start space-x-2 p-3 bg-yellow-900/20 border border-yellow-800 rounded-md">
                <AlertCircle className="h-4 w-4 text-yellow-400 mt-0.5 flex-shrink-0" />
                <div className="text-sm text-yellow-300">
                  <p className="font-medium">Authentication is disabled</p>
                  <p className="text-xs mt-1 text-yellow-400">
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