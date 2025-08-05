# REST Simple Frontend

A modern Next.js frontend application that visualizes and interacts with the REST Simple API for student management.

## Features

- 🎨 Modern UI built with Next.js, React, Tailwind CSS, and shadcn/ui
- 🔐 Optional authentication system with JWT tokens
- 👥 Complete student management (CRUD operations)
- 🏥 API health monitoring
- 📱 Responsive design with mobile-friendly interface
- ⚙️ Configurable authentication (can be disabled for rudimentary backends)
- 🔄 Real-time error handling and loading states
- 📊 Dashboard with system overview

## Tech Stack

- **Framework**: Next.js 15 with App Router
- **Styling**: Tailwind CSS with shadcn/ui components
- **Authentication**: JWT-based with configurable toggle
- **State Management**: React Context API with custom hooks
- **Icons**: Lucide React
- **TypeScript**: Full type safety throughout

## Quick Start

1. **Install dependencies**:

   ```bash
   npm install
   ```

2. **Configure environment** (optional):

   ```bash
   cp .env.sample .env.local
   ```

   Edit `.env.local` to customize:

   - `NEXT_PUBLIC_API_BASE_URL`: Backend API URL (default: http://localhost:8081)
   - `NEXT_PUBLIC_AUTH_ENABLED`: Enable/disable authentication (default: true)

3. **Start development server**:

   ```bash
   npm run dev
   ```

4. **Open your browser**: http://localhost:3000

## Configuration

### Authentication Toggle

The application supports a configurable authentication system:

- **Enabled** (`NEXT_PUBLIC_AUTH_ENABLED=true`): Full authentication with login/register
- **Disabled** (`NEXT_PUBLIC_AUTH_ENABLED=false`): Bypass authentication for simple backends

### API Configuration

- `NEXT_PUBLIC_API_BASE_URL`: Base URL for your REST API
- Default timeout: 30 seconds
- Automatic token refresh handling

## Pages

- **Dashboard** (`/`): System overview and quick actions
- **Students** (`/students`): Complete student management interface
- **Health Check** (`/health`): API monitoring and diagnostics
- **Register** (`/register`): User registration (when auth is enabled)
- **Login**: Integrated into the authentication guard

## Components

### Authentication Components

- `LoginForm`: User login interface
- `RegisterForm`: User registration with validation
- `AuthGuard`: Protects routes when authentication is enabled

### Student Management

- `StudentsPage`: Main student management interface
- `StudentTable`: Data table with CRUD operations
- `CreateStudentDialog`: Modal for adding new students
- `EditStudentDialog`: Modal for editing existing students

### Layout Components

- `MainLayout`: Application shell with header and sidebar
- `Header`: Top navigation with user info and logout
- `Sidebar`: Navigation menu with collapsible design

## API Integration

The frontend integrates with the REST Simple API supporting:

- **Student Operations**: GET, POST, PUT, DELETE `/students`
- **Authentication**: POST `/auth/login`, `/auth/register`, `/auth/refresh`
- **Health Check**: GET `/healthz`

### Error Handling

- Comprehensive error handling with user-friendly messages
- Automatic token refresh for expired tokens
- Network timeout handling
- Validation errors for form inputs

## Development

### Project Structure

```
src/
├── app/                    # Next.js app router pages
├── components/             # React components
│   ├── auth/              # Authentication components
│   ├── layout/            # Layout components
│   ├── students/          # Student management components
│   └── ui/                # Base UI components (shadcn)
├── hooks/                 # Custom React hooks
├── lib/                   # Utilities and configurations
└── types/                 # TypeScript type definitions
```

### Available Scripts

- `npm run dev`: Start development server
- `npm run build`: Build for production
- `npm run start`: Start production server
- `npm run lint`: Run ESLint

### Adding New Features

1. Define types in `src/types/`
2. Add API calls to `src/lib/api-client.ts`
3. Create custom hooks in `src/hooks/`
4. Build UI components in `src/components/`
5. Add pages in `src/app/`

## Production Deployment

1. **Build the application**:

   ```bash
   npm run build
   ```

2. **Set environment variables**:

   - `NEXT_PUBLIC_API_BASE_URL`: Your production API URL
   - `NEXT_PUBLIC_AUTH_ENABLED`: Authentication configuration

3. **Deploy** using your preferred platform (Vercel, Netlify, Docker, etc.)

## Contributing

1. Follow the existing code style and component patterns
2. Use TypeScript for all new code
3. Add proper error handling and loading states
4. Test authentication both enabled and disabled modes
5. Ensure responsive design works on mobile devices

## License

ISC
