package me.mmarz11.aod.handlers.player;

import java.util.HashMap;
import java.util.List;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Kit;
import me.mmarz11.aod.Map;
import me.mmarz11.aod.enums.Timer;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;

public class PortalHandler implements Listener {
	private AoD plugin;
	
	private HashMap<String, Long> cooldown;

	public PortalHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
		cooldown = new HashMap<String, Long>();
	}

	@EventHandler
	public void onEntityPortalEnter(EntityPortalEnterEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;

			Timer timer = this.plugin.handlers.timerHandler.getCurrentTimer();
			Map map = this.plugin.handlers.mapHandler.currentMap;
			if (timer == Timer.LOBBY || map == null) {
				if (cooldown.containsKey(player.getName())) {
					long time = System.currentTimeMillis();
					if (time - cooldown.get(player.getName()) > 15000) {
						player.sendMessage("Portal is not active yet.");
						cooldown.put(player.getName(), time);
					}
				} else {
					player.sendMessage("Portal is not active yet.");
					cooldown.put(player.getName(), System.currentTimeMillis());
				}
			} else {
				if (this.plugin.handlers.playerTypeHandler.hunters.contains(player.getName())) {
					player.teleport(map.mapInformation.hunter.spawn);
					List<Kit> kits = map.mapInformation.hunter.starting;
					this.plugin.handlers.kitHandler.giveKit(player, kits.get((int)(Math.random()*kits.size())));
				} else {
					player.teleport(map.mapInformation.survivor.spawn);
					List<Kit> kits = map.mapInformation.survivor.starting;
					this.plugin.handlers.kitHandler.giveKit(player, kits.get((int)(Math.random()*kits.size())));
				}
			}
		}
	}

	@EventHandler
	public void onEntityPortal(EntityPortalEvent event) {
		event.setCancelled(true);
	}
}
