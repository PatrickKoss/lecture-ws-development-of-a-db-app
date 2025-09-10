#!/bin/bash
# Emergency cleanup script for Docker environment issues

echo "=== Stopping all student containers ==="
cd /opt/student-env 2>/dev/null && docker-compose down -v --remove-orphans || true

echo "=== Force removing all student containers ==="
docker ps -aq --filter "name=student*-vscode" | xargs -r docker rm -f

echo "=== Removing all student volumes ==="
docker volume ls -q --filter "name=student*" | xargs -r docker volume rm -f

echo "=== Removing corrupted student-vscode image ==="
docker rmi student-vscode:latest -f 2>/dev/null || true

echo "=== Pruning Docker system ==="
docker system prune -f --volumes

echo "=== Rebuilding the Docker image ==="
cd /opt/student-env/students/student1/java/rest-simple && \
docker build -t student-vscode:latest --build-arg SERVER_PORT=8081 .

echo "=== Starting fresh containers ==="
cd /opt/student-env && docker-compose up -d

echo "=== Cleanup complete! ==="
docker ps | grep student