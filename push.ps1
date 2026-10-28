Set-Location $PSScriptRoot
Write-Host "Pushing updates to GitHub repository..." -ForegroundColor Green
$gitCmd = "$env:USERPROFILE\.mingit\cmd\git.exe"
& $gitCmd push origin main
