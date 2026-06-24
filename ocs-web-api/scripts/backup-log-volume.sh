#!/usr/bin/env sh
set -eu

VOLUME_NAME="${VOLUME_NAME:-ocs-svc-price-plan-logs}"
BACKUP_DIR="${BACKUP_DIR:-./backups/logs}"
BACKUP_MONTH="${BACKUP_MONTH:-$(date +%Y-%m)}"
BACKUP_RETENTION_DAYS="${BACKUP_RETENTION_DAYS:-0}"
BACKUP_FILE="logs-${BACKUP_MONTH}.tar.gz"

mkdir -p "${BACKUP_DIR}"
BACKUP_DIR_ABS="$(cd "${BACKUP_DIR}" && pwd)"

docker volume inspect "${VOLUME_NAME}" >/dev/null
docker run --rm \
  -v "${VOLUME_NAME}:/logs:ro" \
  -v "${BACKUP_DIR_ABS}:/backup" \
  alpine:3.20 \
  sh -c "cd /logs && tar -czf /backup/${BACKUP_FILE} ."

if [ "${BACKUP_RETENTION_DAYS}" -gt 0 ]; then
  find "${BACKUP_DIR_ABS}" -type f -name 'logs-*.tar.gz' -mtime +"${BACKUP_RETENTION_DAYS}" -delete
fi

echo "Backup created: ${BACKUP_DIR_ABS}/${BACKUP_FILE}"
