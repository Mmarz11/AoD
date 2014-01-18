package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Map;
import me.mmarz11.aod.information.MapInformation;
import me.mmarz11.aod.information.TeamInformation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnHandler implements Listener {
	private AoD plugin;

	public RespawnHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Map map = this.plugin.handlers.mapHandler.currentMap;
		
		if (map == null) {
			event.setRespawnLocation(this.plugin.handlers.mapHandler.lobbySpawn);
			return;
		}
		
		MapInformation info = map.mapInformation;
		Player player = event.getPlayer();

		PlayerTypeHandler handler = this.plugin.handlers.playerTypeHandler;
		if (handler.survivors.contains(player.getName())) {
			TeamInformation teamInfo = info.survivor;
			if (teamInfo.spawnType.equalsIgnoreCase("lobby")) {
				event.setRespawnLocation(this.plugin.handlers.mapHandler.lobbySpawn);
			} else if (teamInfo.spawnType.equalsIgnoreCase("map")) {
				event.setRespawnLocation(teamInfo.spawn);
			}
		} else if (handler.hunters.contains(player.getName())) {
			TeamInformation teamInfo = info.hunter;
			if (teamInfo.spawnType.equalsIgnoreCase("lobby")) {
				event.setRespawnLocation(this.plugin.handlers.mapHandler.lobbySpawn);
			} else if (teamInfo.spawnType.equalsIgnoreCase("map")) {
				event.setRespawnLocation(teamInfo.spawn);
			}
		}
	}
}
