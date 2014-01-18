package me.mmarz11.aod.handlers.map;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Map;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobHandler implements Listener {
	private AoD plugin;

	public MobHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		World world = event.getLocation().getWorld();

		if (world.equals(this.plugin.handlers.mapHandler.lobbySpawn.getWorld())) {
			event.setCancelled(true);
		} else {
			Map map = this.plugin.handlers.mapHandler.currentMap;
			if (world.equals(map)) {
				if (!map.mapInformation.mobsEnabled
						&& event.getSpawnReason() != SpawnReason.SPAWNER_EGG) {
					event.setCancelled(true);
				}
			} else {
				event.setCancelled(true);
			}
		}
	}
}
