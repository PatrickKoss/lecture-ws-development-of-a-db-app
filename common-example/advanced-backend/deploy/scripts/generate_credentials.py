#!/usr/bin/env python3
"""
Generate student credentials for the development environment.
Usage: python3 generate_credentials.py [--count=23] [--output=credentials.json]
"""

import json
import argparse
import secrets
import string
from datetime import datetime

def generate_password(length=16):
    """Generate a secure random password."""
    alphabet = string.ascii_letters + string.digits
    return ''.join(secrets.choice(alphabet) for _ in range(length))

def generate_credentials(student_count, output_file):
    """Generate credentials for all students."""
    credentials = []
    
    for i in range(1, student_count + 1):
        credentials.append({
            'username': f'student{i}',
            'password': generate_password()
        })
    
    data = {
        'generated_at': datetime.now().isoformat(),
        'student_count': student_count,
        'credentials': credentials
    }
    
    with open(output_file, 'w') as f:
        json.dump(data, f, indent=2)
    
    print(f"Generated credentials for {student_count} students in {output_file}")
    print("\nCredentials:")
    for i, cred in enumerate(credentials, 1):
        print(f"Student {i}: {cred['username']} / {cred['password']}")

def main():
    parser = argparse.ArgumentParser(description='Generate student credentials')
    parser.add_argument('--count', type=int, default=23, help='Number of students (default: 23)')
    parser.add_argument('--output', default='student-credentials.json', help='Output file (default: student-credentials.json)')
    
    args = parser.parse_args()
    
    generate_credentials(args.count, args.output)

if __name__ == '__main__':
    main()