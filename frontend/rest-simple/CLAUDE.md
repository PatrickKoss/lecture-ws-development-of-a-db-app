# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

```bash
# Development
npm run dev          # Start development server on http://localhost:3000
npm run build        # Build for production
npm run start        # Start production server
npm run lint         # Run ESLint

# Environment setup
cp .env.sample .env.local    # Copy environment template
```

## Architecture & Core Concepts

### Authentication Toggle System
This application features a configurable authentication system controlled by `NEXT_PUBLIC_AUTH_ENABLED`:
- **Enabled (true)**: Full JWT-based authentication with login/register flows
- **Disabled (false)**: Bypasses authentication for rudimentary backends

The auth state logic is centralized in `src/lib/config.ts` and `src/hooks/use-auth.tsx`. The `AuthGuard` component conditionally renders login forms or allows direct access based on this configuration.

### API Client Architecture
The `ApiClient` class (`src/lib/api-client.ts`) implements:
- Automatic JWT token management (access + refresh tokens)
- Request/response interceptors with timeout handling
- SSR-safe localStorage operations (window checks)
- Configurable auth header injection based on `appConfig.auth.enabled`

### State Management Pattern
Uses React Context + custom hooks pattern:
- `useAuth()`: Authentication state, login/logout actions
- `useStudents()`: Student CRUD operations with optimistic updates
- Context providers wrap the app in `src/app/layout.tsx`

### Component Architecture
Following a hierarchical component structure:

**Page Components** (`src/app/*/page.tsx`):
- Thin wrappers that import and render main components
- Handle routing and page-level concerns

**Main Components** (`src/components/*/`):
- `*-page.tsx`: Full page implementations with state management
- `*-dialog.tsx`: Modal forms for create/edit operations
- `*-table.tsx`: Data display components with inline actions

**Layout System**:
- `MainLayout`: Application shell (header + sidebar + main content)
- `AuthGuard`: Conditional rendering based on auth state
- Collapsible sidebar with route-based active states

### Type Safety Strategy
All API interactions are typed based on the OpenAPI specification:
- `src/types/api.ts`: Request/response interfaces matching OpenAPI schemas
- `src/types/config.ts`: Application configuration and user types
- Generic `ApiClient.request<T>()` method for type-safe API calls

### Error Handling Patterns
Multi-layered error handling:
1. **API Level**: `ApiClient` catches fetch errors, timeouts, HTTP errors
2. **Hook Level**: Custom hooks catch API errors and expose error state
3. **Component Level**: Components display errors and provide retry mechanisms
4. **Form Level**: Validation errors with field-specific messages

## Key Configuration Points

### Environment Variables
- `NEXT_PUBLIC_API_BASE_URL`: Backend API endpoint (default: http://localhost:8081)
- `NEXT_PUBLIC_AUTH_ENABLED`: Authentication toggle (default: true)

### API Client Configuration
Token storage keys and timeout values are configured in `src/lib/config.ts`. The client automatically handles:
- JWT token persistence in localStorage
- Automatic Authorization header injection
- Token refresh on expiration (TODO: implement refresh logic)

### Styling System
- Tailwind CSS with custom design tokens in `src/app/globals.css`
- shadcn/ui components in `src/components/ui/`
- `cn()` utility function for conditional class merging

## Integration with REST API

The frontend integrates with the OpenAPI specification at `api/openapi.yaml`:

**Student Operations**: 
- GET `/students` → `StudentsListResponse`
- POST `/students` → `CreateStudentRequest` → `StudentResponse`  
- PUT `/students/{id}` → `UpdateStudentRequest` → `StudentResponse`
- DELETE `/students/{id}` → 204 No Content

**Authentication Operations**:
- POST `/auth/login` → `LoginRequest` → `AuthResponse`
- POST `/auth/register` → `RegisterRequest` → Success/Error
- POST `/auth/refresh` → `RefreshTokenRequest` → `AuthResponse`

**Health Check**:
- GET `/healthz` → Status response

## Adding New Features

1. **Define Types**: Add interfaces to `src/types/api.ts` based on OpenAPI spec
2. **Extend API Client**: Add methods to `src/lib/api-client.ts` 
3. **Create Custom Hook**: Add state management hook in `src/hooks/`
4. **Build UI Components**: Create components in appropriate `src/components/` subdirectory
5. **Add Pages**: Create page components in `src/app/` following Next.js 15 App Router conventions

## Testing Authentication Modes

When implementing features, test both authentication states:
- Set `NEXT_PUBLIC_AUTH_ENABLED=true` and verify login/logout flows
- Set `NEXT_PUBLIC_AUTH_ENABLED=false` and verify direct access to protected routes