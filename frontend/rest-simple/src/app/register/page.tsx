'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { RegisterForm } from '@/components/auth/register-form';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { CheckCircle } from 'lucide-react';
import { Button } from '@/components/ui/button';
import Link from 'next/link';

export default function RegisterPage() {
  const [isSuccess, setIsSuccess] = useState(false);
  const router = useRouter();

  const handleSuccess = () => {
    setIsSuccess(true);
  };

  if (isSuccess) {
    return (
      <div className="max-w-md mx-auto mt-8">
        <Card>
          <CardHeader className="text-center">
            <div className="mx-auto w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mb-4">
              <CheckCircle className="h-6 w-6 text-green-600" />
            </div>
            <CardTitle className="text-green-700">Registration Successful!</CardTitle>
            <CardDescription>
              Your account has been created successfully. You can now log in with your credentials.
            </CardDescription>
          </CardHeader>
          <CardContent className="text-center space-y-4">
            <Button onClick={() => router.push('/')} className="w-full">
              Go to Dashboard
            </Button>
            <Link href="/">
              <Button variant="outline" className="w-full">
                Back to Home
              </Button>
            </Link>
          </CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="max-w-md mx-auto mt-8">
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-center">Create Account</h1>
        <p className="text-center text-muted-foreground mt-2">
          Register a new admin account to access the application
        </p>
      </div>
      
      <RegisterForm onSuccess={handleSuccess} />
      
      <div className="mt-6 text-center">
        <Link href="/" className="text-sm text-muted-foreground hover:text-primary">
          ← Back to Dashboard
        </Link>
      </div>
    </div>
  );
}