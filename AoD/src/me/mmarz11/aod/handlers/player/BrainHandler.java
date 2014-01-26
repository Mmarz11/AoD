package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BrainHandler implements Listener {
	public int kill;
	public int survive;

	public BrainHandler() {
		AoD.inst.getServer().getPluginManager().registerEvents(this, AoD.inst);
	}
	
	public void init() {
		kill = AoD.inst.handlers.configHandler.getConfig()
				.getInt("brains.kill");
		survive = AoD.inst.handlers.configHandler.getConfig().getInt(
				"brains.survive");
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
		for (String name : AoD.inst.handlers.playerTypeHandler.survivors) {
			addBrains(name, survive);
		}
	}

	public void addBrains(String name, int brains) {
		AoD.inst.handlers.statHandler.stats.get(name).brains += brains;
	}
}
