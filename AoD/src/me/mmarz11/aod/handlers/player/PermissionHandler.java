package me.mmarz11.aod.handlers.player;

import java.util.HashMap;

import me.mmarz11.aod.AoD;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

public class PermissionHandler implements Listener {
	public HashMap<String, PermissionAttachment> permissions;
	
	public PermissionHandler() {
		AoD.inst.getServer().getPluginManager().registerEvents(this, AoD.inst);
		
		this.permissions = new HashMap<String, PermissionAttachment>();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		FileConfiguration config = AoD.inst.handlers.configHandler.getStatsConfig();
		config.getString(player.getName() + ".rank");
	}
}
