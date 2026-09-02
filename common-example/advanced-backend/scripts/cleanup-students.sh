#!/bin/bash

# Script to clean up student environments
# Usage: ./scripts/cleanup-students.sh

echo "Cleaning up student environments..."

# Stop all student containers
echo "Stopping student containers..."
docker stop $(docker ps -q --filter name=student) 2>/dev/null || echo "No running student containers found"

# Remove all student containers
echo "Removing student containers..."
docker rm $(docker ps -aq --filter name=student) 2>/dev/null || echo "No student containers found"

# Remove the student environment image
echo "Removing Docker image..."
docker rmi java-student-env 2>/dev/null || echo "Image not found"

echo "Cleanup complete!"
echo ""
echo "Note: Student workspace directories in /home/student* are preserved."
echo "To remove them manually: sudo rm -rf /home/student*"