package me.mmarz11.aod.handlers.map;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Map;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobHandler implements Listener {
	public MobHandler() {
		AoD.inst.getServer().getPluginManager().registerEvents(this, AoD.inst);
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		World world = event.getLocation().getWorld();

		if (world.equals(AoD.inst.handlers.mapHandler.lobbySpawn.getWorld())) {
			event.setCancelled(true);
		} else {
			Map map = AoD.inst.handlers.mapHandler.currentMap;
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
