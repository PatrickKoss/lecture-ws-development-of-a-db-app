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
ENV GRADLE_VERSION=8.5
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp \
    && unzip -d /opt/gradle /tmp/gradle-${GRADLE_VERSION}-bin.zip \
    && rm /tmp/gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME=/opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH=$GRADLE_HOME/bin:$PATH

# Create restricted user without sudo access
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME \
    && mkdir -p /home/$USERNAME/workspace \
    && chown -R $USERNAME:$USERNAME /home/$USERNAME

# Remove dangerous tools after installation is complete
RUN rm -f /usr/bin/wget /usr/bin/curl /bin/wget /bin/curl \
    && rm -f /usr/bin/apt /usr/bin/apt-get /usr/bin/dpkg \
    && rm -f /usr/bin/snap /usr/bin/systemctl \
    && rm -rf /var/lib/apt/lists/*

# Create restricted PATH for student user
RUN echo '#!/bin/bash' > /usr/local/bin/restricted-shell \
    && echo 'export PATH=/usr/lib/jvm/temurin-21-jdk-amd64/bin:/opt/gradle/gradle-8.5/bin:/usr/bin:/bin:/usr/local/bin' >> /usr/local/bin/restricted-shell \
    && echo 'export SHELL=/bin/bash' >> /usr/local/bin/restricted-shell \
    && echo 'cd /home/student/workspace' >> /usr/local/bin/restricted-shell \
    && echo 'exec /bin/bash "$@"' >> /usr/local/bin/restricted-shell \
    && chmod +x /usr/local/bin/restricted-shell

# Install VS Code Server as root before switching to student user
RUN curl -fsSL https://code-server.dev/install.sh | sh

# Switch to student user
USER $USERNAME
WORKDIR /home/$USERNAME

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
RUN code-server --install-extension k--kato.intellij-idea-keybindings || echo "Failed to install IntelliJ IDEA Keybindings, continuing..." \
    && code-server --install-extension ahmedradwan.shift-shift || echo "Failed to install Shift Shift, continuing..." \
    && code-server --install-extension eamodio.gitlens || echo "Failed to install GitLens, continuing..." \
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

# Set proper permissions for workspace
RUN chmod 755 /home/$USERNAME/workspace \
    && find /home/$USERNAME -type d -exec chmod 755 {} \; \
    && find /home/$USERNAME -type f -exec chmod 644 {} \;

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
CMD ["sh", "-c", "export PATH=/usr/lib/jvm/temurin-21-jdk-amd64/bin:/opt/gradle/gradle-8.5/bin:/usr/bin:/bin:/usr/local/bin && code-server --bind-addr 0.0.0.0:8080 --auth none /home/student/workspace"]