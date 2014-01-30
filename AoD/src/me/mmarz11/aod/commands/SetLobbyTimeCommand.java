package me.mmarz11.aod.commands;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetLobbyTimeCommand implements CommandExecutor {
	public SetLobbyTimeCommand() {
		AoD.inst.getCommand("setlobbytime").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		
		if (AoD.inst.handlers.timerHandler.getCurrentTimer() == Timer.LOBBY) {
			try {
				int time = Integer.valueOf(args[0]);
				AoD.inst.handlers.timerHandler.lobby = time;
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			sender.sendMessage("[AoD] Command can only be used during the lobby.");
			return true;
		}
	}
}
