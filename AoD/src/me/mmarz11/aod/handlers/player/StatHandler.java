package me.mmarz11.aod.handlers.player;

import java.util.HashMap;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.information.PlayerInformation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StatHandler implements Listener {
	public HashMap<String, PlayerInformation> stats;

	public StatHandler() {
		AoD.inst.getServer().getPluginManager()
				.registerEvents(this, AoD.inst);

		this.stats = new HashMap<String, PlayerInformation>();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		stats.put(player.getName(),
				new PlayerInformation(AoD.inst, player.getName(),
						AoD.inst.handlers.configHandler.getStatsConfig()
								.getConfigurationSection(player.getName())));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PlayerInformation info = stats.remove(player.getName());
		if (info != null) {
			info.save();
		}
	}
	
	public void saveAll() {
		for (PlayerInformation info : stats.values()) {
			info.save();
		}
	}
}
