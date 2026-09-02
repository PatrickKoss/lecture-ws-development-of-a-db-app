#!/bin/bash

# Script to set up student environments
# Usage: ./scripts/setup-students.sh <number_of_students>

if [ -z "$1" ]; then
    echo "Usage: $0 <number_of_students>"
    echo "Example: $0 30"
    exit 1
fi

NUM_STUDENTS=$1
IMAGE_NAME="java-student-env"

echo "Setting up $NUM_STUDENTS student environments..."

# Build the Docker image
echo "Building Docker image..."
docker build -t $IMAGE_NAME .

# Create student directories and containers
for i in $(seq 1 $NUM_STUDENTS); do
    STUDENT_DIR="/home/student$i"
    VSCODE_PORT=$((8000 + i))
    APP_PORT=$((8100 + i))
    
    echo "Setting up student$i..."
    
    # Create student directory if it doesn't exist
    sudo mkdir -p $STUDENT_DIR
    sudo chown 1000:1000 $STUDENT_DIR
    
    # Copy project files to student directory
    sudo cp -r . $STUDENT_DIR/
    sudo chown -R 1000:1000 $STUDENT_DIR
    
    # Run container for student
    docker run -d \
        --name "student$i-env" \
        -v "$STUDENT_DIR:/home/student/workspace" \
        -p "$VSCODE_PORT:8080" \
        -p "$APP_PORT:8081" \
        --restart unless-stopped \
        $IMAGE_NAME
    
    echo "Student$i environment ready:"
    echo "  - VS Code Server: http://localhost:$VSCODE_PORT"
    echo "  - Spring Boot App: http://localhost:$APP_PORT"
    echo "  - Workspace: $STUDENT_DIR"
    echo ""
done

echo "All student environments are ready!"
echo ""
echo "To stop all containers: docker stop \$(docker ps -q --filter name=student)"
echo "To remove all containers: docker rm \$(docker ps -aq --filter name=student)"