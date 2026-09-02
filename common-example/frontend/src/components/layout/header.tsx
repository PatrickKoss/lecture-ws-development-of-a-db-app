'use client';

import { usePathname } from 'next/navigation';

export function Header() {
  const pathname = usePathname();
  const title = pathname === '/students' ? 'Studierende' : pathname === '/health' ? 'Health Check' : 'Hochschulverwaltung';

  return (
    <header className="border-b border-gray-800 bg-gray-900 text-white">
      <div className="flex h-16 items-center px-6">
        <h1 className="text-xl font-semibold">{title}</h1>
      </div>
    </header>
  );
}
