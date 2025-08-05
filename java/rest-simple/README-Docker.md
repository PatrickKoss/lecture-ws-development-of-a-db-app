# Docker Setup for Student Environments

This setup provides individual Docker containers for each student with VS Code Server, Java 21, Gradle, and all necessary development tools.

## Quick Start

### Option 1: Automated Setup (Recommended)

Set up containers for 30 students:
```bash
./scripts/setup-students.sh 30
```

### Option 2: Manual Single Container

Run a single student container:
```bash
# Build the image
docker build -t java-student-env .

# Run container for student1
docker run -d \
  -v /home/student1:/home/student/workspace \
  -p 8001:8080 \
  -p 8101:8081 \
  --name student1-env \
  java-student-env
```

## Access Points

Each student gets:
- **VS Code Server**: `http://localhost:800X` (where X = student number)
- **Spring Boot App**: `http://localhost:810X`
- **Workspace**: `/home/studentX/` (mounted as volume)

Example for student1:
- VS Code: http://localhost:8001
- App: http://localhost:8101

## Container Features

- **Java 21** (Temurin/AdoptOpenJDK)
- **Gradle 8.5** 
- **SQLite3** for database
- **VS Code Server** with Java extensions
- **Git** for version control
- **Development tools**: vim, nano, tree, htop

## Management Commands

### Setup
```bash
# Set up 30 student environments
./scripts/setup-students.sh 30

# Check running containers
docker ps --filter name=student
```

### Cleanup
```bash
# Stop and remove all student containers
./scripts/cleanup-students.sh

# Or manually:
docker stop $(docker ps -q --filter name=student)
docker rm $(docker ps -aq --filter name=student)
```

### Individual Container Management
```bash
# Stop specific student
docker stop student1-env

# Start specific student  
docker start student1-env

# View logs
docker logs student1-env

# Execute commands in container
docker exec -it student1-env bash
```

## File Structure

After setup, each student has:
```
/home/studentX/
├── src/                    # Java source code
├── build.gradle           # Gradle build file
├── gradlew               # Gradle wrapper
├── students.db           # SQLite database (created on first run)
└── ...                   # All project files
```

## Port Allocation

- Student 1: VS Code 8001, App 8101
- Student 2: VS Code 8002, App 8102
- ...
- Student 30: VS Code 8030, App 8130

## Troubleshooting

### Container won't start
```bash
# Check container logs
docker logs studentX-env

# Check if ports are in use
netstat -tlnp | grep 800X
```

### Permission issues
```bash
# Fix ownership of student directory
sudo chown -R 1000:1000 /home/studentX
```

### Reset student environment
```bash
# Remove container and recreate
docker stop studentX-env
docker rm studentX-env
./scripts/setup-students.sh 1  # Just recreate one
```

## System Requirements

- Docker installed and running
- Sufficient ports available (8001-8030 and 8101-8130 for 30 students)
- Adequate disk space for student workspaces
- Memory: ~500MB per container (15GB total for 30 students)