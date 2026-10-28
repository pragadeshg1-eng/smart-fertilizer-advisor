# PowerShell Launcher for Vayal - Smart Fertilizer Advisor
Set-Location $PSScriptRoot
Write-Host "Starting Vayal - Smart Fertilizer Advisor..." -ForegroundColor Green

if (-not $env:JAVA_HOME -and (Test-Path "$env:USERPROFILE\.jdks\jdk-17.0.12+7")) {
    $env:JAVA_HOME = "$env:USERPROFILE\.jdks\jdk-17.0.12+7"
}

if ($env:JAVA_HOME) {
    $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
}

$mvnCmd = "mvn"
if (Test-Path "$env:USERPROFILE\.maven\apache-maven-3.9.6\bin\mvn.cmd") {
    $mvnCmd = "$env:USERPROFILE\.maven\apache-maven-3.9.6\bin\mvn.cmd"
}

Write-Host "Using Java: $env:JAVA_HOME" -ForegroundColor Cyan
& $mvnCmd spring-boot:run
