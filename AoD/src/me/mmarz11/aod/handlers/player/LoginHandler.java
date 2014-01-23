package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginHandler implements Listener {
	private AoD plugin;

	public LoginHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (player.hasPlayedBefore()) {
			player.teleport(this.plugin.handlers.mapHandler.lobbySpawn);
		} else {
			String worldName = this.plugin.handlers.configHandler.getConfig()
					.getString("tutorial.world");

			World world = this.plugin.getServer().getWorld(worldName);
			double x = this.plugin.handlers.configHandler.getConfig().getDouble(
					"tutorial.location.x");
			double y = this.plugin.handlers.configHandler.getConfig().getDouble(
					"tutorial.location.y");
			double z = this.plugin.handlers.configHandler.getConfig().getDouble(
					"tutorial.location.z");

			Location spawn = new Location(world, x, y, z);
			
			player.teleport(spawn);
		}
	}
}
