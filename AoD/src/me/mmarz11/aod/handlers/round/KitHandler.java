package me.mmarz11.aod.handlers.round;

import java.util.logging.Level;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitHandler {
	public void giveKit(Player player, Kit kit) {
		AoD.inst.getLogger().log(Level.INFO,
				player.getName() + " received kit " + kit.name);
		player.sendMessage("You have been given kit: " + kit.name);
		for (ItemStack itemstack : kit.inventory) {
			if (itemstack != null) {
				player.getInventory().addItem(itemstack);
			}
		}
	}
}
