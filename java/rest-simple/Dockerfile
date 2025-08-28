FROM ubuntu:22.04

# Avoid prompts from apt
ENV DEBIAN_FRONTEND=noninteractive

# Set up user for development
ARG USERNAME=student
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Install minimal system dependencies
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    git \
    unzip \
    sqlite3 \
    ca-certificates \
    gnupg \
    lsb-release \
    vim \
    nano \
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
ENV GRADLE_VERSION=8.14
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME=/opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH=$GRADLE_HOME/bin:$PATH

# Create restricted user without sudo access
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m -s /bin/bash $USERNAME \
    && mkdir -p /home/$USERNAME/workspace \
    && chown -R $USERNAME:$USERNAME /home/$USERNAME

# Install VS Code Server as root before removing tools
RUN curl -fsSL https://code-server.dev/install.sh | sh \
    && cp /usr/bin/code-server /usr/local/bin/code-server 2>/dev/null || cp ~/.local/bin/code-server /usr/local/bin/code-server 2>/dev/null || echo "code-server copied"

# Remove dangerous tools after installation is complete (but preserve code-server)
RUN rm -f /usr/bin/wget /usr/bin/curl /bin/wget /bin/curl \
    && rm -f /usr/bin/apt /usr/bin/apt-get /usr/bin/dpkg \
    && rm -f /usr/bin/snap /usr/bin/systemctl \
    && rm -rf /var/lib/apt/lists/*

# Create restricted PATH for student user
# RUN echo '#!/bin/bash' > /usr/local/bin/restricted-shell \
#     && echo 'export PATH=/usr/lib/jvm/temurin-21-jdk-amd64/bin:/opt/gradle/gradle-8.5/bin:/usr/bin:/bin:/usr/local/bin' >> /usr/local/bin/restricted-shell \
#     && echo 'export SHELL=/bin/bash' >> /usr/local/bin/restricted-shell \
#     && echo 'cd /home/student/workspace' >> /usr/local/bin/restricted-shell \
#     && echo 'exec /bin/bash "$@"' >> /usr/local/bin/restricted-shell \
#     && chmod +x /usr/local/bin/restricted-shell

# Switch to student user
USER $USERNAME
WORKDIR /home/$USERNAME

# Set Java environment for the student user
ENV JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64
ENV GRADLE_HOME=/opt/gradle/gradle-8.14
ENV PATH=$JAVA_HOME/bin:$GRADLE_HOME/bin:$PATH
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
ENV SHELL=/bin/bash

# Create VS Code Server config directory
RUN mkdir -p /home/$USERNAME/.config/code-server

# Configure VS Code Server
RUN echo "bind-addr: 0.0.0.0:8080" > /home/$USERNAME/.config/code-server/config.yaml \
    && echo "auth: none" >> /home/$USERNAME/.config/code-server/config.yaml \
    && echo "cert: false" >> /home/$USERNAME/.config/code-server/config.yaml

# Install essential VS Code extensions for Java development
# Install core Java extensions first
RUN code-server --install-extension redhat.java || echo "Failed to install Language Support for Java, continuing..." \
    && code-server --install-extension vscjava.vscode-java-debug || echo "Failed to install Java Debugger, continuing..." \
    && code-server --install-extension vscjava.vscode-java-test || echo "Failed to install Java Test Runner, continuing..." \
    && code-server --install-extension vscjava.vscode-maven || echo "Failed to install Maven for Java, continuing..."

# Install Spring Boot extensions  
RUN code-server --install-extension vmware.vscode-spring-boot || echo "Failed to install Spring Boot Tools, continuing..." \
    && code-server --install-extension vscjava.vscode-spring-initializr || echo "Failed to install Spring Initializr, continuing..."

# Install database and file format extensions
RUN code-server --install-extension alexcvzz.vscode-sqlite || echo "Failed to install SQLite extension, continuing..." \
    && code-server --install-extension redhat.vscode-xml || echo "Failed to install XML extension, continuing..." \
    && code-server --install-extension redhat.vscode-yaml || echo "Failed to install YAML extension, continuing..."

# Install development productivity extensions
RUN code-server --install-extension k--kato.intellij-idea-keybindings || echo "Failed to install IntelliJ IDEA Keybindings, continuing..." \
    && code-server --install-extension eamodio.gitlens || echo "Failed to install GitLens, continuing..." \
    && code-server --install-extension PKief.material-icon-theme || echo "Failed to install Material Icon Theme, continuing..." \
    && code-server --install-extension formulahendry.auto-rename-tag || echo "Failed to install Auto Rename Tag, continuing..." \
    && code-server --install-extension oderwat.indent-rainbow || echo "Failed to install Indent Rainbow, continuing..."

# Install API testing and additional tools
RUN code-server --install-extension humao.rest-client || echo "Failed to install REST Client, continuing..." \
    && code-server --install-extension rangav.vscode-thunder-client || echo "Failed to install Thunder Client, continuing..." \
    && code-server --install-extension gruntfuggly.todo-tree || echo "Failed to install TODO Tree, continuing..." \
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
    "java.jdt.ls.vmargs": "-XX:+UseParallelGC -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -Dsun.zip.disableMemoryMapping=true -Xmx2G -Xms100m -Dfile.encoding=UTF-8",\
    "java.home": "/usr/lib/jvm/temurin-21-jdk-amd64",\
    "java.server.launchMode": "Standard",\
    "java.configuration.checkProjectSettingsExclusions": false,\
    "java.autobuild.enabled": true,\
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

# Create workspace-specific settings for multi-project support
RUN echo '{\
    "java.configuration.runtimes": [\
        {\
            "name": "JavaSE-21",\
            "path": "/usr/lib/jvm/temurin-21-jdk-amd64"\
        }\
    ],\
    "java.compile.nullAnalysis.mode": "automatic",\
    "java.gradle.buildServer.enabled": "on",\
    "java.import.gradle.enabled": true,\
    "java.import.gradle.wrapper.enabled": true,\
    "java.import.gradle.offline.enabled": false,\
    "java.import.gradle.arguments": "",\
    "java.import.gradle.jvmArguments": "",\
    "java.import.gradle.home": "/opt/gradle/gradle-8.14",\
    "java.import.gradle.java.home": "/usr/lib/jvm/temurin-21-jdk-amd64",\
    "java.import.maven.enabled": true,\
    "java.configuration.detectProjectsInWorkspace": true,\
    "java.maxConcurrentBuilds": 1,\
    "java.project.sourcePaths": [],\
    "java.project.outputPath": "",\
    "java.configuration.workspaceCacheLimit": 90,\
    "java.sources.organizeImports.starThreshold": 99,\
    "java.sources.organizeImports.staticStarThreshold": 99,\
    "java.configuration.maven.userSettings": null,\
    "java.eclipse.downloadSources": true,\
    "java.maven.downloadSources": true,\
    "java.gradle.buildServer.enabled": "on",\
    "files.exclude": {\
        "**/.classpath": true,\
        "**/.project": true,\
        "**/.settings": true,\
        "**/.factorypath": true\
    },\
    "java.import.exclusions": [\
        "**/node_modules/**",\
        "**/.metadata/**",\
        "**/archetype-resources/**",\
        "**/META-INF/maven/**"\
    ]\
}' > /home/$USERNAME/workspace/.vscode/settings.json

# Set proper permissions for workspace and create Gradle directories
RUN mkdir -p /home/$USERNAME/.gradle \
    && chown -R $USERNAME:$USERNAME /home/$USERNAME \
    && find /home/$USERNAME/.local/share/code-server/extensions -name "java" -type f -exec chmod 755 {} \; 2>/dev/null || true \
    && find /home/$USERNAME/.local/share/code-server/extensions -path "*/bin/*" -type f -exec chmod 755 {} \; 2>/dev/null || true \
    && find /home/$USERNAME/.local/share/code-server/extensions -path "*/jre/*/bin/*" -type f -exec chmod 755 {} \; 2>/dev/null || true

# Create a startup script to help with project detection and fix Gradle issues
RUN echo '#!/bin/bash' > /home/$USERNAME/setup-java-project.sh \
    && echo 'echo "Setting up Java project environment..."' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'echo "Cleaning Gradle cache completely..."' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'rm -rf ~/.gradle/caches/ 2>/dev/null || true' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'rm -rf ~/.gradle/daemon/ 2>/dev/null || true' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'mkdir -p ~/.gradle/caches' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'chmod -R 755 ~/.gradle/ 2>/dev/null || true' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'if [ -f "./build.gradle" ] || [ -f "./build.gradle.kts" ]; then' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  echo "Gradle project detected in current directory"' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  chmod +x ./gradlew 2>/dev/null || true' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  echo "Testing Gradle wrapper..."' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  ./gradlew --version 2>/dev/null || echo "Gradle wrapper failed, trying system gradle..."' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  gradle --version 2>/dev/null || echo "System gradle not found"' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'else' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  echo "No Gradle project found in current directory"' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  echo "Looking for Java projects in subdirectories..."' >> /home/$USERNAME/setup-java-project.sh \
    && echo '  find . -name "build.gradle" -o -name "build.gradle.kts" 2>/dev/null | head -5' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'fi' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'echo "Java home: $JAVA_HOME"' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'echo "Gradle home: $GRADLE_HOME"' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'echo "Java version:"' >> /home/$USERNAME/setup-java-project.sh \
    && echo 'java --version' >> /home/$USERNAME/setup-java-project.sh \
    && chmod +x /home/$USERNAME/setup-java-project.sh

# Create allowed commands list for student user
RUN echo 'java' > /home/$USERNAME/.allowed_commands \
    && echo 'javac' >> /home/$USERNAME/.allowed_commands \
    && echo 'gradle' >> /home/$USERNAME/.allowed_commands \
    && echo 'sqlite3' >> /home/$USERNAME/.allowed_commands \
    && echo 'git' >> /home/$USERNAME/.allowed_commands \
    && echo 'vim' >> /home/$USERNAME/.allowed_commands \
    && echo 'nano' >> /home/$USERNAME/.allowed_commands \
    && echo 'ls' >> /home/$USERNAME/.allowed_commands \
    && echo 'cd' >> /home/$USERNAME/.allowed_commands \
    && echo 'mkdir' >> /home/$USERNAME/.allowed_commands \
    && echo 'rm' >> /home/$USERNAME/.allowed_commands \
    && echo 'cp' >> /home/$USERNAME/.allowed_commands \
    && echo 'mv' >> /home/$USERNAME/.allowed_commands \
    && echo 'cat' >> /home/$USERNAME/.allowed_commands \
    && echo 'grep' >> /home/$USERNAME/.allowed_commands \
    && echo 'find' >> /home/$USERNAME/.allowed_commands \
    && echo 'chmod' >> /home/$USERNAME/.allowed_commands

# Set working directory
WORKDIR /home/$USERNAME/workspace

# Expose VS Code Server port
EXPOSE 8080

# Expose Spring Boot application port (configurable via SERVER_PORT)
ARG SERVER_PORT=8888
ENV SERVER_PORT=${SERVER_PORT}
EXPOSE ${SERVER_PORT}

# Start VS Code Server with restricted environment
CMD ["/usr/local/bin/code-server", "--bind-addr", "0.0.0.0:8080", "--auth", "none", "/home/student/workspace"]