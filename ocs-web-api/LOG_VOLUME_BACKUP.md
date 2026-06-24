# Log Volume Backup

## Struktur Log

Log aplikasi disimpan di Docker named volume `ocs-svc-price-plan-logs` dan di-mount ke `/apps/logs`.

- File aktif/current:
  - `/apps/logs/current/app.log`
  - `/apps/logs/current/error.log`
  - `/apps/logs/current/advice-cache.log`
- Archive bulanan:
  - `/apps/logs/YYYY-MM/app-YYYY-MM-DD.0.log.gz`
  - `/apps/logs/YYYY-MM/error-YYYY-MM-DD.0.log.gz`
  - `/apps/logs/YYYY-MM/advice-cache-YYYY-MM-DD.0.log.gz`

Logback melakukan cleanup otomatis dengan `maxHistory`, `totalSizeCap`, dan `cleanHistoryOnStart=true`. Default production:

- `LOG_RETENTION_DAYS=180`
- `LOG_MAX_TOTAL_SIZE=10GB`
- `LOG_MAX_FILE_SIZE=100MB`

## Backup Manual Linux

```sh
mkdir -p ./backups/logs
docker run --rm \
  -v ocs-svc-price-plan-logs:/logs:ro \
  -v "$(pwd)/backups/logs:/backup" \
  alpine:3.20 \
  sh -c 'cd /logs && tar -czf /backup/logs-$(date +%Y-%m).tar.gz .'
```

## Backup Manual Windows PowerShell

```powershell
New-Item -ItemType Directory -Force -Path .\backups\logs | Out-Null
$backupDir = (Resolve-Path .\backups\logs).Path
$month = Get-Date -Format "yyyy-MM"
docker run --rm `
  -v "ocs-svc-price-plan-logs:/logs:ro" `
  -v "${backupDir}:/backup" `
  alpine:3.20 `
  sh -c "cd /logs && tar -czf /backup/logs-$month.tar.gz ."
```

## Restore Manual Linux

```sh
docker volume create ocs-svc-price-plan-logs
docker run --rm \
  -v ocs-svc-price-plan-logs:/logs \
  -v "$(pwd)/backups/logs:/backup:ro" \
  alpine:3.20 \
  sh -c 'cd /logs && tar -xzf /backup/logs-2026-05.tar.gz'
```

## Restore Manual Windows PowerShell

```powershell
$backupDir = (Resolve-Path .\backups\logs).Path
docker volume create ocs-svc-price-plan-logs
docker run --rm `
  -v "ocs-svc-price-plan-logs:/logs" `
  -v "${backupDir}:/backup:ro" `
  alpine:3.20 `
  sh -c "cd /logs && tar -xzf /backup/logs-2026-05.tar.gz"
```

## Script Siap Pakai

Linux:

```sh
sh scripts/backup-log-volume.sh
BACKUP_MONTH=2026-05 sh scripts/restore-log-volume.sh
```

Windows PowerShell:

```powershell
.\scripts\backup-log-volume.ps1
$env:BACKUP_MONTH = "2026-05"; .\scripts\restore-log-volume.ps1
```

Environment variable yang didukung script backup:

- `VOLUME_NAME`, default `ocs-svc-price-plan-logs`
- `BACKUP_DIR`, default `./backups/logs`
- `BACKUP_MONTH`, default bulan saat ini dalam format `YYYY-MM`
- `BACKUP_RETENTION_DAYS`, default `0` atau tidak menghapus backup lama

Script restore juga mendukung `BACKUP_FILE` bila nama file berbeda dari `logs-YYYY-MM.tar.gz`.

## Cronjob Linux

Jalankan `crontab -e`, lalu tambahkan contoh berikut untuk backup tanggal 1 setiap bulan jam 01:30:

```cron
30 1 1 * * cd /opt/ocs-web-api && BACKUP_RETENTION_DAYS=730 sh scripts/backup-log-volume.sh >> backups/logs/backup.log 2>&1
```

## Task Scheduler Windows

Contoh command untuk action Task Scheduler:

```powershell
powershell.exe -ExecutionPolicy Bypass -File C:\path\to\ocs-web-api\scripts\backup-log-volume.ps1
```

Atur trigger bulanan, misalnya tanggal 1 jam 01:30, dan set working directory ke folder project.

## Upload ke S3/Object Storage

AWS S3:

```sh
sh scripts/backup-log-volume.sh
aws s3 cp ./backups/logs/logs-$(date +%Y-%m).tar.gz s3://my-bucket/ocs/logs/
```

S3-compatible object storage:

```sh
AWS_ENDPOINT_URL=https://s3.example.com \
aws s3 cp ./backups/logs/logs-$(date +%Y-%m).tar.gz s3://my-bucket/ocs/logs/ \
  --endpoint-url "$AWS_ENDPOINT_URL"
```

## Backup ke NAS

Contoh rsync ke NAS:

```sh
sh scripts/backup-log-volume.sh
rsync -av --progress ./backups/logs/ nas-user@nas-host:/volume1/backup/ocs/logs/
```

## Monitoring Ukuran Log

```sh
docker exec -it docker-ocs-web-api du -sh /apps/logs/*
docker exec -it docker-ocs-web-api sh -c 'find /apps/logs -type f | wc -l'
```

Tail file aktif:

```sh
docker exec -it docker-ocs-web-api tail -f /apps/logs/current/app.log
docker exec -it docker-ocs-web-api tail -f /apps/logs/current/error.log
```

Baca archive `.gz`:

```sh
zcat /apps/logs/2026-05/app-2026-05-26.0.log.gz
zgrep "ERROR" /apps/logs/2026-05/error-2026-05-26.0.log.gz
```

## Rekomendasi Production

- Log transaksi: 6 bulan atau 180 hari.
- Log audit: 12 bulan atau 365 hari, atau ikuti compliance internal.
- Log debug: 30 sampai 90 hari.
- Simpan backup remote/NAS/S3 lebih lama bila diperlukan, misalnya 12 sampai 24 bulan.
- Gunakan mekanisme rolling dan cleanup bawaan Logback untuk aplikasi container; hindari cron cleanup di dalam container aplikasi.
- Pastikan backup volume diuji dengan restore berkala ke environment non-production.
- Batasi akses file backup karena archive log dapat berisi data operasional sensitif.
