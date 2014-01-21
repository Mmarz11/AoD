package me.mmarz11.aod.handlers.round;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.Timer;

import org.bukkit.scheduler.BukkitScheduler;

public class TimerHandler {
	private AoD plugin;

	private BukkitScheduler scheduler;

	public int lobby;
	public int build;
	public int round;

	private int lobbyTask;
	private int buildTask;
	private int roundTask;

	private Timer currentTimer;

	public TimerHandler(AoD plugin) {
		this.plugin = plugin;
		this.scheduler = plugin.getServer().getScheduler();
		this.currentTimer = Timer.INVALID;
	}

	public void lobby() {
		currentTimer = Timer.LOBBY;

		lobby = this.plugin.handlers.configHandler.getConfig().getInt("lobby.timer");

		lobbyTask = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if (lobby == 0) {
					plugin.handlers.mapHandler.setRandomCurrentMap();
					endLobby();
				}
				
				lobby--;
				plugin.handlers.scoreboardHandler.updateScoreboard();
			}
		}, 0L, 20L);
	}

	public void build() {
		currentTimer = Timer.BUILD;

		build = this.plugin.handlers.mapHandler.currentMap.mapInformation.build;
		final int buildReset = build;
		plugin.handlers.scoreboardHandler.updateScoreboard();

		buildTask = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if (build == 0) {
					if (plugin.handlers.playerTypeHandler.survivors.size() <= 0) { //TODO Change this back
						build = buildReset;
					} else {
						endBuild();
					}
				} else if (build > 0 && build <= 5) {
					plugin.getServer().broadcastMessage("[AoD] Build 0:0" + build);
				}
				
				build--;
				plugin.handlers.scoreboardHandler.updateScoreboard();
			}
		}, 0L, 20L);
	}

	public void round() {
		currentTimer = Timer.ROUND;

		round = this.plugin.handlers.mapHandler.currentMap.mapInformation.game;
		plugin.handlers.scoreboardHandler.updateScoreboard();
		
		plugin.handlers.playerTypeHandler.selectFirstHunter();

		roundTask = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {			
				if (round == 0) {
					plugin.handlers.brainHandler.survived();
					endRound();
				} else if (round % 60 == 0) {
					plugin.getServer().broadcastMessage("[AoD] " + round/60 + ":00 remains!");
				} else if (round > 0 && round <= 5) {
					plugin.getServer().broadcastMessage("[AoD] Survive 0:0" + round);
				}
				
				round--;
				plugin.handlers.scoreboardHandler.updateScoreboard();
			}
		}, 0L, 20L);
	}
	
	public void endLobby() {
		scheduler.cancelTask(lobbyTask);
		build();
	}
	
	public void endBuild() {
		scheduler.cancelTask(buildTask);
		round();
	}
	
	public void endRound() {
		scheduler.cancelTask(roundTask);
		lobby();
	}

	public Timer getCurrentTimer() {
		return currentTimer;
	}
}
