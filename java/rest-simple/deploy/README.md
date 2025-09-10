# Student Development Environment Deployment

This Ansible deployment sets up individual development environments for students with VSCode Server and Java Spring Boot applications accessible via domain-based routing with basic authentication.

## Architecture

- **23 Student Environments**: Each student gets isolated VSCode and API access
- **Repository**: Automatically clones/updates from https://github.com/PatrickKoss/lecture-ws-development-of-a-db-app.git
- **Domain Structure**:
  - VSCode: `student[1-23].vscode.patrick-koss.de`
  - API: `student[1-23].api.patrick-koss.de`
- **Port Allocation**: Each student gets unique ports (student1: 8080/8081, student2: 8090/8091, etc.)
- **Authentication**: Individual username/password per student via nginx basic auth
- **Smart Updates**: Automatically rebuilds and restarts containers when repository changes are detected

## Prerequisites

### Control Machine (where you run Ansible)

```bash
# Install Ansible
sudo apt update && sudo apt install ansible

# Or via pip
pip3 install ansible

# Install required collections
ansible-galaxy collection install community.docker
ansible-galaxy collection install community.general
```

### Target Server Requirements

- Ubuntu 20.04+ or Debian 10+
- SSH access with sudo privileges
- Minimum 8GB RAM, 50GB storage
- DNS records configured for `*.vscode.patrick-koss.de` and `*.api.patrick-koss.de`

## Quick Start

### 1. Configure Inventory

Edit `deploy/inventory` and add your target server:

```ini
[student_servers]
myserver ansible_host=192.168.1.100 ansible_user=ubuntu ansible_ssh_private_key_file=~/.ssh/id_rsa
```

### 2. Configure Variables

Edit `deploy/vars/main.yml` to customize:

```yaml
domain_name: "patrick-koss.de"
student_count: 23
repository_url: "https://github.com/your-username/your-repo.git" # Optional
```

### 3. Deploy

```bash
cd deploy/

# Deploy everything (recommended - uses helper script)
./scripts/update_and_deploy.sh

# Deploy with dry run (check what would be changed)
./scripts/update_and_deploy.sh --dry-run

# Deploy with custom credentials file
./scripts/update_and_deploy.sh -c ./my-credentials.json

# Deploy to specific environment
./scripts/update_and_deploy.sh -e vars/production.yml

# Manual deployment (advanced)
ansible-playbook -i inventory playbooks/deploy-student-env.yml
```

## Credential Management

### Option 1: Auto-Generate Credentials

If no credentials file is provided, the playbook will automatically generate secure passwords and save them to `student-credentials.json`.

### Option 2: Pre-Generate Credentials

```bash
cd deploy/scripts/
./generate_credentials.py --count=23 --output=../student-credentials.json
```

### Option 3: Provide Existing Credentials

Create a JSON file with the following format:

```json
{
  "generated_at": "2024-01-01T12:00:00",
  "student_count": 23,
  "credentials": [
    { "username": "student1", "password": "secure_password_1" },
    { "username": "student2", "password": "secure_password_2" }
  ]
}
```

## Access URLs

After deployment, students can access:

- **VSCode**: `http://student[1-23].vscode.patrick-koss.de` (or `https://` if SSL enabled)
- **API**: `http://student[1-23].api.patrick-koss.de` (or `https://` if SSL enabled)

Each student uses their individual username/password for both VSCode and API access.

## Running Spring Boot Application

Students can start their Spring Boot application inside VSCode:

```bash
# Using make (recommended)
make run

# Using gradle directly
SERVER_PORT=8081 ./gradlew bootRun

# Using custom port
SERVER_PORT=8082 ./gradlew bootRun
```

The application will be accessible at their API domain (e.g., `https://student1.api.patrick-koss.de`).

## File Structure

```
deploy/
├── inventory                    # Ansible inventory file
├── playbooks/
│   └── deploy-student-env.yml  # Main deployment playbook
├── scripts/
│   ├── generate_credentials.py # Credential generation script
│   └── manage_credentials.yml  # Credential management tasks
├── templates/
│   ├── docker-compose.yml.j2  # Docker Compose template
│   └── nginx.conf.j2          # Nginx configuration template
├── vars/
│   └── main.yml               # Main configuration variables
└── README.md                  # This file
```

## Customization

### Add Environment-Specific Variables

Create files like `vars/production.yml`:

```yaml
domain_name: "prod.patrick-koss.de"
student_count: 30
enable_ssl: true
```

Deploy with:

```bash
ansible-playbook -i inventory playbooks/deploy-student-env.yml -e @vars/production.yml
```

### Modify Port Allocation

Edit `vars/main.yml`:

```yaml
base_vscode_port: 9000
base_api_port: 9001
port_increment: 100 # student1: 9000/9001, student2: 9100/9101
```

### Enable SSL/TLS with Let's Encrypt

1. **Configure SSL Variables**:

   ```bash
   # Copy and edit SSL configuration
   cp vars/ssl.yml vars/production-ssl.yml
   # Edit vars/production-ssl.yml and set your email
   ```

2. **Deploy with SSL**:

   ```bash
   ./scripts/update_and_deploy.sh -e vars/production-ssl.yml
   ```

3. **Or edit `vars/main.yml` directly**:
   ```yaml
   enable_ssl: true
   ssl_email: "your-email@patrick-koss.de" # Required for Let's Encrypt
   ```

**Important SSL Notes**:

- Uses **single SAN certificate** for all 46 student domains (efficient!)
- **HTTP challenge** only - no DNS provider required
- **Automatic renewal** via cron (daily at 2 AM)
- **A+ SSL rating** with modern TLS configuration
- Certificates valid for 90 days, renewed automatically at 60 days

## Repository Updates and Smart Rebuilds

The system automatically handles repository updates:

### How It Works

1. **First Run**: Clones repository for each student
2. **Subsequent Runs**:
   - Checks current commit hash
   - Pulls latest changes
   - Compares commit hashes
   - Rebuilds and restarts only changed containers

### Manual Repository Update

```bash
# Update repositories and rebuild affected containers
./scripts/update_and_deploy.sh

# Check what would be updated (dry run)
./scripts/update_and_deploy.sh --dry-run
```

### Force Rebuild All Containers

```bash
cd /opt/student-env
docker-compose down
docker image rm student-vscode:latest
./scripts/update_and_deploy.sh
```

## SSL Certificate Management

### Check Certificate Status

```bash
# Check certificate details
sudo certbot certificates

# Check certificate expiry
sudo openssl x509 -in /etc/letsencrypt/live/student1.vscode.patrick-koss.de/fullchain.pem -text -noout | grep "Not After"

# Test renewal (dry run)
sudo certbot renew --dry-run
```

### Manual Certificate Operations

```bash
# Force renewal (if needed)
sudo certbot renew --force-renewal

# Revoke certificate (emergency only)
sudo certbot revoke --cert-path /etc/letsencrypt/live/student1.vscode.patrick-koss.de/fullchain.pem

# Test SSL configuration
curl -I https://student1.vscode.patrick-koss.de
```

### SSL Troubleshooting

```bash
# Check nginx SSL configuration
sudo nginx -t

# View SSL logs
sudo tail -f /var/log/letsencrypt/letsencrypt.log

# Check certificate validity for all domains
for i in {1..23}; do
  echo "Testing student$i vscode..."
  curl -I https://student$i.vscode.patrick-koss.de 2>/dev/null | head -1
  echo "Testing student$i api..."
  curl -I https://student$i.api.patrick-koss.de 2>/dev/null | head -1
done
```

## Troubleshooting

### Check Container Status

```bash
ssh user@server
cd /opt/student-env
docker-compose ps
```

### View Repository Status

```bash
# Check which students have which commit
for i in {1..23}; do
  echo "Student $i: $(git -C /opt/student-env/students/student$i rev-parse --short HEAD)"
done
```

### View Logs

```bash
# Nginx logs
sudo tail -f /var/log/nginx/student-access.log
sudo tail -f /var/log/nginx/student-error.log

# Container logs
docker-compose logs student1-vscode

# Follow logs for specific student
docker-compose logs -f student1-vscode
```

### Restart Services

```bash
# Restart all containers
docker-compose restart

# Restart specific student
docker-compose restart student1-vscode

# Restart nginx
sudo systemctl restart nginx
```

### Regenerate Credentials

```bash
rm student-credentials.json
./scripts/update_and_deploy.sh
```

### Access Container Shell

```bash
docker exec -it student1-vscode /bin/bash
```

### Debug Repository Issues

```bash
# Check git status for a student
git -C /opt/student-env/students/student1 status

# Manually pull updates for a student
git -C /opt/student-env/students/student1 pull origin main

# Reset student repository if corrupted
rm -rf /opt/student-env/students/student1
git clone https://github.com/PatrickKoss/lecture-ws-development-of-a-db-app.git /opt/student-env/students/student1
chown -R 1000:1000 /opt/student-env/students/student1
```

## Security Considerations

- Each student has isolated environment with basic auth
- Containers run as non-root user (UID 1000)
- Nginx provides additional security headers
- Consider enabling SSL/TLS for production use
- Firewall should only allow HTTP/HTTPS and SSH access

## Scaling

To add more students:

1. Update `student_count` in `vars/main.yml`
2. Regenerate credentials if needed
3. Run the playbook again

The system will automatically create additional containers and nginx configurations.

## Backup and Maintenance

### Backup Student Data

```bash
# Backup all student workspaces
tar -czf student-backup-$(date +%Y%m%d).tar.gz /opt/student-env/students/

# Backup credentials
cp student-credentials.json student-credentials-backup-$(date +%Y%m%d).json
```

### Update Application

```bash
# Rebuild and restart containers
cd /opt/student-env
docker-compose build --no-cache
docker-compose up -d
```
