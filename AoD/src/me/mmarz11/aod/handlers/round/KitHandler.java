package me.mmarz11.aod.handlers.round;

import java.util.logging.Level;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Kit;

import org.bukkit.entity.Player;

public class KitHandler {
	private AoD plugin;

	public KitHandler(AoD plugin) {
		this.plugin = plugin;
	}

	public void giveKit(Player player, Kit kit) {
		this.plugin.getLogger().log(Level.INFO,
				player.getName() + " received kit " + kit.name);
		player.sendMessage("You have been given kit: " + kit.name);
		player.getInventory().setContents(kit.inventory);
	}
}
