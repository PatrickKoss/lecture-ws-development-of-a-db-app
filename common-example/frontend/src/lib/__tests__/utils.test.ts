import { cn } from '../utils';

describe('cn utility function', () => {
  it('should merge class names correctly', () => {
    const result = cn('px-2 py-1', 'bg-red-500');
    expect(result).toBe('px-2 py-1 bg-red-500');
  });

  it('should handle conditional classes', () => {
    const result = cn('base-class', true && 'conditional-class', false && 'hidden-class');
    expect(result).toBe('base-class conditional-class');
  });

  it('should handle conflicting Tailwind classes', () => {
    const result = cn('px-2 px-4'); // px-4 should win
    expect(result).toBe('px-4');
  });

  it('should handle arrays of classes', () => {
    const result = cn(['px-2', 'py-1'], 'bg-blue-500');
    expect(result).toBe('px-2 py-1 bg-blue-500');
  });

  it('should handle empty and undefined values', () => {
    const result = cn('px-2', '', undefined, null, 'py-1');
    expect(result).toBe('px-2 py-1');
  });

  it('should merge overlapping utility classes correctly', () => {
    const result = cn('text-sm text-lg'); // text-lg should win
    expect(result).toBe('text-lg');
  });
});