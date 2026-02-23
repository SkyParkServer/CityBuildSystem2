package de.skypark.citybuild.commands;

import de.skypark.citybuild.CityBuildSystem;
import de.skypark.citybuild.util.LocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final CityBuildSystem plugin;

    public SetSpawnCommand(CityBuildSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            plugin.messages().error(sender, "Only players can run this command.");
            return true;
        }

        if (!sender.hasPermission("citybuild.admin.spawn")) {
            plugin.messages().error(sender, plugin.settings().noPermissionMessage());
            return true;
        }

        String locText = LocationUtil.locToText(player.getLocation());
        plugin.globals().setSpawnLocationText(locText);

        plugin.messages().success(player, "Spawn has been updated.");
        plugin.messages().debug("Spawn changed by " + player.getName() + " to " + locText);
        return true;
    }
}
