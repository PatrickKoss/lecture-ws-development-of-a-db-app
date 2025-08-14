# REST API Development Environment Setup

A Spring Boot REST API with SQLite database and JWT authentication.

## Requirements

- **Java 21** (Temurin JDK recommended)
- **Gradle 8.5+**
- **SQLite3** (for database operations)
- **VS Code** (for development)

## Windows Setup

### Install Java 21

```powershell
# Using winget
winget install EclipseAdoptium.Temurin.21.JDK

# Or download from https://adoptium.net/temurin/releases/?version=21
```

### Install Gradle

```powershell
# Using chocolatey
choco install gradle

# Or using winget
winget install Gradle.Gradle
```

### Install SQLite

```powershell
# Using chocolatey
choco install sqlite

# Or download from https://sqlite.org/download.html
```

### Install VS Code

```powershell
# Using winget
winget install Microsoft.VisualStudioCode

# Or download from https://code.visualstudio.com/
```

### Install VS Code Extensions

```bash
# Essential Java extensions
code --install-extension vscjava.vscode-java-pack
code --install-extension redhat.java

# Spring Boot extensions
code --install-extension vmware.vscode-spring-boot
code --install-extension vscjava.vscode-spring-boot-dashboard

# Database and utility extensions
code --install-extension alexcvzz.vscode-sqlite
code --install-extension redhat.vscode-xml
code --install-extension redhat.vscode-yaml
code --install-extension humao.rest-client
code --install-extension rangav.vscode-thunder-client
```

### Verify Installation

```powershell
java -version
gradle -version
sqlite3 --version
```

## Linux Setup

### Install Java 21

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install -y wget gnupg
wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo apt-key add -
echo "deb https://packages.adoptium.net/artifactory/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/adoptium.list
sudo apt update
sudo apt install temurin-21-jdk

# CentOS/RHEL/Fedora
sudo dnf install java-21-openjdk-devel

# Arch Linux
sudo pacman -S jdk21-openjdk
```

### Install Gradle

```bash
# Using SDKMAN (recommended)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install gradle 8.5

# Or manual installation
wget https://services.gradle.org/distributions/gradle-8.5-bin.zip
sudo unzip -d /opt/gradle gradle-8.5-bin.zip
echo 'export GRADLE_HOME=/opt/gradle/gradle-8.5' >> ~/.bashrc
echo 'export PATH=$GRADLE_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc
```

### Install SQLite

```bash
# Ubuntu/Debian
sudo apt install sqlite3

# CentOS/RHEL/Fedora
sudo dnf install sqlite

# Arch Linux
sudo pacman -S sqlite
```

### Install VS Code

```bash
# Ubuntu/Debian
sudo apt install software-properties-common apt-transport-https wget
wget -qO- https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > packages.microsoft.gpg
sudo install -o root -g root -m 644 packages.microsoft.gpg /etc/apt/trusted.gpg.d/
sudo sh -c 'echo "deb [arch=amd64,arm64,armhf signed-by=/etc/apt/trusted.gpg.d/packages.microsoft.gpg] https://packages.microsoft.com/repos/code stable main" > /etc/apt/sources.list.d/vscode.list'
sudo apt update
sudo apt install code

# CentOS/RHEL/Fedora
sudo rpm --import https://packages.microsoft.com/keys/microsoft.asc
sudo sh -c 'echo -e "[code]\nname=Visual Studio Code\nbaseurl=https://packages.microsoft.com/yumrepos/vscode\nenabled=1\ngpgcheck=1\ngpgkey=https://packages.microsoft.com/keys/microsoft.asc" > /etc/yum.repos.d/vscode.repo'
sudo dnf install code

# Arch Linux
sudo pacman -S code
```

### Install VS Code Extensions

```bash
# Essential Java extensions
code --install-extension vscjava.vscode-java-pack
code --install-extension redhat.java

# Spring Boot extensions
code --install-extension vmware.vscode-spring-boot
code --install-extension vscjava.vscode-spring-boot-dashboard

# Database and utility extensions
code --install-extension alexcvzz.vscode-sqlite
code --install-extension redhat.vscode-xml
code --install-extension redhat.vscode-yaml
code --install-extension humao.rest-client
code --install-extension rangav.vscode-thunder-client
```

### Verify Installation

```bash
java -version
gradle -version
sqlite3 --version
```

## macOS Setup

### Install Java 21

```bash
# Using Homebrew
brew install --cask temurin21

# Or using SDKMAN
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 21.0.5-tem
```

### Install Gradle

```bash
# Using Homebrew
brew install gradle

# Or using SDKMAN
sdk install gradle 8.5
```

### Install SQLite

```bash
# Using Homebrew (usually pre-installed)
brew install sqlite
```

### Install VS Code

```bash
# Using Homebrew
brew install --cask visual-studio-code

# Or download from https://code.visualstudio.com/
```

### Install VS Code Extensions

```bash
# Essential Java extensions
code --install-extension vscjava.vscode-java-pack
code --install-extension redhat.java

# Spring Boot extensions
code --install-extension vmware.vscode-spring-boot
code --install-extension vscjava.vscode-spring-boot-dashboard

# Database and utility extensions
code --install-extension alexcvzz.vscode-sqlite
code --install-extension redhat.vscode-xml
code --install-extension redhat.vscode-yaml
code --install-extension humao.rest-client
code --install-extension rangav.vscode-thunder-client
```

### Verify Installation

```bash
java -version
gradle -version
sqlite3 --version
```

## Running the Application

### Build and Run

```bash
cd rest-simple
./gradlew build
./gradlew bootRun
```

### Development Mode

```bash
# Run with auto-reload
./gradlew bootRun --continuous
```

### Testing

```bash
# Run all tests
./gradlew test

# Run with coverage
./gradlew test jacocoTestReport
```

## API Documentation

Once running, access Swagger UI at: http://localhost:8081/swagger-ui.html

## Database

SQLite database file: `students.db` (created automatically)

View database:

```bash
sqlite3 students.db
.tables
.schema
```

## Docker Alternative

If you prefer using Docker:

```bash
cd rest-simple
docker build -t rest-simple .
docker run -p 8080:8080 -p 8081:8081 rest-simple
```

Access VS Code Server at: http://localhost:8080
