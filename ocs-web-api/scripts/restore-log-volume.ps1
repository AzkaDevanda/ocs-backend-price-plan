param(
  [string]$VolumeName = $env:VOLUME_NAME,
  [string]$BackupDir = $env:BACKUP_DIR,
  [string]$BackupMonth = $env:BACKUP_MONTH,
  [string]$BackupFile = $env:BACKUP_FILE
)

$ErrorActionPreference = "Stop"

if (-not $VolumeName) { $VolumeName = "ocs-svc-price-plan-logs" }
if (-not $BackupDir) { $BackupDir = ".\backups\logs" }
if (-not $BackupMonth) { $BackupMonth = Get-Date -Format "yyyy-MM" }
if (-not $BackupFile) { $BackupFile = "logs-$BackupMonth.tar.gz" }

$backupPath = Join-Path $BackupDir $BackupFile
if (-not (Test-Path $backupPath)) {
  throw "Backup file not found: $backupPath"
}

$backupDirAbs = (Resolve-Path $BackupDir).Path

docker volume create $VolumeName | Out-Null
docker run --rm `
  -v "${VolumeName}:/logs" `
  -v "${backupDirAbs}:/backup:ro" `
  alpine:3.20 `
  sh -c "cd /logs && tar -xzf /backup/$BackupFile"

Write-Host "Backup restored to volume ${VolumeName}: $backupPath"
