package me.mmarz11.aod.commands;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.Timer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EndRoundCommand implements CommandExecutor {
	public EndRoundCommand() {
		AoD.inst.getCommand("endround").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (AoD.inst.handlers.timerHandler.getCurrentTimer() == Timer.ROUND) {
			AoD.inst.handlers.timerHandler.endRound();
			return true;
		} else {
			sender.sendMessage("[AoD] Command can only be used during the round.");
			return true;
		}
	}
}
