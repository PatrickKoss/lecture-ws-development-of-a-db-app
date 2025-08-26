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

# Display deployment information
echo -e "${GREEN}=== Student Environment Deployment ===${NC}"
echo "Inventory: $INVENTORY_FILE"
echo "Playbook: $PLAYBOOK"
[[ -n "$VARS_FILE" ]] && echo "Vars file: $VARS_FILE"
[[ -n "$CREDENTIALS_FILE" ]] && echo "Credentials: $CREDENTIALS_FILE"
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