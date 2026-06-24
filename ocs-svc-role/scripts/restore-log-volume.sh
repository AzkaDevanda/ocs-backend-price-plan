#!/usr/bin/env sh
set -eu

VOLUME_NAME="${VOLUME_NAME:-ocs-svc-role-logs}"
BACKUP_FILE="${1:-${BACKUP_FILE:-}}"

if [ -z "${BACKUP_FILE}" ]; then
  echo "Usage: $0 <logs-YYYY-MM.tar.gz>"
  echo "Or set BACKUP_FILE=/path/to/logs-YYYY-MM.tar.gz"
  exit 1
fi

if [ ! -f "${BACKUP_FILE}" ]; then
  echo "Backup file not found: ${BACKUP_FILE}"
  exit 1
fi

BACKUP_DIR_ABS="$(cd "$(dirname "${BACKUP_FILE}")" && pwd)"
BACKUP_NAME="$(basename "${BACKUP_FILE}")"

docker volume inspect "${VOLUME_NAME}" >/dev/null 2>&1 || docker volume create "${VOLUME_NAME}" >/dev/null

docker run --rm \
  -v "${VOLUME_NAME}:/volume" \
  -v "${BACKUP_DIR_ABS}:/restore:ro" \
  alpine:3.20 \
  sh -c "cd /volume && tar -xzf /restore/${BACKUP_NAME}"

echo "Restore completed to volume: ${VOLUME_NAME}"
