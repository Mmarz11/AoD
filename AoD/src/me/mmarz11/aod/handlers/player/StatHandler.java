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
	private AoD plugin;

	public HashMap<String, PlayerInformation> stats;

	public StatHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);

		this.stats = new HashMap<String, PlayerInformation>();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		stats.put(player.getName(),
				new PlayerInformation(this.plugin, player.getName(),
						this.plugin.handlers.configHandler.getStatsConfig()
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
