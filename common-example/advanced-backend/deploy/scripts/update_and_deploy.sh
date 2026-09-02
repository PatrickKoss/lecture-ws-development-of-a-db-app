#!/bin/bash
# Enhanced deployment script with repository update tracking

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DEPLOY_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Default values
INVENTORY_FILE="$DEPLOY_DIR/inventory"
PLAYBOOK="$DEPLOY_DIR/playbooks/deploy-student-env.yml"
VARS_FILE=""
CREDENTIALS_FILE=""
DRY_RUN=false
VERBOSE=""
DNS01=false
CF_TOKEN=""
CF_TOKEN_FILE=""
ENABLE_SSL_FLAG=""

# Function to display usage
usage() {
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  -i, --inventory FILE     Specify inventory file (default: $INVENTORY_FILE)"
    echo "  -e, --vars FILE         Specify additional vars file"
    echo "  -c, --credentials FILE  Specify credentials file"
    echo "  -n, --dry-run          Run in check mode (no changes made)"
    echo "  -v, --verbose          Enable verbose output"
    echo "  -h, --help             Display this help message"
    echo ""
    echo "DNS-01 (Cloudflare) options:"
    echo "  --dns-01               Enable DNS-01 wildcard issuance"
    echo "  --cf-token TOKEN       Cloudflare API token (Zone.DNS:Edit)"
    echo "  --cf-token-file FILE   Read Cloudflare API token from file"
    echo "  --enable-ssl           Force enable_ssl=true via extra-vars"
    echo ""
    echo "Examples:"
    echo "  $0                                    # Basic deployment"
    echo "  $0 -n                                # Dry run"
    echo "  $0 -e vars/production.yml            # With custom vars"
    echo "  $0 -c ./my-credentials.json          # With custom credentials"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -i|--inventory)
            INVENTORY_FILE="$2"
            shift 2
            ;;
        -e|--vars)
            VARS_FILE="$2"
            shift 2
            ;;
        -c|--credentials)
            CREDENTIALS_FILE="$2"
            shift 2
            ;;
        -n|--dry-run)
            DRY_RUN=true
            shift
            ;;
        -v|--verbose)
            VERBOSE="-vvv"
            shift
            ;;
        -h|--help)
            usage
            exit 0
            ;;
        --dns-01)
            DNS01=true
            shift
            ;;
        --cf-token)
            CF_TOKEN="$2"
            shift 2
            ;;
        --cf-token-file)
            CF_TOKEN_FILE="$2"
            shift 2
            ;;
        --enable-ssl)
            ENABLE_SSL_FLAG="-e enable_ssl=true"
            shift
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            usage
            exit 1
            ;;
    esac
done

# Check if required files exist
if [[ ! -f "$INVENTORY_FILE" ]]; then
    echo -e "${RED}Error: Inventory file not found: $INVENTORY_FILE${NC}"
    exit 1
fi

if [[ ! -f "$PLAYBOOK" ]]; then
    echo -e "${RED}Error: Playbook not found: $PLAYBOOK${NC}"
    exit 1
fi

# Build ansible-playbook command
ANSIBLE_CMD="ansible-playbook -i \"$INVENTORY_FILE\" \"$PLAYBOOK\" $VERBOSE"

if [[ "$DRY_RUN" == true ]]; then
    ANSIBLE_CMD="$ANSIBLE_CMD --check --diff"
    echo -e "${YELLOW}Running in DRY RUN mode - no changes will be made${NC}"
fi

if [[ -n "$VARS_FILE" ]]; then
    if [[ ! -f "$VARS_FILE" ]]; then
        echo -e "${RED}Error: Vars file not found: $VARS_FILE${NC}"
        exit 1
    fi
    ANSIBLE_CMD="$ANSIBLE_CMD -e @\"$VARS_FILE\""
fi

if [[ -n "$CREDENTIALS_FILE" ]]; then
    if [[ ! -f "$CREDENTIALS_FILE" ]]; then
        echo -e "${RED}Error: Credentials file not found: $CREDENTIALS_FILE${NC}"
        exit 1
    fi
    ANSIBLE_CMD="$ANSIBLE_CMD -e credentials_file=\"$CREDENTIALS_FILE\""
fi

# Handle DNS-01 options
if [[ "$DNS01" == true ]]; then
    ANSIBLE_CMD="$ANSIBLE_CMD -e ssl_use_dns_challenge=true"
    # Prefer explicit token options, else environment fallback
    if [[ -n "$CF_TOKEN_FILE" ]]; then
        if [[ ! -f "$CF_TOKEN_FILE" ]]; then
            echo -e "${RED}Error: Cloudflare token file not found: $CF_TOKEN_FILE${NC}"
            exit 1
        fi
        CF_TOKEN_CONTENT=$(cat "$CF_TOKEN_FILE")
        ANSIBLE_CMD="$ANSIBLE_CMD -e cloudflare_api_token='${CF_TOKEN_CONTENT}'"
    elif [[ -n "$CF_TOKEN" ]]; then
        ANSIBLE_CMD="$ANSIBLE_CMD -e cloudflare_api_token='${CF_TOKEN}'"
    elif [[ -n "$CF_API_TOKEN" ]]; then
        ANSIBLE_CMD="$ANSIBLE_CMD -e cloudflare_api_token='${CF_API_TOKEN}'"
    else
        echo -e "${YELLOW}Warning: DNS-01 enabled but no Cloudflare token provided. Set --cf-token, --cf-token-file, or CF_API_TOKEN env.${NC}"
    fi
fi

# Optional enable_ssl flag
if [[ -n "$ENABLE_SSL_FLAG" ]]; then
    ANSIBLE_CMD="$ANSIBLE_CMD $ENABLE_SSL_FLAG"
fi

# Display deployment information
echo -e "${GREEN}=== Student Environment Deployment ===${NC}"
echo "Inventory: $INVENTORY_FILE"
echo "Playbook: $PLAYBOOK"
[[ -n "$VARS_FILE" ]] && echo "Vars file: $VARS_FILE"
[[ -n "$CREDENTIALS_FILE" ]] && echo "Credentials: $CREDENTIALS_FILE"
[[ "$DNS01" == true ]] && echo "DNS-01: ENABLED"
[[ -n "$CF_TOKEN_FILE" ]] && echo "Cloudflare token file: $CF_TOKEN_FILE"
[[ -n "$CF_TOKEN" ]] && echo "Cloudflare token: (provided via CLI)"
[[ -n "$CF_API_TOKEN" ]] && echo "Cloudflare token: (provided via env CF_API_TOKEN)"
echo "Mode: $([ "$DRY_RUN" == true ] && echo "DRY RUN" || echo "LIVE DEPLOYMENT")"
echo ""

# Confirm deployment (skip for dry run)
if [[ "$DRY_RUN" != true ]]; then
    read -p "Proceed with deployment? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "Deployment cancelled."
        exit 0
    fi
fi

# Change to deploy directory
cd "$DEPLOY_DIR"

# Run the deployment
echo -e "${GREEN}Starting deployment...${NC}"
eval $ANSIBLE_CMD

# Check exit status
if [[ $? -eq 0 ]]; then
    echo -e "${GREEN}Deployment completed successfully!${NC}"
    
    # Display post-deployment information
    echo ""
    echo -e "${GREEN}=== Deployment Summary ===${NC}"
    echo "- Repository: https://github.com/PatrickKoss/lecture-ws-development-of-a-db-app.git"
    echo "- Student environments: 23"
    echo "- VSCode URLs: student[1-23].vscode.patrick-koss.de"
    echo "- API URLs: student[1-23].api.patrick-koss.de"
    echo ""
    echo "Check the generated credentials file for student access information."
else
    echo -e "${RED}Deployment failed!${NC}"
    exit 1
fi
