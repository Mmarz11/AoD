package me.mmarz11.aod.handlers.map;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Map;
import me.mmarz11.aod.enums.Timer;
import me.mmarz11.aod.handlers.player.PlayerTypeHandler;
import me.mmarz11.aod.information.TeamInformation;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildBreakHandler implements Listener {
	public BuildBreakHandler() {
		AoD.inst.getServer().getPluginManager().registerEvents(this, AoD.inst);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		String name = block.getType().name();

		String worldName = AoD.inst.handlers.configHandler.getConfig()
				.getString("lobby.world");

		World lobby = AoD.inst.getServer().getWorld(worldName);
		if (block.getWorld().equals(lobby)) {
			event.setCancelled(true);
			return;
		}

		Player player = event.getPlayer();
		PlayerTypeHandler handler = AoD.inst.handlers.playerTypeHandler;

		Map map = AoD.inst.handlers.mapHandler.currentMap;
		Timer timer = AoD.inst.handlers.timerHandler.getCurrentTimer();
		if (handler.survivors.contains(player.getName())) {
			TeamInformation info = map.mapInformation.survivor;
			if (timer == Timer.BUILD) {
				if (info.buildEnabled) {
					if (info.buildRestricted.contains(name)) {
						event.setCancelled(true);
					}
				} else {
					if (!info.buildAllowed.contains(name)
							&& !info.buildAlways.contains(name)) {
						event.setCancelled(true);
					}
				}
			} else if (timer == Timer.ROUND) {
				if (!info.buildAlways.contains(name)) {
					event.setCancelled(true);
				}
			}
		} else if (handler.hunters.contains(player.getName())) {
			TeamInformation info = map.mapInformation.hunter;
			if (info.buildEnabled) {
				if (info.buildRestricted.contains(name)) {
					event.setCancelled(true);
				}
			} else {
				if (!info.buildAllowed.contains(name)
						&& !info.buildAlways.contains(name)) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		Block block = event.getBlock();
		String name = block.getType().name();

		String worldName = AoD.inst.handlers.configHandler.getConfig()
				.getString("lobby.world");

		World lobby = AoD.inst.getServer().getWorld(worldName);
		if (block.getWorld().equals(lobby)) {
			event.setCancelled(true);
			return;
		}

		Player player = event.getPlayer();
		PlayerTypeHandler handler = AoD.inst.handlers.playerTypeHandler;

		Map map = AoD.inst.handlers.mapHandler.currentMap;
		Timer timer = AoD.inst.handlers.timerHandler.getCurrentTimer();
		if (handler.survivors.contains(player.getName())) {
			TeamInformation info = map.mapInformation.survivor;
			if (timer == Timer.BUILD) {
				if (info.breakEnabled) {
					if (info.breakRestricted.contains(name)) {
						event.setCancelled(true);
					}
				} else {
					if (!info.breakAllowed.contains(name)
							&& !info.breakAlways.contains(name)) {
						event.setCancelled(true);
					}
				}
			} else if (timer == Timer.ROUND) {
				if (!info.breakAlways.contains(name)) {
					event.setCancelled(true);
				}
			}
		} else if (handler.hunters.contains(player.getName())) {
			TeamInformation info = map.mapInformation.hunter;
			if (info.breakEnabled) {
				if (info.breakRestricted.contains(name)) {
					event.setCancelled(true);
				}
			} else {
				if (!info.breakAllowed.contains(name)
						&& !info.breakAlways.contains(name)) {
					event.setCancelled(true);
				}
			}
		}
	}
}
