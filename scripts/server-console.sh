#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -eq 0 ]; then
  echo "Usage: ./scripts/server-console.sh <command>"
  echo "Example: ./scripts/server-console.sh \"say CityBuild dev server online\""
  exit 1
fi

docker exec -i citybuild-paper rcon-cli "$*"
