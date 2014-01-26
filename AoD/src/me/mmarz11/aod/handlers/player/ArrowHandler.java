package me.mmarz11.aod.handlers.player;

import java.util.HashMap;
import java.util.List;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.ArrowType;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class ArrowHandler implements Listener {
	private HashMap<Integer, ArrowType> arrows;

	public ArrowHandler() {
		AoD.inst.getServer().getPluginManager()
				.registerEvents(this, AoD.inst);
		this.arrows = new HashMap<Integer, ArrowType>();
	}

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		Entity entity = event.getEntity();
		if (AoD.inst.handlers.mapHandler.lobbySpawn.getWorld().equals(
				entity.getWorld())) {
			event.setCancelled(true);
			return;
		}

		if (entity instanceof Player) {
			Player player = (Player) entity;
			ItemMeta meta = event.getBow().getItemMeta();

			if (meta.hasLore()) {
				int id = event.getProjectile().getEntityId();

				List<String> lore = meta.getLore();
				String type = lore.get(0);
				if (type.equalsIgnoreCase("explosive")) {
					player.sendMessage("Explosive arrow shot.");
					arrows.put(id, ArrowType.EXPLOSIVE);
				} else if (type.equalsIgnoreCase("poison")) {
					player.sendMessage("Poison arrow shot.");
					arrows.put(id, ArrowType.POISON);
				} else if (type.equalsIgnoreCase("teleport")) {
					player.sendMessage("Teleportation arrow shot.");
					arrows.put(id, ArrowType.TELEPORT);
				}
			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();
		if (projectile instanceof Arrow) {
			Arrow arrow = (Arrow) projectile;
			World world = arrow.getWorld();
			Location location = arrow.getLocation();
			ArrowType type = arrows.remove(arrow.getEntityId());
			if (type == ArrowType.EXPLOSIVE) {
				world.createExplosion(location, 4F);
				arrow.remove();
			} else if (type == ArrowType.POISON) {
				Entity entity = world.spawnEntity(location,
						EntityType.SPLASH_POTION);
				ThrownPotion potionEntity = (ThrownPotion) entity;

				Potion potion = new Potion(PotionType.POISON);
				potion.setLevel(1);

				potionEntity.setItem(potion.toItemStack(1));
				potionEntity.setVelocity(arrow.getVelocity());
				potionEntity.setShooter(arrow.getShooter());
				arrow.remove();
			} else if (type == ArrowType.TELEPORT) {
				LivingEntity shooter = arrow.getShooter();
				shooter.teleport(new Location(world, location.getX(), location
						.getY(), location.getZ(), shooter.getLocation()
						.getYaw(), shooter.getLocation().getPitch()));
				arrow.remove();
			}
		}
	}
}
