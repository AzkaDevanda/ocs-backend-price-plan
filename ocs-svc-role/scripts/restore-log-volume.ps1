param(
  [string]$BackupFile = $env:BACKUP_FILE
)

$ErrorActionPreference = "Stop"

$VolumeName = if ($env:VOLUME_NAME) { $env:VOLUME_NAME } else { "ocs-svc-role-logs" }

if (-not $BackupFile) {
  throw "Usage: .\scripts\restore-log-volume.ps1 -BackupFile .\backups\logs\logs-YYYY-MM.tar.gz"
}

if (-not (Test-Path $BackupFile -PathType Leaf)) {
  throw "Backup file not found: $BackupFile"
}

$BackupFileAbs = (Resolve-Path $BackupFile).Path
$BackupDirAbs = Split-Path $BackupFileAbs -Parent
$BackupName = Split-Path $BackupFileAbs -Leaf

docker volume inspect $VolumeName *> $null
if ($LASTEXITCODE -ne 0) {
  docker volume create $VolumeName | Out-Null
}

docker run --rm `
  -v "${VolumeName}:/volume" `
  -v "${BackupDirAbs}:/restore:ro" `
  alpine:3.20 `
  sh -c "cd /volume && tar -xzf /restore/$BackupName"

Write-Host "Restore completed to volume: $VolumeName"
