# Quick Start: Enable Force and Direct Push to Main

This guide provides quick instructions for enabling force push and direct push to the main branch.

## For Repository Administrators

### Quick Steps (Web Interface)

1. Go to: `https://github.com/panatl/open-receivable/settings/branches`
2. Find or create a branch protection rule for `main`
3. Configure:
   - ✅ Check "Allow force pushes"
   - ❌ Uncheck "Require a pull request before merging"
   - OR ✅ Check "Allow specified actors to bypass required pull requests"
4. Save changes

### Alternative: Remove All Protection

1. Go to: `https://github.com/panatl/open-receivable/settings/branches`
2. Delete the branch protection rule for `main`
3. Confirm deletion

## Testing After Configuration

```bash
# Clone repository
git clone https://github.com/panatl/open-receivable.git
cd open-receivable

# Test direct push
git checkout main
echo "test" >> test.txt
git add test.txt
git commit -m "Test direct push"
git push origin main

# Test force push (use with caution!)
git push --force-with-lease origin main
```

## Important Notes

⚠️ **Force push will rewrite history** - Use `--force-with-lease` instead of `--force` when possible

⚠️ **Coordinate with team** - Communicate before force pushing to shared branches

✅ **Best practice** - Use feature branches for development, direct push to main for quick fixes only

## Full Documentation

- **Detailed Setup Guide:** [BRANCH_PROTECTION.md](BRANCH_PROTECTION.md)
- **Development Workflow:** [CONTRIBUTING.md](CONTRIBUTING.md)
- **Project Overview:** [README.md](README.md)

## Common Issues

### "Protected branch hook declined"
**Solution:** Branch protection is still active. Follow the steps above to disable or modify protection rules.

### "Permission denied"
**Solution:** You need admin access to modify branch protection settings. Contact the repository owner.

### "Failed to push some refs"
**Solution:** Pull latest changes first with `git pull origin main` or use `--force-with-lease` if you intend to overwrite.

## Support

If you encounter issues:
1. Review [BRANCH_PROTECTION.md](BRANCH_PROTECTION.md) for detailed troubleshooting
2. Check repository settings permissions
3. Open an issue in the repository

---

**Last Updated:** 2025-11-04
