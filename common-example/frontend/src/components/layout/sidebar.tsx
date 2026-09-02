'use client';

import Link from 'next/link';
import { Activity, Home, Users } from 'lucide-react';
import { usePathname } from 'next/navigation';
import { cn } from '@/lib/utils';

const items = [
  { title: 'Übersicht', href: '/', icon: Home },
  { title: 'Studierende', href: '/students', icon: Users },
  { title: 'Health Check', href: '/health', icon: Activity },
];

export function Sidebar() {
  const pathname = usePathname();
  return (
    <aside className="w-64 border-r border-gray-800 bg-gray-900 p-4">
      <nav className="space-y-1">
        {items.map((item) => {
          const Icon = item.icon;
          return (
            <Link
              key={item.href}
              href={item.href}
              className={cn(
                'flex items-center gap-2 rounded px-3 py-2 text-gray-300 hover:bg-gray-800',
                pathname === item.href && 'bg-gray-800 text-white',
              )}
            >
              <Icon className="h-4 w-4" />
              {item.title}
            </Link>
          );
        })}
      </nav>
    </aside>
  );
}
