#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

if [ ! -f "${ROOT_DIR}/.env" ]; then
  cp "${ROOT_DIR}/.env.example" "${ROOT_DIR}/.env"
  echo "Created .env from .env.example"
fi

"${ROOT_DIR}/scripts/download-plugins.sh"
"${ROOT_DIR}/scripts/ensure-skript-config.sh"
docker compose -f "${ROOT_DIR}/docker-compose.yml" up -d
