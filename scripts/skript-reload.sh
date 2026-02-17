#!/usr/bin/env bash
set -euo pipefail

if [[ $# -gt 0 ]]; then
  target="$1"
  docker exec -i citybuild-paper rcon-cli "skript reload ${target}"
else
  docker exec -i citybuild-paper rcon-cli "skript reload all"
fi
