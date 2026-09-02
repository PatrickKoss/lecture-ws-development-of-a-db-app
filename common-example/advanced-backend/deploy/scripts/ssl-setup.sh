#!/bin/bash
# SSL Setup and Management Script for Student Environment

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DEPLOY_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
DOMAIN="patrick-koss.de"
STUDENT_COUNT=23
CERT_PATH="/etc/letsencrypt/live/student1.vscode.${DOMAIN}"

# Functions
usage() {
    echo "SSL Management Script for Student Environment"
    echo ""
    echo "Usage: $0 [COMMAND] [OPTIONS]"
    echo ""
    echo "Commands:"
    echo "  setup EMAIL        Initial SSL setup with Let's Encrypt"
    echo "  status             Show certificate status and expiry"
    echo "  renew              Force certificate renewal"
    echo "  test               Test SSL configuration for all domains"
    echo "  info               Show certificate information"
    echo ""
    echo "Examples:"
    echo "  $0 setup admin@patrick-koss.de     # Initial setup"
    echo "  $0 status                          # Check status"
    echo "  $0 test                           # Test all domains"
}

log() {
    echo -e "${GREEN}[$(date '+%Y-%m-%d %H:%M:%S')] $1${NC}"
}

warn() {
    echo -e "${YELLOW}[$(date '+%Y-%m-%d %H:%M:%S')] WARNING: $1${NC}"
}

error() {
    echo -e "${RED}[$(date '+%Y-%m-%d %H:%M:%S')] ERROR: $1${NC}"
    exit 1
}

check_root() {
    if [[ $EUID -ne 0 ]]; then
        error "This script must be run as root (use sudo)"
    fi
}

setup_ssl() {
    local email="$1"
    
    if [[ -z "$email" ]]; then
        error "Email address is required for SSL setup"
    fi
    
    log "Setting up SSL certificates for $STUDENT_COUNT students..."
    
    # Build domain list
    local domains=""
    for i in $(seq 1 $STUDENT_COUNT); do
        domains+=" -d student${i}.vscode.${DOMAIN}"
        domains+=" -d student${i}.api.${DOMAIN}"
    done
    
    # Check if certificate already exists
    if [[ -f "${CERT_PATH}/fullchain.pem" ]]; then
        warn "Certificate already exists at ${CERT_PATH}"
        read -p "Do you want to renew it? (y/N): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            log "SSL setup cancelled"
            return 0
        fi
    fi
    
    log "Generating certificate for domains..."
    log "This may take a few minutes..."
    
    # Generate certificate
    if certbot certonly --nginx --agree-tos --email "$email" --non-interactive $domains; then
        log "SSL certificate generated successfully!"
        log "Certificate path: ${CERT_PATH}/fullchain.pem"
        log "Private key path: ${CERT_PATH}/privkey.pem"
        
        # Set up renewal cron job
        setup_renewal_cron
        
        log "SSL setup complete!"
        log "Don't forget to deploy with SSL enabled: ./scripts/update_and_deploy.sh -e vars/ssl.yml"
    else
        error "Failed to generate SSL certificate"
    fi
}

setup_renewal_cron() {
    local cron_job="0 2 * * * /usr/bin/certbot renew --quiet && systemctl reload nginx"
    
    # Check if cron job already exists
    if crontab -l 2>/dev/null | grep -q "certbot renew"; then
        log "Certificate renewal cron job already exists"
    else
        # Add cron job
        (crontab -l 2>/dev/null; echo "$cron_job") | crontab -
        log "Added automatic certificate renewal (daily at 2 AM)"
    fi
}

show_status() {
    log "SSL Certificate Status"
    echo "===================="
    
    if [[ ! -f "${CERT_PATH}/fullchain.pem" ]]; then
        warn "No SSL certificate found at ${CERT_PATH}"
        return 1
    fi
    
    # Certificate information
    local expiry=$(openssl x509 -in "${CERT_PATH}/fullchain.pem" -text -noout | grep "Not After" | cut -d: -f2-)
    local days_left=$(( ($(date -d "$expiry" +%s) - $(date +%s)) / 86400 ))
    
    echo "Certificate Path: ${CERT_PATH}"
    echo "Expires: $expiry"
    
    if [[ $days_left -lt 30 ]]; then
        warn "Certificate expires in $days_left days!"
    elif [[ $days_left -lt 60 ]]; then
        echo -e "${YELLOW}Certificate expires in $days_left days${NC}"
    else
        echo -e "${GREEN}Certificate expires in $days_left days${NC}"
    fi
    
    # List all domains in certificate
    echo ""
    echo "Certificate covers the following domains:"
    openssl x509 -in "${CERT_PATH}/fullchain.pem" -text -noout | grep -A 1 "Subject Alternative Name" | tail -n 1 | tr ',' '\n' | sed 's/.*DNS://g' | sort
}

renew_certificate() {
    log "Renewing SSL certificate..."
    
    if certbot renew --force-renewal; then
        systemctl reload nginx
        log "Certificate renewed successfully!"
        show_status
    else
        error "Failed to renew certificate"
    fi
}

test_domains() {
    log "Testing SSL configuration for all student domains..."
    
    local failed_domains=()
    local success_count=0
    
    for i in $(seq 1 $STUDENT_COUNT); do
        echo -n "Testing student$i.vscode.${DOMAIN}... "
        if curl -I "https://student$i.vscode.${DOMAIN}" --connect-timeout 5 --max-time 10 -s >/dev/null 2>&1; then
            echo -e "${GREEN}OK${NC}"
            ((success_count++))
        else
            echo -e "${RED}FAILED${NC}"
            failed_domains+=("student$i.vscode.${DOMAIN}")
        fi
        
        echo -n "Testing student$i.api.${DOMAIN}... "
        if curl -I "https://student$i.api.${DOMAIN}" --connect-timeout 5 --max-time 10 -s >/dev/null 2>&1; then
            echo -e "${GREEN}OK${NC}"
            ((success_count++))
        else
            echo -e "${RED}FAILED${NC}"
            failed_domains+=("student$i.api.${DOMAIN}")
        fi
    done
    
    echo ""
    echo "Test Results:"
    echo "============="
    echo "Successful: $success_count/$(($STUDENT_COUNT * 2)) domains"
    
    if [[ ${#failed_domains[@]} -gt 0 ]]; then
        warn "Failed domains:"
        printf '%s\n' "${failed_domains[@]}"
    else
        log "All domains are working correctly!"
    fi
}

show_info() {
    log "SSL Certificate Information"
    
    if [[ ! -f "${CERT_PATH}/fullchain.pem" ]]; then
        warn "No SSL certificate found"
        return 1
    fi
    
    certbot certificates
    echo ""
    echo "Certificate Details:"
    openssl x509 -in "${CERT_PATH}/fullchain.pem" -text -noout | head -20
}

# Main script logic
case "${1:-}" in
    setup)
        check_root
        setup_ssl "$2"
        ;;
    status)
        show_status
        ;;
    renew)
        check_root
        renew_certificate
        ;;
    test)
        test_domains
        ;;
    info)
        show_info
        ;;
    *)
        usage
        exit 1
        ;;
esac