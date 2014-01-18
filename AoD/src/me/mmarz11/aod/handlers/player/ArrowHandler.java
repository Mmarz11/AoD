package me.mmarz11.aod.handlers.player;

import java.util.HashMap;
import java.util.List;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.ArrowType;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArrowHandler implements Listener {
	private AoD plugin;

	private HashMap<Integer, ArrowType> arrows;

	public ArrowHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
		this.arrows = new HashMap<Integer, ArrowType>();
	}

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		Entity entity = event.getEntity();
		if (this.plugin.handlers.mapHandler.lobbySpawn.getWorld().equals(
				entity.getWorld())) {
			event.setCancelled(true);
			return;
		}

		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemStack bow = event.getBow();
			ItemMeta meta = bow.getItemMeta();

			if (meta.hasLore()) {
				List<String> lore = meta.getLore();
				if (lore.get(0).equalsIgnoreCase("explosive")) {
					player.sendMessage("Explosive arrow shot.");
					arrows.put(event.getProjectile().getEntityId(),
							ArrowType.EXPLOSIVE);
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();
		if (projectile instanceof Arrow) {
			Arrow arrow = (Arrow) projectile;
			if (arrows.remove(arrow.getEntityId()) == ArrowType.EXPLOSIVE) {
				arrow.getWorld().createExplosion(arrow.getLocation(), 4F);
			}
		}
	}
}
