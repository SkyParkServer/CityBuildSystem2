#!/usr/bin/env bash
set -euo pipefail

docker exec -i citybuild-paper rcon-cli "skript reload all"
