package de.skypark.citybuild.commands;

import de.skypark.citybuild.CityBuildSystem;
import de.skypark.citybuild.util.DurationFormatUtil;
import de.skypark.citybuild.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

public class SpawnCommand implements CommandExecutor {

    private final CityBuildSystem plugin;

    public SpawnCommand(CityBuildSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            plugin.messages().error(sender, "Only players can run this command.");
            return true;
        }

        String locText = plugin.globals().spawnLocationText();
        if (locText == null || locText.isEmpty()) {
            plugin.messages().error(player, "Spawn is not configured yet. Use /setspawn first.");
            return true;
        }

        if (plugin.cooldowns().hasCooldown("spawn", player)) {
            Duration remaining = plugin.cooldowns().cooldownRemaining("spawn", player);
            plugin.messages().error(player, "You can use /spawn again in " + DurationFormatUtil.format(remaining) + ".");
            return true;
        }

        int cooldownSeconds = plugin.globals().spawnCooldownSeconds();
        // Skript logic: if {cb.spawn.cooldown-seconds} not set -> const default; our GlobalState already ensures.
        plugin.cooldowns().startCooldown("spawn", player, cooldownSeconds);

        Location spawn = LocationUtil.textToLoc(locText);
        if (spawn == null) {
            plugin.messages().error(player, "Spawn location is invalid or world is missing.");
            return true;
        }

        player.teleport(spawn);
        plugin.messages().success(player, "Teleported to spawn.");
        return true;
    }
}
