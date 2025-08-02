# GitHub Branch Protection Configuration Guide

## Quick Setup Checklist

### ✅ New Branch Ruleset Configuration

**Basic Information:**
- Ruleset Name: `Protect Master Branch`
- Enforcement status: `Active`
- Bypass list: `Empty` (or add specific admins if needed)

**Target Branches:**
```
✅ Include default branch
OR
✅ Add branch: master
```

**Rules to Enable:**

### 🛡️ Core Protection Rules

#### 1. Require a pull request before merging ✅
```
✅ Required number of approvals: 1
✅ Dismiss stale PR approvals when new commits are pushed
✅ Require review from code owners (if applicable)
✅ Require conversation resolution before merging
```

#### 2. Restrict updates ✅
```
✅ Only allow users with bypass permission to update matching refs
```

#### 3. Block force pushes ✅
```
✅ Prevent users with push access from force pushing
```

#### 4. Require status checks to pass ✅
```
✅ Choose status checks: validate
✅ Require branches to be up to date before merging
```

### 🔧 Optional but Recommended

#### 5. Restrict deletions ✅
```
✅ Only allow users with bypass permissions to delete matching refs
```

#### 6. Require linear history ⚡ (Optional)
```
✅ Prevent merge commits from being pushed to matching refs
Note: This requires rebasing instead of merge commits
```

### ❌ Leave Disabled (for most projects)

- ❌ Restrict creations
- ❌ Require deployments to succeed (unless you have deployment pipelines)
- ❌ Require signed commits (unless you need cryptographic verification)
- ❌ Require code scanning results (unless using GitHub Advanced Security)

## Expected Behavior After Setup

### ✅ What Will Happen:
- ✅ Direct pushes to master will be blocked
- ✅ All changes must go through pull requests
- ✅ PRs require 1 approval before merging
- ✅ GitHub Actions must pass before merging
- ✅ Force pushes to master are prevented
- ✅ Master branch cannot be deleted

### ❌ What Will Be Blocked:
- ❌ `git push origin master` (direct push)
- ❌ `git push --force origin master` (force push)
- ❌ Merging PRs without approval
- ❌ Merging PRs with failing status checks

## Testing Your Setup

After configuration, test with these commands:

```bash
# This should be BLOCKED
git checkout master
echo "test" >> README.md
git add README.md
git commit -m "test direct push"
git push origin master
# Expected: remote: error: GH006: Protected branch update failed

# This should WORK
git checkout -b test/protection-check
git push origin test/protection-check
# Then create PR via GitHub UI
```

## Workflow Integration

Your `git-workflow.ps1` script will help developers work within these constraints:

```powershell
# Create feature branch (will work)
.\git-workflow.ps1 new-feature my-change

# Try to push to master (will be blocked)
git checkout master
git push origin master  # ❌ Blocked by rules

# Proper workflow (will work)
git checkout feature/my-change
.\git-workflow.ps1 push  # ✅ Allowed
# Create PR via GitHub UI
```

## Troubleshooting

### If PRs are blocked:
1. Check that GitHub Actions workflow is passing
2. Ensure required approvals are met
3. Verify all conversations are resolved

### If direct pushes still work:
1. Verify ruleset is `Active`
2. Check that `master` branch is targeted
3. Confirm `Restrict updates` is enabled

### For emergency access:
1. Add yourself to bypass list temporarily
2. Make critical fixes
3. Remove from bypass list afterward

## Success Indicators

✅ Repository Settings → Rules shows active ruleset
✅ Direct pushes to master are rejected
✅ PRs show required status checks
✅ Merge button is disabled until approval
✅ Branch deletion attempts are blocked
