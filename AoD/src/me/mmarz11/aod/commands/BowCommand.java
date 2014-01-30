package me.mmarz11.aod.commands;

import java.util.ArrayList;
import java.util.List;

import me.mmarz11.aod.AoD;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BowCommand implements CommandExecutor {
	public BowCommand() {
		AoD.inst.getCommand("bow").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				return false;
			}

			Player player = (Player) sender;
			ItemStack itemstack = new ItemStack(Material.BOW);
			List<String> lore = new ArrayList<String>();
			lore.add(args[0]);
			ItemMeta meta = itemstack.getItemMeta();
			meta.setLore(lore);
			itemstack.setItemMeta(meta);

			player.getInventory().addItem(itemstack);
			return true;
		}
		return false;
	}
}
