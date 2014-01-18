package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TeamkillHandler implements Listener {
	private AoD plugin;

	public TeamkillHandler(AoD plugin) {
		this.plugin = plugin;

		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		System.out.println("Triggered EDBEE");

		Entity damaged = event.getEntity();

		if (damaged.getWorld().equals(
				this.plugin.handlers.mapHandler.lobbySpawn.getWorld())) {
			event.setCancelled(true);
			return;
		}

		if (damaged instanceof Player) {
			Player player = (Player) damaged;
			Entity damager = event.getDamager();

			if (damager instanceof Player) {
				Player player2 = (Player) damager;

				PlayerTypeHandler handler = this.plugin.handlers.playerTypeHandler;
				if ((handler.survivors.contains(player.getName()) && handler.survivors
						.contains(player2.getName()))
						|| (handler.hunters.contains(player.getName()) && handler.hunters
								.contains(player2.getName()))) {
					player2.sendMessage(ChatColor.RED
							+ "Team killing is not allowed.");
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		System.out.println("Triggered PSE");

		ThrownPotion potion = event.getEntity();

		if (potion.getWorld().equals(
				this.plugin.handlers.mapHandler.lobbySpawn.getWorld())) {
			event.setCancelled(true);
			return;
		}

		if (isHarmful(potion)) {
			Entity shooter = potion.getShooter();
			if (shooter instanceof Player) {
				Player player = (Player) shooter;

				boolean attemptedTeamkill = false;
				for (Entity entity : event.getAffectedEntities()) {
					if (entity instanceof Player) {
						Player player2 = (Player) entity;

						if (player.getName().equals(player2.getName())) {
							continue;
						}

						PlayerTypeHandler handler = this.plugin.handlers.playerTypeHandler;
						if ((handler.survivors.contains(player.getName()) && handler.survivors
								.contains(player2.getName()))
								|| (handler.hunters.contains(player.getName()) && handler.hunters
										.contains(player2.getName()))) {
							attemptedTeamkill = true;
							event.setIntensity(player2, 0);
						}
					}
				}

				if (attemptedTeamkill) {
					player.sendMessage(ChatColor.RED
							+ "Throwing negative potions on teammates is not allowed.");
				}
			}
		}
	}

	private boolean isHarmful(ThrownPotion potion) {
		for (PotionEffect effect : potion.getEffects()) {
			PotionEffectType type = effect.getType();
			if (type == PotionEffectType.BLINDNESS
					|| type == PotionEffectType.CONFUSION
					|| type == PotionEffectType.HARM
					|| type == PotionEffectType.HUNGER
					|| type == PotionEffectType.POISON
					|| type == PotionEffectType.SLOW
					|| type == PotionEffectType.SLOW_DIGGING
					|| type == PotionEffectType.WEAKNESS
					|| type == PotionEffectType.WITHER) {
				return true;
			}
		}
		return false;
	}
}
