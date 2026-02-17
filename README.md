# CityBuild Skript Dev Environment (Paper 1.21.11)

This repository gives you a ready-to-use local CityBuild development setup with Paper + Skript and a clean script architecture for scaling features.

## Included stack

- Paper `1.21.11` (Docker)
- Skript `2.14.1`
- skript-reflect `2.6.3`
- SkBee `3.16.1`
- SkBriggy `1.5.7`
- skript-yaml `1.7.2`
- Vault, LuckPerms, PlaceholderAPI, ProtocolLib (core utility plugins)

## Quick start

1. Copy env file:

   ```bash
   cp .env.example .env
   ```

2. (Optional) edit `.env`:

   - `MC_PORT` if 25565 is already used
   - `MC_MEMORY` to increase/decrease RAM
   - `MC_OPS` to auto-op your Minecraft username
   - `MC_ONLINE_MODE` keep `false` for local dev

3. Start everything:

   ```bash
   ./scripts/server-up.sh
   ```

4. Follow logs:

   ```bash
   ./scripts/server-logs.sh
   ```

5. Stop server:

   ```bash
   ./scripts/server-down.sh
   ```

## Script layout

Skript files are mounted from `./skript` into the container at:

`/data/plugins/Skript/scripts/citybuild`

Current base modules:

- `00_options.sk`: defaults and global settings
- `01_utils_messages.sk`: prefix, color, info/success/error/debug helpers
- `02_utils_core.sk`: cooldown, balance, money and location helper
- `10_core_playerdata.sk`: player initialize/join/quit data
- `20_core_spawn.sk`: `/spawn` and `/setspawn`
- `30_admin_cb.sk`: `/cb` admin command (`reload`, `debug`, `info`)
- `40_core_home.sk`: `/sethome`, `/home`, `/delhome`, `/homes`
- `50_core_economy.sk`: `/balance`, `/eco`
- `60_core_qol.sk`: `/wb`, `/workbench`, `/craft`, `/anvil`, `/sign`, `/repair`, `/wetter`

All custom commands are implemented as `brig command` (SkBriggy), so they use Brigadier-style command suggestions/tab-complete.

## Useful commands

- In game/console:
  - `/cb reload`
  - `/cb debug on`
  - `/cb info`

- Host scripts:
  - `./scripts/download-plugins.sh`
  - `./scripts/ensure-skript-config.sh`
  - `./scripts/server-up.sh`
  - `./scripts/server-down.sh`
  - `./scripts/server-logs.sh`
  - `./scripts/server-console.sh "<command>"`
  - `./scripts/skript-reload.sh`

## Notes

- This is a dev-focused setup and starts with `online-mode=false` by default.
- All runtime server files are stored under `./server` (git-ignored).
- `./scripts/download-plugins.sh` skips already present jars by default. Use `FORCE_PLUGIN_DOWNLOAD=true ./scripts/download-plugins.sh` to force re-download.
- If you update plugin versions later, restart server after re-downloading jars.
