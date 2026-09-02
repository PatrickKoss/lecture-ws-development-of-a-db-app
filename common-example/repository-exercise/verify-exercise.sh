#!/bin/bash

echo "=== Student Management Exercise Verification ==="
echo

# Check if all required files exist
echo "✓ Checking project structure..."

required_files=(
    "build.gradle.kts"
    "settings.gradle.kts" 
    ".gitignore"
    "README.md"
    "gradlew"
    "src/main/java/org/lecture/Student.java"
    "src/main/java/org/lecture/StudentRepository.java"
    "src/main/java/org/lecture/StudentRepositoryImpl.java"
    "src/main/java/org/lecture/Main.java"
)

for file in "${required_files[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ $file (MISSING)"
    fi
done

echo

# Check if project builds
echo "✓ Testing build..."
if ./gradlew build --quiet; then
    echo "  ✓ Project builds successfully"
else
    echo "  ✗ Build failed"
    exit 1
fi

echo

# Check for TODO markers in StudentRepositoryImpl
echo "✓ Checking implementation status..."
todo_count=$(grep -c "UnsupportedOperationException" src/main/java/org/lecture/StudentRepositoryImpl.java)
echo "  → $todo_count methods to implement in StudentRepositoryImpl"

echo

# Check CLI skeleton
echo "✓ Checking CLI skeleton..."
if grep -q "TODO: Implement StudentRepository" src/main/java/org/lecture/Main.java; then
    echo "  ✓ CLI skeleton is properly prepared"
else
    echo "  ✗ CLI skeleton may be incomplete"
fi

echo
echo "=== Exercise Setup Complete ==="
echo "Students can now:"
echo "1. Implement StudentRepositoryImpl.java"
echo "2. Uncomment TODO blocks in Main.java"  
echo "3. Run './gradlew run' to test their implementation"
echo