package me.mmarz11.aod.handlers.round;

import java.util.logging.Level;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.Timer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardHandler implements Listener {
	private AoD plugin;

	public ScoreboardManager scoreboardManager;
	public Scoreboard scoreboard;
	public Objective objective;

	private Score timer;
	private Score hunters;
	private Score survivors;

	public ScoreboardHandler(AoD plugin) {
		this.plugin = plugin;
		init();
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	public void init() {
		scoreboardManager = plugin.getServer().getScoreboardManager();
		scoreboard = scoreboardManager.getNewScoreboard();
		objective = scoreboard.registerNewObjective("AoD", "dummy");

		objective.setDisplayName("");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);

		timer = objective.getScore(plugin.getServer().getOfflinePlayer(
				ChatColor.GREEN + "Time Left:"));

		hunters = objective.getScore(plugin.getServer().getOfflinePlayer(
				ChatColor.RED + "Hunters:"));

		survivors = objective.getScore(plugin.getServer().getOfflinePlayer(
				ChatColor.GRAY + "Survivors:"));
	}

	public void updateScoreboard() {
		updateDisplayName();
		updateTimer();
		updatePlayers();
	}

	public void updateDisplayName() {
		Timer time = plugin.handlers.timerHandler.getCurrentTimer();

		if (time == Timer.BUILD) {
			objective.setDisplayName("Build:");
		} else if (time == Timer.LOBBY) {
			objective.setDisplayName("Lobby:");
		} else if (time == Timer.ROUND) {
			objective.setDisplayName("Round:");
		} else {
			plugin.getLogger().log(Level.SEVERE,
					"[AoD] Can Not Find Valid Timer");
		}
	}

	public void updateTimer() {
		Timer time = plugin.handlers.timerHandler.getCurrentTimer();

		if (time == Timer.BUILD) {
			timer.setScore(plugin.handlers.timerHandler.build);
		} else if (time == Timer.LOBBY) {
			timer.setScore(plugin.handlers.timerHandler.lobby);
		} else if (time == Timer.ROUND) {
			timer.setScore(plugin.handlers.timerHandler.round);
		} else {
			plugin.getLogger().log(Level.SEVERE,
					"[AoD] Can Not Find Valid Timer");
		}
	}

	public void updatePlayers() {
		updateHunters();
		updateSurvivors();
	}

	public void updateHunters() {
		hunters.setScore(plugin.handlers.playerTypeHandler.hunters.size());
	}

	public void updateSurvivors() {
		survivors.setScore(plugin.handlers.playerTypeHandler.survivors.size());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setScoreboard(scoreboard);
	}
}
