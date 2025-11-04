# Branch Protection Configuration Guide

This document explains how to enable force push and direct push to the main branch in the GitHub repository.

## Understanding Branch Protection

By default, GitHub repositories may have branch protection rules that prevent:
- Force pushes to protected branches
- Direct pushes to protected branches without pull requests
- Deletion of protected branches

## Enabling Force Push and Direct Push to Main

To allow force push and direct push to the main branch, you need to configure the branch protection rules in your GitHub repository settings.

### Step 1: Access Repository Settings

1. Go to your GitHub repository: `https://github.com/panatl/open-receivable`
2. Click on **Settings** (you need admin access to the repository)
3. In the left sidebar, click on **Branches** under "Code and automation"

### Step 2: Configure Branch Protection Rules

#### Option A: Remove Branch Protection (Most Permissive)

If you want completely unrestricted access to the main branch:

1. Find the branch protection rule for `main` (if it exists)
2. Click **Delete** to remove the protection rule
3. Confirm the deletion

This will allow:
- ✅ Direct pushes to main
- ✅ Force pushes to main
- ✅ Branch deletion

#### Option B: Modify Branch Protection (Recommended for Teams)

If you want some protection but still allow force/direct pushes:

1. Click **Add branch protection rule** or edit the existing rule for `main`
2. In "Branch name pattern", enter: `main`
3. Configure the following settings:

   **Allow force pushes:**
   - ✅ Check "Allow force pushes"
   - Optionally, specify who can force push (Everyone or specific roles)

   **Allow direct pushes (without PR):**
   - ❌ Uncheck "Require a pull request before merging"
   - OR check it but also check "Allow specified actors to bypass required pull requests"
   - If using bypass, add the users/teams who can push directly

4. Other recommended settings:
   - ✅ "Require status checks to pass before merging" (optional)
   - ✅ "Require conversation resolution before merging" (optional)
   - ❌ "Require signed commits" (optional)
   - ❌ "Require linear history" (optional if you want to allow force push)

5. Click **Create** or **Save changes**

### Step 3: Verify Configuration

After configuring, you can test by:

```bash
# Clone or navigate to your repository
git clone https://github.com/panatl/open-receivable.git
cd open-receivable

# Switch to main branch
git checkout main

# Try direct push
echo "test" >> test.txt
git add test.txt
git commit -m "Test direct push"
git push origin main

# Try force push (if needed)
git push --force origin main
```

## Important Considerations

### Security Implications

Allowing force push and direct push to main has security and workflow implications:

⚠️ **Risks:**
- History can be rewritten (force push)
- Changes can bypass code review
- Potential for accidental overwrites
- Risk of breaking the main branch

✅ **Best Practices:**
- Only allow trusted team members
- Use force push with caution (prefer `--force-with-lease`)
- Consider using protected branches for production
- Implement CI/CD pipelines to catch issues
- Use branch naming conventions (e.g., `develop` for integration, `main` for production)

### Alternative Workflows

If you're removing branch protection for development purposes, consider:

1. **Development Branch:** Use `develop` or `integration` as the main development branch without protection
2. **Feature Branches:** Create feature branches for new work
3. **Release Branches:** Use `main` only for releases with protection enabled
4. **Git Flow:** Implement a Git Flow or GitHub Flow workflow

## GitHub API Configuration (Advanced)

You can also configure branch protection programmatically using the GitHub API:

```bash
# Remove branch protection
curl -X DELETE \
  -H "Authorization: token YOUR_GITHUB_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  https://api.github.com/repos/panatl/open-receivable/branches/main/protection

# Update branch protection to allow force pushes
curl -X PUT \
  -H "Authorization: token YOUR_GITHUB_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  https://api.github.com/repos/panatl/open-receivable/branches/main/protection \
  -d '{
    "required_status_checks": null,
    "enforce_admins": false,
    "required_pull_request_reviews": null,
    "restrictions": null,
    "allow_force_pushes": true,
    "allow_deletions": false
  }'
```

Replace `YOUR_GITHUB_TOKEN` with a personal access token that has `repo` scope.

## Troubleshooting

### Error: "protected branch hook declined"

This means branch protection is still enabled. Follow the steps above to disable or modify the protection rules.

### Error: "Permission denied"

You need admin access to the repository to modify branch protection rules. Contact the repository owner.

### Error: "failed to push some refs"

This could be due to:
1. Branch protection rules still in place
2. Lack of permissions
3. Merge conflicts (use `git pull` first or `--force` if intentional)

## For Repository Administrators

As a repository administrator, you can:

1. Navigate to repository **Settings > Branches**
2. Manage all branch protection rules
3. Create different rules for different branch patterns
4. Set up status checks and required reviews
5. Configure who can bypass protection rules

## Summary

To enable force push and direct push to main:

1. ✅ Go to GitHub repository Settings > Branches
2. ✅ Either delete the branch protection rule for `main`
3. ✅ Or modify it to allow force pushes and disable PR requirements
4. ✅ Save changes and test with your git commands

Remember to communicate any changes to your team and establish clear guidelines for using force push responsibly.
