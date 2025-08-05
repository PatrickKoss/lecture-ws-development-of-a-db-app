FROM ubuntu:22.04

# Avoid prompts from apt
ENV DEBIAN_FRONTEND=noninteractive

# Set up user for development
ARG USERNAME=student
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Install system dependencies
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    git \
    unzip \
    sqlite3 \
    build-essential \
    ca-certificates \
    gnupg \
    lsb-release \
    sudo \
    vim \
    nano \
    tree \
    htop \
    && rm -rf /var/lib/apt/lists/*

# Install Java 21
RUN wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | apt-key add - \
    && echo "deb https://packages.adoptium.net/artifactory/deb $(lsb_release -cs) main" | tee /etc/apt/sources.list.d/adoptium.list \
    && apt-get update \
    && apt-get install -y temurin-21-jdk \
    && rm -rf /var/lib/apt/lists/*

# Set JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Install Gradle
ENV GRADLE_VERSION=8.5
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME=/opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH=$GRADLE_HOME/bin:$PATH

# Create user
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME \
    && echo $USERNAME ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME

# Install VS Code Server
USER $USERNAME
WORKDIR /home/$USERNAME

# Download and install code-server
RUN curl -fsSL https://code-server.dev/install.sh | sh

# Create VS Code Server config directory
RUN mkdir -p /home/$USERNAME/.config/code-server

# Configure VS Code Server
RUN echo "bind-addr: 0.0.0.0:8080" > /home/$USERNAME/.config/code-server/config.yaml \
    && echo "auth: none" >> /home/$USERNAME/.config/code-server/config.yaml \
    && echo "cert: false" >> /home/$USERNAME/.config/code-server/config.yaml

# Install essential VS Code extensions for Java development
# Install core Java extensions first
RUN code-server --install-extension vscjava.vscode-java-pack || echo "Failed to install Java Pack, continuing..." \
    && code-server --install-extension redhat.java || echo "Failed to install Language Support for Java, continuing..."

# Install Spring Boot extensions
RUN code-server --install-extension vmware.vscode-spring-boot || echo "Failed to install Spring Boot Tools, continuing..." \
    && code-server --install-extension vscjava.vscode-spring-boot-dashboard || echo "Failed to install Spring Boot Dashboard, continuing..."

# Install database and file format extensions
RUN code-server --install-extension alexcvzz.vscode-sqlite || echo "Failed to install SQLite extension, continuing..." \
    && code-server --install-extension redhat.vscode-xml || echo "Failed to install XML extension, continuing..." \
    && code-server --install-extension redhat.vscode-yaml || echo "Failed to install YAML extension, continuing..."

# Install development productivity extensions
RUN code-server --install-extension eamodio.gitlens || echo "Failed to install GitLens, continuing..." \
    && code-server --install-extension ms-vscode.vscode-json || echo "Failed to install JSON extension, continuing..." \
    && code-server --install-extension PKief.material-icon-theme || echo "Failed to install Material Icon Theme, continuing..." \
    && code-server --install-extension formulahendry.auto-rename-tag || echo "Failed to install Auto Rename Tag, continuing..." \
    && code-server --install-extension oderwat.indent-rainbow || echo "Failed to install Indent Rainbow, continuing..."

# Install API testing and additional tools
RUN code-server --install-extension humao.rest-client || echo "Failed to install REST Client, continuing..." \
    && code-server --install-extension rangav.vscode-thunder-client || echo "Failed to install Thunder Client, continuing..." \
    && code-server --install-extension wayou.vscode-todo-highlight || echo "Failed to install TODO Highlight, continuing..." \
    && code-server --install-extension streetsidesoftware.code-spell-checker || echo "Failed to install Code Spell Checker, continuing..."

# Set up workspace directory and VS Code settings
RUN mkdir -p /home/$USERNAME/workspace/.vscode \
    && mkdir -p /home/$USERNAME/.config/code-server/User

# Create VS Code settings for optimal Java development
RUN echo '{\
    "java.configuration.updateBuildConfiguration": "automatic",\
    "java.compile.nullAnalysis.mode": "automatic",\
    "java.debug.settings.onBuildFailureProceed": true,\
    "java.format.settings.url": "",\
    "java.saveActions.organizeImports": true,\
    "java.test.config": {},\
    "java.gradle.buildServer.enabled": "on",\
    "gradle.nestedProjects": true,\
    "spring-boot.ls.checkjvm": false,\
    "spring.initializr.defaultLanguage": "Java",\
    "files.associations": {\
        "*.sql": "sql"\
    },\
    "sqltools.connections": [\
        {\
            "name": "SQLite - students.db",\
            "driver": "SQLite",\
            "database": "./students.db"\
        }\
    ],\
    "workbench.iconTheme": "material-icon-theme",\
    "editor.formatOnSave": true,\
    "editor.codeActionsOnSave": {\
        "source.organizeImports": true\
    },\
    "git.enableSmartCommit": true,\
    "git.confirmSync": false,\
    "terminal.integrated.defaultProfile.linux": "bash"\
}' > /home/$USERNAME/.config/code-server/User/settings.json

# Create workspace-specific settings
RUN echo '{\
    "java.configuration.runtimes": [\
        {\
            "name": "JavaSE-21",\
            "path": "/usr/lib/jvm/temurin-21-jdk-amd64"\
        }\
    ],\
    "java.compile.nullAnalysis.mode": "automatic",\
    "java.gradle.buildServer.enabled": "on"\
}' > /home/$USERNAME/workspace/.vscode/settings.json

# Set working directory
WORKDIR /home/$USERNAME/workspace

# Expose VS Code Server port
EXPOSE 8080

# Expose Spring Boot application port
EXPOSE 8081

# Start VS Code Server
CMD ["code-server", "--bind-addr", "0.0.0.0:8080", "--auth", "none", "/home/student/workspace"]