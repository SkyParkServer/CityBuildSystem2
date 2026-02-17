#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PLUGIN_DIR="${ROOT_DIR}/server/plugins"

mkdir -p "${PLUGIN_DIR}"

download_plugin() {
  local file_name="$1"
  local url="$2"
  local target_file="${PLUGIN_DIR}/${file_name}"

  if [ -f "${target_file}" ] && [ "${FORCE_PLUGIN_DOWNLOAD:-false}" != "true" ]; then
    echo "-> ${file_name} (already present, skipping)"
    return
  fi

  echo "-> ${file_name}"
  curl -fsSL "${url}" -o "${target_file}.part"
  mv "${target_file}.part" "${target_file}"
}

echo "Downloading plugin jars to ${PLUGIN_DIR}"

download_plugin "Skript-2.14.1.jar" "https://github.com/SkriptLang/Skript/releases/download/2.14.1/Skript-2.14.1.jar"
download_plugin "skript-reflect-2.6.3.jar" "https://github.com/SkriptLang/skript-reflect/releases/download/v2.6.3/skript-reflect-2.6.3.jar"
download_plugin "SkBee-3.16.1.jar" "https://cdn.modrinth.com/data/a0tlbHZO/versions/YUe8KJhT/SkBee-3.16.1.jar"
download_plugin "skript-yaml-1.7.2.jar" "https://github.com/Sashie/skript-yaml/releases/download/v1.7.2/skript-yaml-1.7.2.jar"
download_plugin "SkBriggy-1.5.7.jar" "https://cdn.modrinth.com/data/EU37SyNH/versions/gtk8qBvt/SkBriggy-1.5.7.jar"
download_plugin "Skcrew-4.4.2.jar" "https://gitlab.crewpvp.xyz/-/project/1/uploads/a0053961b564a4a1d395b1bead7a19bd/Skcrew-4.4.2.jar"

download_plugin "Vault.jar" "https://github.com/MilkBowl/Vault/releases/download/1.7.3/Vault.jar"
download_plugin "LuckPerms-Bukkit-5.5.17.jar" "https://cdn.modrinth.com/data/Vebnzrzj/versions/OrIs0S6b/LuckPerms-Bukkit-5.5.17.jar"
download_plugin "PlaceholderAPI-2.12.2.jar" "https://github.com/PlaceholderAPI/PlaceholderAPI/releases/download/2.12.2/PlaceholderAPI-2.12.2.jar"
download_plugin "ProtocolLib.jar" "https://github.com/dmulloy2/ProtocolLib/releases/download/5.4.0/ProtocolLib.jar"

echo "Done. Restart the server after plugin updates."
