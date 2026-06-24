$ErrorActionPreference = "Stop"

$VolumeName = if ($env:VOLUME_NAME) { $env:VOLUME_NAME } else { "ocs-svc-role-logs" }
$BackupDir = if ($env:BACKUP_DIR) { $env:BACKUP_DIR } else { ".\backups\logs" }
$BackupMonth = if ($env:BACKUP_MONTH) { $env:BACKUP_MONTH } else { Get-Date -Format "yyyy-MM" }
$BackupRetentionDays = if ($env:BACKUP_RETENTION_DAYS) { [int]$env:BACKUP_RETENTION_DAYS } else { 0 }

New-Item -ItemType Directory -Force -Path $BackupDir | Out-Null
$BackupDirAbs = (Resolve-Path $BackupDir).Path
$OutputFile = "logs-$BackupMonth.tar.gz"

docker volume inspect $VolumeName | Out-Null

docker run --rm `
  -v "${VolumeName}:/volume:ro" `
  -v "${BackupDirAbs}:/backup" `
  alpine:3.20 `
  sh -c "cd /volume && tar -czf /backup/$OutputFile ."

if ($BackupRetentionDays -gt 0) {
  Get-ChildItem -Path $BackupDirAbs -Filter "logs-*.tar.gz" -File |
    Where-Object { $_.LastWriteTime -lt (Get-Date).AddDays(-$BackupRetentionDays) } |
    Remove-Item -Force
}

Write-Host "Backup created: $BackupDirAbs\$OutputFile"
