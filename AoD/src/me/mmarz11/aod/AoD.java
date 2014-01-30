package me.mmarz11.aod;

import me.mmarz11.aod.commands.Commands;
import me.mmarz11.aod.enums.PlayerType;
import me.mmarz11.aod.handlers.Handlers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AoD extends JavaPlugin {
	public static AoD inst;

	public Handlers handlers;
	public Commands commands;

	@Override
	public void onEnable() {
		inst = this;

		handlers = new Handlers();
		handlers.init();
		handlers.timerHandler.lobby();
		
		commands = new Commands();

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
}
