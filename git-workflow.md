# Git Workflow Guide

## Feature Branch Workflow

### 1. Create a New Feature Branch
```bash
# Always start from the latest master
git checkout master
git pull origin master

# Create and switch to a new feature branch
git checkout -b feature/your-feature-name

# Push the branch to remote
git push -u origin feature/your-feature-name
```

### 2. Work on Your Feature
```bash
# Make your changes
# ... edit files ...

# Stage and commit changes
git add .
git commit -m "feat: add your feature description"

# Push changes to feature branch
git push origin feature/your-feature-name
```

### 3. Create Pull Request
1. Go to GitHub: https://github.com/mitkumar1/user-management-api
2. Click "Compare & pull request" for your feature branch
3. Fill in PR description
4. Request review from team members
5. Wait for approval before merging

### 4. After PR Approval
```bash
# Switch back to master
git checkout master

# Pull the merged changes
git pull origin master

# Delete the merged feature branch (optional)
git branch -d feature/your-feature-name
git push origin --delete feature/your-feature-name
```

## Branch Protection Setup

To enforce PR approval requirements, configure these settings on GitHub:

1. Go to: Settings → Branches → Add rule
2. Branch name pattern: `master`
3. Enable: "Require a pull request before merging"
4. Enable: "Require approvals" (set to 1)
5. Enable: "Dismiss stale PR approvals when new commits are pushed"
6. Enable: "Require status checks to pass before merging"

## Quick Commands

### Switch between branches
```bash
git checkout master                    # Switch to master
git checkout feature/your-branch       # Switch to feature branch
git branch -a                          # List all branches
```

### Sync with remote
```bash
git fetch origin                       # Fetch latest from remote
git pull origin master                 # Pull latest master
git push origin feature/your-branch    # Push your feature branch
```

### Clean up merged branches
```bash
git branch --merged master             # List merged branches
git branch -d feature/old-branch       # Delete local merged branch
git push origin --delete feature/old-branch  # Delete remote branch
```
