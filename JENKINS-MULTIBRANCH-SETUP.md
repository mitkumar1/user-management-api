# Jenkins Multi-branch Pipeline Setup

## Problem
Your Jenkins job only builds the `master` branch, but you need it to build feature branches like `feature/profile-enhancements` for proper CI/CD workflow.

## Solution: Multi-branch Pipeline

### Step 1: Create Multi-branch Pipeline Job

1. **Navigate to Jenkins**: `http://localhost:8081/`
2. **Create New Item**:
   - Click "New Item"
   - Name: `user-management-api-multibranch`
   - Type: "Multibranch Pipeline"
   - Click "OK"

### Step 2: Configure Branch Sources

1. **Branch Sources**:
   - Click "Add source" → "Git"
   - **Repository URL**: `https://github.com/mitkumar1/user-management-api.git`
   - **Credentials**: Add your GitHub Personal Access Token

2. **Behaviors**:
   - Add "Discover branches"
   - Strategy: "All branches" or "Only branches that are also filed as PRs"

3. **Property Strategy**:
   - Select "All branches get the same properties"

### Step 3: Build Configuration

1. **Build Configuration**:
   - Mode: "by Jenkinsfile"
   - Script Path: `Jenkinsfile` (default)

2. **Scan Multibranch Pipeline Triggers**:
   - ✅ "Periodically if not otherwise run"
   - Interval: "1 minute" (for development/testing)

### Step 4: Save and Scan

1. Click "Save"
2. Jenkins will automatically scan for branches
3. You should see both `master` and `feature/profile-enhancements` branches

## Alternative: Update Existing Job

If you prefer to modify your existing job instead:

1. **Go to**: `http://localhost:8081/job/user-management-api/configure`
2. **Source Code Management** → Git:
   - **Branches to build**: Change from `*/master` to `*/*`
3. **Build Triggers**:
   - ✅ "GitHub hook trigger for GITScm polling"
   - ✅ "Poll SCM" with schedule: `H/5 * * * *` (every 5 minutes)

## Expected Behavior After Setup

### ✅ What Should Happen:
- New feature branches automatically appear in Jenkins
- Each push to any branch triggers a build
- PR status checks work properly
- Parallel builds for different branches

### 🏗️ Build Results:
- `master` branch builds run deployment pipeline
- Feature branches run tests and validation only
- Failed builds block PR merging (with proper GitHub integration)

## Branch-Specific Pipeline Behavior

Your current `Jenkinsfile` supports different behavior per branch:

```groovy
// Example branch-specific logic in Jenkinsfile
when {
    branch 'master'
}
// Only deploy from master

when {
    anyOf {
        branch 'master'
        branch 'feature/*'
    }
}
// Run tests on master and feature branches
```

## GitHub Integration

For complete CI/CD workflow:

1. **GitHub Webhook**: 
   - Repository Settings → Webhooks
   - Add: `http://your-jenkins-url/github-webhook/`

2. **Status Checks**:
   - Jenkins will report build status to GitHub
   - PR merge blocked if builds fail
   - Green checkmarks when builds pass

## Troubleshooting

### Branch Not Appearing?
1. Check repository URL and credentials
2. Verify branch exists in remote repository
3. Manually trigger "Scan Multibranch Pipeline Now"

### Builds Not Triggering?
1. Check GitHub webhook configuration
2. Verify polling is enabled
3. Check Jenkins logs for errors

### Permission Issues?
1. Ensure GitHub token has repository access
2. Check Jenkins user permissions
3. Verify firewall/network connectivity

## Testing Your Setup

1. **Create a test commit** on your feature branch:
   ```bash
   git checkout feature/profile-enhancements
   echo "# Test" >> test-jenkins.md
   git add test-jenkins.md
   git commit -m "test: trigger Jenkins build"
   git push origin feature/profile-enhancements
   ```

2. **Check Jenkins**: Should automatically detect and build the branch

3. **Verify GitHub**: Should show build status on the commit

## Benefits of Multi-branch Pipeline

✅ **Automatic branch detection**
✅ **Parallel builds for multiple branches**
✅ **Branch-specific build strategies**
✅ **Proper PR status integration**
✅ **Clean separation of deployment vs testing**

Your workflow is now: Feature Branch → Jenkins Build → GitHub Status → PR Review → Merge
