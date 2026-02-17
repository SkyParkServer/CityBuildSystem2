#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PLUGIN_DIR="${ROOT_DIR}/server/plugins"
SKRIPT_DIR="${PLUGIN_DIR}/Skript"
SKRIPT_JAR="${PLUGIN_DIR}/Skript-2.14.1.jar"

if [ ! -f "${SKRIPT_JAR}" ]; then
  echo "Skript jar not found at ${SKRIPT_JAR}"
  exit 1
fi

mkdir -p "${SKRIPT_DIR}/scripts"

if [ ! -f "${SKRIPT_DIR}/config.sk" ]; then
  unzip -p "${SKRIPT_JAR}" config.sk > "${SKRIPT_DIR}/config.sk"
  echo "Created Skript config.sk"
fi

if [ ! -f "${SKRIPT_DIR}/features.sk" ]; then
  unzip -p "${SKRIPT_JAR}" features.sk > "${SKRIPT_DIR}/features.sk"
  echo "Created Skript features.sk"
fi
