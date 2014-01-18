package me.mmarz11.aod;

import java.util.ArrayList;
import java.util.List;

import me.mmarz11.aod.enums.PlayerType;
import me.mmarz11.aod.handlers.Handlers;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class AoD extends JavaPlugin {
	public Handlers handlers;

	@Override
	public void onEnable() {
		handlers = new Handlers(this);
		handlers.init();
		handlers.timerHandler.lobby();

		for (Player player : this.getServer().getOnlinePlayers()) {
			this.handlers.playerTypeHandler.addPlayer(player,
					PlayerType.SURVIVOR);
			player.setScoreboard(this.handlers.scoreboardHandler.scoreboard);
		}
	}

	@Override
	public void onDisable() {
		handlers.statHandler.saveAll();
		handlers.configHandler.saveAll();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (label.equalsIgnoreCase("killmobs")) {
			int count = 0;
			for (World world : this.getServer().getWorlds()) {
				for (Entity entity : world.getEntities()) {
					if (!(entity instanceof Player)) {
						entity.remove();
						count++;
					}
				}
			}
			sender.sendMessage(count + " entities removed.");
			return true;
		} else if (label.equalsIgnoreCase("bow")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				ItemStack itemstack = new ItemStack(Material.BOW);
				List<String> lore = new ArrayList<String>();
				lore.add("explosive");
				ItemMeta meta = itemstack.getItemMeta();
				meta.setLore(lore);
				itemstack.setItemMeta(meta);
				
				player.getInventory().addItem(itemstack);
				return true;
			}
		}
		return false;
	}
}
