package me.mmarz11.aod.handlers.round;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.Timer;

import org.bukkit.scheduler.BukkitScheduler;

public class TimerHandler {
	private BukkitScheduler scheduler;

	public int lobby;
	public int build;
	public int round;

	private int lobbyTask;
	private int buildTask;
	private int roundTask;

	private Timer currentTimer;

	public TimerHandler() {
		this.scheduler = AoD.inst.getServer().getScheduler();
		this.currentTimer = Timer.INVALID;
	}

	public void lobby() {
		currentTimer = Timer.LOBBY;

		lobby = AoD.inst.handlers.configHandler.getConfig().getInt(
				"lobby.timer");

		lobbyTask = scheduler.scheduleSyncRepeatingTask(AoD.inst,
				new Runnable() {
					public void run() {
						if (lobby == 0) {
							AoD.inst.handlers.mapHandler.setRandomCurrentMap();
							endLobby();
						}

						lobby--;
						AoD.inst.handlers.scoreboardHandler.updateScoreboard();
					}
				}, 0L, 20L);
	}

	public void build() {
		currentTimer = Timer.BUILD;

		build = AoD.inst.handlers.mapHandler.currentMap.mapInformation.build;
		final int buildReset = build;
		AoD.inst.handlers.scoreboardHandler.updateScoreboard();

		buildTask = scheduler.scheduleSyncRepeatingTask(AoD.inst,
				new Runnable() {
					public void run() {
						if (build == 0) {
							if (AoD.inst.handlers.playerTypeHandler.survivors
									.size() <= 0) { // TODO
													// Change
													// this
													// back
								build = buildReset;
							} else {
								endBuild();
							}
						} else if (build > 0 && build <= 5) {
							AoD.inst.getServer().broadcastMessage(
									"[AoD] Build 0:0" + build);
						}

						build--;
						AoD.inst.handlers.scoreboardHandler.updateScoreboard();
					}
				}, 0L, 20L);
	}

	public void round() {
		currentTimer = Timer.ROUND;

		round = AoD.inst.handlers.mapHandler.currentMap.mapInformation.game;
		AoD.inst.handlers.scoreboardHandler.updateScoreboard();

		AoD.inst.handlers.playerTypeHandler.selectFirstHunter();

		roundTask = scheduler.scheduleSyncRepeatingTask(AoD.inst,
				new Runnable() {
					public void run() {
						if (round == 0) {
							AoD.inst.handlers.brainHandler.survived();
							endRound();
						} else if (round % 60 == 0) {
							AoD.inst.getServer().broadcastMessage(
									"[AoD] " + round / 60 + ":00 remains!");
						} else if (round > 0 && round <= 5) {
							AoD.inst.getServer().broadcastMessage(
									"[AoD] Survive 0:0" + round);
						}

						round--;
						AoD.inst.handlers.scoreboardHandler.updateScoreboard();
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
