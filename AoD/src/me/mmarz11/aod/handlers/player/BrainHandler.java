package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BrainHandler implements Listener {
	private AoD plugin;

	public int kill;
	public int survive;

	public BrainHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);

		kill = this.plugin.handlers.configHandler.getConfig().getInt(
				"brains.kill");
		survive = this.plugin.handlers.configHandler
				.getConfig().getInt("brains.survive");
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player victim = event.getEntity();
		Player killer = victim.getKiller();

		if (killer != null && !killer.equals(victim)) {
			addBrains(killer.getName(), kill);
		}
	}
	
	public void survived() {
		for (String name : this.plugin.handlers.playerTypeHandler.survivors) {
			addBrains(name, survive);
		}
	}

	public void addBrains(String name, int brains) {
		this.plugin.handlers.statHandler.stats.get(name).brains += brains;
	}
}
