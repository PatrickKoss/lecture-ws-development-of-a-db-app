'use client';

import { useAuth } from '@/hooks/use-auth';
import { appConfig } from '@/lib/config';
import { Button } from '@/components/ui/button';
import { LogOut, User, Settings } from 'lucide-react';

export function Header() {
  const { user, logout, isAuthenticated } = useAuth();

  return (
    <header className="border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="container flex h-16 items-center justify-between">
        <div className="flex items-center gap-6">
          <div className="flex items-center gap-2">
            <div className="h-8 w-8 rounded-lg bg-primary flex items-center justify-center">
              <span className="text-primary-foreground font-bold text-sm">RS</span>
            </div>
            <span className="font-semibold text-lg">REST Simple</span>
          </div>
        </div>

        <div className="flex items-center gap-4">
          {appConfig.auth.enabled && isAuthenticated && user && (
            <>
              <div className="flex items-center gap-2 text-sm text-muted-foreground">
                <User className="h-4 w-4" />
                <span>{user.firstName} {user.lastName}</span>
                <span className="text-xs">({user.username})</span>
              </div>
              
              <Button
                variant="outline"
                size="sm"
                onClick={logout}
              >
                <LogOut className="h-4 w-4 mr-2" />
                Logout
              </Button>
            </>
          )}

          {!appConfig.auth.enabled && (
            <div className="flex items-center gap-2 text-sm text-muted-foreground">
              <Settings className="h-4 w-4" />
              <span>Auth Disabled</span>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}