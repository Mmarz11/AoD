package me.mmarz11.aod.handlers.player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Kit;
import me.mmarz11.aod.Map;
import me.mmarz11.aod.enums.PlayerType;
import me.mmarz11.aod.enums.Timer;
import me.mmarz11.aod.handlers.map.MapHandler;
import me.mmarz11.aod.handlers.round.KitHandler;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerTypeHandler implements Listener {
	public ArrayList<String> survivors;
	public ArrayList<String> hunters;
	public ArrayList<String> referees;

	public PlayerTypeHandler() {
		this.survivors = new ArrayList<String>();
		this.hunters = new ArrayList<String>();
		this.referees = new ArrayList<String>();

		AoD.inst.getServer().getPluginManager().registerEvents(this, AoD.inst);
	}

	public void addPlayer(Player player, PlayerType playerType) {
		if (playerType == PlayerType.HUNTER) {
			hunters.add(player.getName());
		} else if (playerType == PlayerType.SURVIVOR) {
			survivors.add(player.getName());
		} else if (playerType == PlayerType.REFEREE) {
			referees.add(player.getName());
		} else {
			AoD.inst.getLogger().log(Level.SEVERE,
					"[AoD] Can Not Find Valid Player Type");
		}

		AoD.inst.handlers.scoreboardHandler.updateScoreboard();
	}

	public void removePlayerFromAll(Player player) {
		removePlayer(player, PlayerType.HUNTER);
		removePlayer(player, PlayerType.SURVIVOR);
		removePlayer(player, PlayerType.REFEREE);

		AoD.inst.handlers.scoreboardHandler.updateScoreboard();
	}

	public void removePlayer(Player player, PlayerType playerType) {
		if (playerType == PlayerType.HUNTER) {
			hunters.remove(player.getName());
		} else if (playerType == PlayerType.SURVIVOR) {
			survivors.remove(player.getName());
		} else if (playerType == PlayerType.REFEREE) {
			referees.remove(player.getName());
		} else {
			AoD.inst.getLogger().log(Level.SEVERE,
					"[AoD] Can Not Find Valid Player Type");
		}

		AoD.inst.handlers.scoreboardHandler.updateScoreboard();
	}

	public void infect(Player player) {
		removePlayer(player, PlayerType.SURVIVOR);
		addPlayer(player, PlayerType.HUNTER);

		AoD.inst.handlers.scoreboardHandler.updateScoreboard();
	}

	public void uninfectAll() {
		for (Player player : AoD.inst.getServer().getOnlinePlayers()) {
			uninfect(player);
		}
	}

	public void uninfect(Player player) {
		removePlayer(player, PlayerType.HUNTER);
		addPlayer(player, PlayerType.SURVIVOR);

		AoD.inst.handlers.scoreboardHandler.updateScoreboard();
	}

	public void selectFirstHunter() {
		Player player = AoD.inst
				.getServer()
				.getOfflinePlayer(
						survivors.get((int) (Math.random() * survivors.size())))
				.getPlayer();
		infect(player);
		AoD.inst.getServer().broadcastMessage(
				player.getName() + " has been selected as the first hunter!");
		World world = player.getWorld();

		MapHandler mapHandler = AoD.inst.handlers.mapHandler;
		Map map = mapHandler.currentMap;
		if (world.equals(map)) {
			if (map.mapInformation.hunter.moveFirstHunter) {
				player.teleport(map.mapInformation.hunter.spawn);
			}
		} else {
			player.teleport(map.mapInformation.hunter.spawn);
		}

		KitHandler kitHandler = AoD.inst.handlers.kitHandler;
		kitHandler.giveKit(player, map.mapInformation.hunter.first);

		world.setThunderDuration(60);
		world.strikeLightningEffect(player.getLocation());

		for (Player p : AoD.inst.getServer().getOnlinePlayers()) {
			if (p.getWorld().equals(mapHandler.lobbySpawn.getWorld())) {
				PlayerTypeHandler playerTypeHandler = AoD.inst.handlers.playerTypeHandler;
				if (playerTypeHandler.hunters.contains(p.getName())) {
					p.teleport(map.mapInformation.hunter.spawn);
					if (!p.equals(player)) {
						List<Kit> kits = map.mapInformation.hunter.starting;
						kitHandler.giveKit(p,
								kits.get((int) (Math.random() * kits.size())));
					}
				} else {
					List<Kit> kits = map.mapInformation.survivor.starting;
					kitHandler.giveKit(p,
							kits.get((int) (Math.random() * kits.size())));
					p.teleport(map.mapInformation.survivor.spawn);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Timer timer = AoD.inst.handlers.timerHandler.getCurrentTimer();

		if (timer == Timer.LOBBY || timer == Timer.BUILD) {
			this.addPlayer(player, PlayerType.SURVIVOR);
		} else {
			this.addPlayer(player, PlayerType.HUNTER);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		Timer timer = AoD.inst.handlers.timerHandler.getCurrentTimer();

		if (timer == Timer.ROUND) {
			if (survivors.contains(player.getName())) {
				infect(player);
			}
		}

		if (timer == Timer.ROUND && survivors.size() == 0) {
			AoD.inst.getServer().broadcastMessage("No survivors remain!");
			AoD.inst.handlers.timerHandler.endRound();
			this.uninfectAll();
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		this.removePlayerFromAll(player);

		Timer timer = AoD.inst.handlers.timerHandler.getCurrentTimer();

		if (timer == Timer.ROUND && survivors.size() == 0) {
			AoD.inst.getServer().broadcastMessage("No survivors remain!");
			AoD.inst.handlers.timerHandler.endRound();
			this.uninfectAll();
		}
	}
}
