# Git Workflow Helper Script
# Usage: .\git-workflow.ps1 [command]

param(
    [Parameter(Position=0)]
    [string]$Command,
    
    [Parameter(Position=1)]
    [string]$BranchName
)

function Show-Usage {
    Write-Host "Git Workflow Helper" -ForegroundColor Green
    Write-Host "Usage: .\git-workflow.ps1 [command] [branch-name]" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Commands:" -ForegroundColor Cyan
    Write-Host "  new-feature [name]    - Create new feature branch"
    Write-Host "  sync                  - Sync with master"
    Write-Host "  push                  - Push current branch"
    Write-Host "  status                - Show git status"
    Write-Host "  branches              - List all branches"
    Write-Host "  cleanup               - Clean up merged branches"
    Write-Host "  pr-ready              - Check if ready for PR"
    Write-Host ""
}

function New-FeatureBranch {
    param([string]$Name)
    
    if (-not $Name) {
        Write-Host "Please provide a branch name: .\git-workflow.ps1 new-feature my-feature" -ForegroundColor Red
        return
    }
    
    $branchName = "feature/$Name"
    Write-Host "Creating feature branch: $branchName" -ForegroundColor Green
    
    git checkout master
    git pull origin master
    git checkout -b $branchName
    git push -u origin $branchName
    
    Write-Host "✅ Feature branch '$branchName' created and pushed!" -ForegroundColor Green
    Write-Host "You can now start working on your feature." -ForegroundColor Yellow
}

function Sync-WithMaster {
    Write-Host "Syncing with master..." -ForegroundColor Green
    git fetch origin
    git checkout master
    git pull origin master
    Write-Host "✅ Synced with master!" -ForegroundColor Green
}

function Push-CurrentBranch {
    $currentBranch = git branch --show-current
    Write-Host "Pushing current branch: $currentBranch" -ForegroundColor Green
    git push origin $currentBranch
    Write-Host "✅ Pushed '$currentBranch'!" -ForegroundColor Green
}

function Show-Status {
    Write-Host "Git Status:" -ForegroundColor Green
    git status
    Write-Host ""
    Write-Host "Current Branch:" -ForegroundColor Cyan
    git branch --show-current
}

function Show-Branches {
    Write-Host "All Branches:" -ForegroundColor Green
    git branch -a
}

function Cleanup-MergedBranches {
    Write-Host "Cleaning up merged branches..." -ForegroundColor Green
    
    $mergedBranches = git branch --merged master | Where-Object { $_ -notmatch "master" -and $_ -notmatch "\*" }
    
    if ($mergedBranches) {
        Write-Host "Merged branches to delete:" -ForegroundColor Yellow
        $mergedBranches | ForEach-Object { Write-Host "  $($_.Trim())" }
        
        $confirm = Read-Host "Delete these branches? (y/N)"
        if ($confirm -eq 'y' -or $confirm -eq 'Y') {
            $mergedBranches | ForEach-Object {
                $branch = $_.Trim()
                git branch -d $branch
                git push origin --delete $branch 2>$null
            }
            Write-Host "✅ Cleaned up merged branches!" -ForegroundColor Green
        }
    } else {
        Write-Host "No merged branches to clean up." -ForegroundColor Yellow
    }
}

function Check-PrReady {
    $currentBranch = git branch --show-current
    Write-Host "Checking if '$currentBranch' is ready for PR..." -ForegroundColor Green
    
    # Check if branch is pushed
    $remoteExists = git ls-remote --heads origin $currentBranch
    if (-not $remoteExists) {
        Write-Host "❌ Branch not pushed to remote. Run: git push origin $currentBranch" -ForegroundColor Red
        return
    }
    
    # Check if there are uncommitted changes
    $status = git status --porcelain
    if ($status) {
        Write-Host "❌ You have uncommitted changes. Commit them first." -ForegroundColor Red
        return
    }
    
    # Check if local is up to date with remote
    git fetch origin $currentBranch 2>$null
    $behind = git rev-list --count HEAD..origin/$currentBranch 2>$null
    $ahead = git rev-list --count origin/$currentBranch..HEAD 2>$null
    
    if ($behind -gt 0) {
        Write-Host "❌ Your branch is behind remote. Run: git pull origin $currentBranch" -ForegroundColor Red
        return
    }
    
    if ($ahead -gt 0) {
        Write-Host "❌ You have unpushed commits. Run: git push origin $currentBranch" -ForegroundColor Red
        return
    }
    
    Write-Host "✅ Branch '$currentBranch' is ready for PR!" -ForegroundColor Green
    Write-Host "Create PR at: https://github.com/mitkumar1/user-management-api/compare/master...$currentBranch" -ForegroundColor Cyan
}

# Main script logic
switch ($Command.ToLower()) {
    "new-feature" { New-FeatureBranch -Name $BranchName }
    "sync" { Sync-WithMaster }
    "push" { Push-CurrentBranch }
    "status" { Show-Status }
    "branches" { Show-Branches }
    "cleanup" { Cleanup-MergedBranches }
    "pr-ready" { Check-PrReady }
    default { Show-Usage }
}
