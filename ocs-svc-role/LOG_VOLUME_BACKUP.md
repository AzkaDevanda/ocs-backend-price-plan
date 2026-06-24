# Log Volume Backup

## Struktur Log

Log aplikasi disimpan di Docker named volume `ocs-svc-role-logs` dan di-mount ke `/apps/logs`.

```text
/apps/logs/
  current/
    app.log
    error.log
  2026-05/
    app-2026-05-26.0.log.gz
    error-2026-05-26.0.log.gz
```

`current/app.log` berisi log normal sampai WARN. `current/error.log` berisi ERROR ke atas. Archive harian dikompres `.gz` dan dipisahkan per bulan.

## Backup Manual Linux

```sh
mkdir -p ./backups/logs
docker run --rm \
  -v ocs-svc-role-logs:/volume:ro \
  -v "$(pwd)/backups/logs:/backup" \
  alpine:3.20 \
  sh -c 'cd /volume && tar -czf /backup/logs-$(date +%Y-%m).tar.gz .'
```

## Backup Manual Windows PowerShell

```powershell
New-Item -ItemType Directory -Force -Path .\backups\logs | Out-Null
$month = Get-Date -Format "yyyy-MM"
$backupDir = (Resolve-Path .\backups\logs).Path
docker run --rm `
  -v ocs-svc-role-logs:/volume:ro `
  -v "${backupDir}:/backup" `
  alpine:3.20 `
  sh -c "cd /volume && tar -czf /backup/logs-$month.tar.gz ."
```

## Restore Manual Linux

```sh
docker volume create ocs-svc-role-logs
docker run --rm \
  -v ocs-svc-role-logs:/volume \
  -v "$(pwd)/backups/logs:/restore:ro" \
  alpine:3.20 \
  sh -c 'cd /volume && tar -xzf /restore/logs-2026-05.tar.gz'
```

## Restore Manual Windows PowerShell

```powershell
docker volume create ocs-svc-role-logs
$backupDir = (Resolve-Path .\backups\logs).Path
docker run --rm `
  -v ocs-svc-role-logs:/volume `
  -v "${backupDir}:/restore:ro" `
  alpine:3.20 `
  sh -c "cd /volume && tar -xzf /restore/logs-2026-05.tar.gz"
```

## Script Siap Pakai

Linux backup:

```sh
sh scripts/backup-log-volume.sh
```

Linux restore:

```sh
sh scripts/restore-log-volume.sh ./backups/logs/logs-2026-05.tar.gz
```

Windows backup:

```powershell
.\scripts\backup-log-volume.ps1
```

Windows restore:

```powershell
.\scripts\restore-log-volume.ps1 -BackupFile .\backups\logs\logs-2026-05.tar.gz
```

Environment variable yang didukung script backup:

```text
VOLUME_NAME=ocs-svc-role-logs
BACKUP_DIR=./backups/logs
BACKUP_MONTH=2026-05
BACKUP_RETENTION_DAYS=365
```

Script restore mendukung `VOLUME_NAME` dan `BACKUP_FILE`.

## Cronjob Linux

Jalankan `crontab -e`, lalu tambahkan contoh berikut untuk backup tanggal 1 setiap bulan pukul 01:30.

```cron
30 1 1 * * cd /opt/ocs-svc-role && BACKUP_RETENTION_DAYS=730 sh scripts/backup-log-volume.sh >> /var/log/ocs-svc-role-log-backup.log 2>&1
```

## Task Scheduler Windows

Contoh command untuk Action di Task Scheduler:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File "C:\path\to\ocs-svc-role\scripts\backup-log-volume.ps1"
```

Set trigger bulanan, misalnya tanggal 1 pukul 01:30.

## Upload ke S3 atau Object Storage

AWS S3:

```sh
sh scripts/backup-log-volume.sh
aws s3 cp ./backups/logs/logs-$(date +%Y-%m).tar.gz s3://my-bucket/ocs-svc-role/logs/
```

S3-compatible endpoint:

```sh
aws --endpoint-url https://s3.example.com s3 cp ./backups/logs/logs-$(date +%Y-%m).tar.gz s3://my-bucket/ocs-svc-role/logs/
```

## Backup ke NAS

Contoh menggunakan `rsync` setelah file backup dibuat:

```sh
sh scripts/backup-log-volume.sh
rsync -av --partial ./backups/logs/ nas-user@nas.example.com:/volume1/backups/ocs-svc-role/logs/
```

## Monitoring Ukuran Log

```sh
docker exec -it ocs-svc-role-container du -sh /apps/logs/*
docker exec -it ocs-svc-role-container sh -c 'find /apps/logs -type f | wc -l'
```

Tail log aktif:

```sh
docker exec -it ocs-svc-role-container tail -f /apps/logs/current/app.log
docker exec -it ocs-svc-role-container tail -f /apps/logs/current/error.log
```

Baca archive `.gz`:

```sh
zcat /apps/logs/2026-05/app-2026-05-26.0.log.gz
zgrep "ERROR" /apps/logs/2026-05/error-2026-05-26.0.log.gz
```

## Rekomendasi Production

Gunakan retention log transaksi 6 bulan atau 180 hari. Log audit sebaiknya 12 bulan atau 365 hari, atau mengikuti aturan compliance. Log debug cukup 30 sampai 90 hari dan sebaiknya tidak aktif terus-menerus di production.

Logback sudah dikonfigurasi dengan `maxHistory`, `totalSizeCap`, dan `cleanHistoryOnStart=true`, sehingga cleanup archive berjalan otomatis tanpa cron di container aplikasi. Backup remote ke NAS, S3, atau object storage dapat disimpan lebih lama, misalnya 12 sampai 24 bulan.
