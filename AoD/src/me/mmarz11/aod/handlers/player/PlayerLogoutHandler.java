package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogoutHandler implements Listener {
	public PlayerLogoutHandler() {
		AoD.inst.getServer().getPluginManager().registerEvents(this, AoD.inst);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		AoD.inst.handlers.playerTypeHandler.removePlayerFromAll(player);
	}
}
