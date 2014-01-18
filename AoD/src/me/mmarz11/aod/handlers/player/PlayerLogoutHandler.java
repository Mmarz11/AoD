package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogoutHandler implements Listener {
	private AoD plugin;
	
	public PlayerLogoutHandler(AoD plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		plugin.handlers.playerTypeHandler.removePlayerFromAll(player);
	}
}
