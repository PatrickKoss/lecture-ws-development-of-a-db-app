'use client';

import { useAuth } from '@/hooks/use-auth';
import { appConfig } from '@/lib/config';
import { Button } from '@/components/ui/button';
import { LogOut, User, Settings, Bell, Search } from 'lucide-react';
import { usePathname } from 'next/navigation';

export function Header() {
  const { user, logout, isAuthenticated } = useAuth();
  const pathname = usePathname();
  
  // Get page title based on pathname
  const getPageTitle = () => {
    if (pathname === '/') return 'Dashboard';
    if (pathname === '/students') return 'Students';
    if (pathname === '/health') return 'Health Check';
    if (pathname === '/register') return 'Register';
    if (pathname === '/login') return 'Login';
    return 'Page';
  };

  return (
    <header className="border-b bg-gray-900 text-white border-gray-800">
      <div className="flex h-16 items-center justify-between px-6">
        {/* Left side - Page title */}
        <div className="flex items-center gap-4">
          <h1 className="text-xl font-semibold text-white">
            {getPageTitle()}
          </h1>
        </div>

        {/* Right side - Profile, notifications, and actions */}
        <div className="flex items-center gap-3">
          {/* Search icon */}
          <Button variant="ghost" size="sm" className="h-9 w-9 p-0 text-gray-400 hover:text-white hover:bg-gray-800">
            <Search className="h-4 w-4" />
          </Button>

          {/* Notifications */}
          <Button variant="ghost" size="sm" className="h-9 w-9 p-0 text-gray-400 hover:text-white hover:bg-gray-800">
            <Bell className="h-4 w-4" />
          </Button>

          {appConfig.auth.enabled && isAuthenticated && user && (
            <>
              {/* User profile */}
              <div className="flex items-center gap-3 pl-3 border-l border-gray-700">
                <div className="flex items-center gap-2">
                  <div className="h-8 w-8 rounded-full bg-gray-700 flex items-center justify-center">
                    <User className="h-4 w-4 text-gray-300" />
                  </div>
                  <div className="flex flex-col">
                    <span className="text-sm font-medium text-white">
                      {user.firstName} {user.lastName}
                    </span>
                    <span className="text-xs text-gray-400">
                      {user.username}
                    </span>
                  </div>
                </div>
                
                <Button
                  variant="ghost"
                  size="sm"
                  onClick={logout}
                  className="text-gray-400 hover:text-white hover:bg-gray-800"
                >
                  <LogOut className="h-4 w-4" />
                </Button>
              </div>
            </>
          )}

          {!appConfig.auth.enabled && (
            <div className="flex items-center gap-2 text-sm text-gray-400 pl-3 border-l border-gray-700">
              <Settings className="h-4 w-4" />
              <span>Auth Disabled</span>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}