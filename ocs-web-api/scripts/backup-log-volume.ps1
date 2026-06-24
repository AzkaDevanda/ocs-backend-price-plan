param(
  [string]$VolumeName = $env:VOLUME_NAME,
  [string]$BackupDir = $env:BACKUP_DIR,
  [string]$BackupMonth = $env:BACKUP_MONTH,
  [int]$BackupRetentionDays = $(if ($env:BACKUP_RETENTION_DAYS) { [int]$env:BACKUP_RETENTION_DAYS } else { 0 })
)

$ErrorActionPreference = "Stop"

if (-not $VolumeName) { $VolumeName = "ocs-svc-price-plan-logs" }
if (-not $BackupDir) { $BackupDir = ".\backups\logs" }
if (-not $BackupMonth) { $BackupMonth = Get-Date -Format "yyyy-MM" }

$backupFile = "logs-$BackupMonth.tar.gz"
New-Item -ItemType Directory -Force -Path $BackupDir | Out-Null
$backupDirAbs = (Resolve-Path $BackupDir).Path

docker volume inspect $VolumeName | Out-Null
docker run --rm `
  -v "${VolumeName}:/logs:ro" `
  -v "${backupDirAbs}:/backup" `
  alpine:3.20 `
  sh -c "cd /logs && tar -czf /backup/$backupFile ."

if ($BackupRetentionDays -gt 0) {
  Get-ChildItem -Path $backupDirAbs -Filter "logs-*.tar.gz" |
    Where-Object { $_.LastWriteTime -lt (Get-Date).AddDays(-$BackupRetentionDays) } |
    Remove-Item -Force
}

Write-Host "Backup created: $backupDirAbs\$backupFile"
