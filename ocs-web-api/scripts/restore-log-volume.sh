#!/usr/bin/env sh
set -eu

VOLUME_NAME="${VOLUME_NAME:-ocs-svc-price-plan-logs}"
BACKUP_DIR="${BACKUP_DIR:-./backups/logs}"
BACKUP_MONTH="${BACKUP_MONTH:-$(date +%Y-%m)}"
BACKUP_FILE="${BACKUP_FILE:-logs-${BACKUP_MONTH}.tar.gz}"
BACKUP_PATH="${BACKUP_DIR}/${BACKUP_FILE}"

if [ ! -f "${BACKUP_PATH}" ]; then
  echo "Backup file not found: ${BACKUP_PATH}" >&2
  exit 1
fi

BACKUP_DIR_ABS="$(cd "${BACKUP_DIR}" && pwd)"

docker volume create "${VOLUME_NAME}" >/dev/null
docker run --rm \
  -v "${VOLUME_NAME}:/logs" \
  -v "${BACKUP_DIR_ABS}:/backup:ro" \
  alpine:3.20 \
  sh -c "cd /logs && tar -xzf /backup/${BACKUP_FILE}"

echo "Backup restored to volume ${VOLUME_NAME}: ${BACKUP_PATH}"
