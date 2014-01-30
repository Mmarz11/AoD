package me.mmarz11.aod.commands;

import me.mmarz11.aod.AoD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BrainsCommand implements CommandExecutor {
	public BrainsCommand() {
		AoD.inst.getCommand("brains").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage("You have "
					+ AoD.inst.handlers.statHandler.stats.get(player.getName()).brains
					+ " brains.");
			return true;
		}
		return false;
	}
}
