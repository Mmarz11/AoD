package me.mmarz11.aod.commands;

import me.mmarz11.aod.AoD;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class KillMobsCommand implements CommandExecutor {
	public KillMobsCommand() {
		AoD.inst.getCommand("killmobs").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		int count = 0;
		for (World world : AoD.inst.getServer().getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (!(entity instanceof Player)) {
					entity.remove();
					count++;
				}
			}
		}
		sender.sendMessage(count + " entities removed.");
		return true;
	}

}
