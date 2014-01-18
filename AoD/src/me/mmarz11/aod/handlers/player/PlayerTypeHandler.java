package me.mmarz11.aod.handlers.player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.Kit;
import me.mmarz11.aod.Map;
import me.mmarz11.aod.enums.PlayerType;
import me.mmarz11.aod.enums.Timer;
import me.mmarz11.aod.handlers.round.KitHandler;
import me.mmarz11.aod.handlers.round.MapHandler;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerTypeHandler implements Listener {
	private AoD plugin;

	public ArrayList<String> survivors;
	public ArrayList<String> hunters;
	public ArrayList<String> referees;

	public PlayerTypeHandler(AoD plugin) {
		this.plugin = plugin;

		this.survivors = new ArrayList<String>();
		this.hunters = new ArrayList<String>();
		this.referees = new ArrayList<String>();

		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	public void addPlayer(Player player, PlayerType playerType) {
		if (playerType == PlayerType.HUNTER) {
			hunters.add(player.getName());
		} else if (playerType == PlayerType.SURVIVOR) {
			survivors.add(player.getName());
		} else if (playerType == PlayerType.REFEREE) {
			referees.add(player.getName());
		} else {
			plugin.getLogger().log(Level.SEVERE,
					"[AoD] Can Not Find Valid Player Type");
		}

		this.plugin.handlers.scoreboardHandler.updateScoreboard();
	}

	public void removePlayerFromAll(Player player) {
		removePlayer(player, PlayerType.HUNTER);
		removePlayer(player, PlayerType.SURVIVOR);
		removePlayer(player, PlayerType.REFEREE);

		this.plugin.handlers.scoreboardHandler.updateScoreboard();
	}

	public void removePlayer(Player player, PlayerType playerType) {
		if (playerType == PlayerType.HUNTER) {
			hunters.remove(player.getName());
		} else if (playerType == PlayerType.SURVIVOR) {
			survivors.remove(player.getName());
		} else if (playerType == PlayerType.REFEREE) {
			referees.remove(player.getName());
		} else {
			plugin.getLogger().log(Level.SEVERE,
					"[AoD] Can Not Find Valid Player Type");
		}

		this.plugin.handlers.scoreboardHandler.updateScoreboard();
	}

	public void infect(Player player) {
		removePlayer(player, PlayerType.SURVIVOR);
		addPlayer(player, PlayerType.HUNTER);

		this.plugin.handlers.scoreboardHandler.updateScoreboard();
	}

	public void uninfectAll() {
		for (Player player : this.plugin.getServer().getOnlinePlayers()) {
			uninfect(player);
		}
	}

	public void uninfect(Player player) {
		removePlayer(player, PlayerType.HUNTER);
		addPlayer(player, PlayerType.SURVIVOR);

		this.plugin.handlers.scoreboardHandler.updateScoreboard();
	}

	public void selectFirstHunter() {
		Player player = plugin
				.getServer()
				.getOfflinePlayer(
						survivors.get((int) (Math.random() * survivors.size())))
				.getPlayer();
		infect(player);
		this.plugin.getServer().broadcastMessage(
				player.getName() + " has been selected as the first hunter!");
		World world = player.getWorld();
		
		MapHandler mapHandler = this.plugin.handlers.mapHandler;
		Map map = mapHandler.currentMap;
		if (world.equals(map)) {
			if (map.mapInformation.hunter.moveFirstHunter) {
				player.teleport(map.mapInformation.hunter.spawn);
			}
		} else {
			player.teleport(map.mapInformation.hunter.spawn);
		}
		
		KitHandler kitHandler = this.plugin.handlers.kitHandler;
		kitHandler.giveKit(player, map.mapInformation.hunter.first);
		
		world.setThunderDuration(60);
		world.strikeLightningEffect(player.getLocation());
		
		for (Player p : this.plugin.getServer().getOnlinePlayers()) {
			if (p.getWorld().equals(mapHandler.lobbySpawn.getWorld())) {
				PlayerTypeHandler playerTypeHandler = this.plugin.handlers.playerTypeHandler;
				if (playerTypeHandler.hunters.contains(p.getName())) {
					p.teleport(map.mapInformation.hunter.spawn);
					if (!p.equals(player)) {
						List<Kit> kits = map.mapInformation.hunter.starting;
						kitHandler.giveKit(p, kits.get((int)(Math.random()*kits.size())));
					}
				} else {
					List<Kit> kits = map.mapInformation.survivor.starting;
					kitHandler.giveKit(p, kits.get((int)(Math.random()*kits.size())));
					p.teleport(map.mapInformation.survivor.spawn);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		Timer timer = this.plugin.handlers.timerHandler.getCurrentTimer();

		if (timer == Timer.LOBBY || timer == Timer.BUILD) {
			this.addPlayer(player, PlayerType.SURVIVOR);
		} else {
			this.addPlayer(player, PlayerType.HUNTER);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();

		Timer timer = this.plugin.handlers.timerHandler.getCurrentTimer();

		if (timer == Timer.ROUND) {
			if (survivors.contains(player.getName())) {
				infect(player);
			}
		}

		if (timer == Timer.ROUND && survivors.size() == 0) {
			this.plugin.getServer().broadcastMessage("No survivors remain!");
			this.plugin.handlers.timerHandler.endRound();
			this.uninfectAll();
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		this.removePlayerFromAll(player);

		Timer timer = this.plugin.handlers.timerHandler.getCurrentTimer();

		if (timer == Timer.ROUND && survivors.size() == 0) {
			this.plugin.getServer().broadcastMessage("No survivors remain!");
			this.plugin.handlers.timerHandler.endRound();
			this.uninfectAll();
		}
	}
}
