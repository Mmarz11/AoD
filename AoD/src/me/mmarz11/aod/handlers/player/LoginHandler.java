package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginHandler implements Listener {
	public LoginHandler() {
		AoD.inst.getServer().getPluginManager()
				.registerEvents(this, AoD.inst);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (player.hasPlayedBefore()) {
			player.teleport(AoD.inst.handlers.mapHandler.lobbySpawn);
		} else {
			String worldName = AoD.inst.handlers.configHandler.getConfig()
					.getString("tutorial.world");

			World world = AoD.inst.getServer().getWorld(worldName);
			double x = AoD.inst.handlers.configHandler.getConfig().getDouble(
					"tutorial.location.x");
			double y = AoD.inst.handlers.configHandler.getConfig().getDouble(
					"tutorial.location.y");
			double z = AoD.inst.handlers.configHandler.getConfig().getDouble(
					"tutorial.location.z");

			Location spawn = new Location(world, x, y, z);
			
			player.teleport(spawn);
		}
	}
}
