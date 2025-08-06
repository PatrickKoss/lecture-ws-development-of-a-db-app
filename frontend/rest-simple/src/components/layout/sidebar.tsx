'use client';

import { useState } from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import { 
  Users, 
  Home, 
  Settings, 
  ChevronLeft, 
  ChevronRight,
  UserPlus,
  Activity
} from 'lucide-react';
import { useAuth } from '@/hooks/use-auth';
import { appConfig } from '@/lib/config';

const navigationItems = [
  {
    title: 'Dashboard',
    href: '/',
    icon: Home,
  },
  {
    title: 'Students',
    href: '/students',
    icon: Users,
  },
  {
    title: 'Health Check',
    href: '/health',
    icon: Activity,
  },
];

const authItems = [
  {
    title: 'Register',
    href: '/register',
    icon: UserPlus,
  },
];

export function Sidebar() {
  const [collapsed, setCollapsed] = useState(false);
  const pathname = usePathname();
  const { isAuthenticated } = useAuth();

  const showAuthItems = appConfig.auth.enabled && !isAuthenticated;

  return (
    <aside className={cn(
      "border-r bg-gray-900 border-gray-800 transition-all duration-300",
      collapsed ? "w-16" : "w-64"
    )}>
      <div className="p-4">
        <Button
          variant="ghost"
          size="sm"
          onClick={() => setCollapsed(!collapsed)}
          className="w-full justify-end text-gray-400 hover:text-white hover:bg-gray-800"
        >
          {collapsed ? <ChevronRight className="h-4 w-4" /> : <ChevronLeft className="h-4 w-4" />}
        </Button>
      </div>

      <nav className="space-y-1 p-4">
        {(appConfig.auth.enabled ? isAuthenticated : true) && navigationItems.map((item) => {
          const Icon = item.icon;
          const isActive = pathname === item.href;
          
          return (
            <Link key={item.href} href={item.href}>
              <Button
                variant="ghost"
                className={cn(
                  "w-full justify-start text-gray-300 hover:text-white hover:bg-gray-800",
                  collapsed && "px-2",
                  isActive && "bg-gray-800 text-white"
                )}
              >
                <Icon className="h-4 w-4" />
                {!collapsed && <span className="ml-2">{item.title}</span>}
              </Button>
            </Link>
          );
        })}

        {showAuthItems && (
          <>
            <div className={cn("border-t border-gray-700 pt-4 mt-4", collapsed && "mx-2")}>
              {!collapsed && (
                <p className="text-xs text-gray-500 mb-2 px-2">
                  Authentication
                </p>
              )}
            </div>
            
            {authItems.map((item) => {
              const Icon = item.icon;
              const isActive = pathname === item.href;
              
              return (
                <Link key={item.href} href={item.href}>
                  <Button
                    variant="ghost"
                    className={cn(
                      "w-full justify-start text-gray-300 hover:text-white hover:bg-gray-800",
                      collapsed && "px-2",
                      isActive && "bg-gray-800 text-white"
                    )}
                  >
                    <Icon className="h-4 w-4" />
                    {!collapsed && <span className="ml-2">{item.title}</span>}
                  </Button>
                </Link>
              );
            })}
          </>
        )}
      </nav>
    </aside>
  );
}