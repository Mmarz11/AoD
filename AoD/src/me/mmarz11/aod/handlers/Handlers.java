package me.mmarz11.aod.handlers;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.handlers.map.BuildBreakHandler;
import me.mmarz11.aod.handlers.map.MobHandler;
import me.mmarz11.aod.handlers.player.ArrowHandler;
import me.mmarz11.aod.handlers.player.HungerHandler;
import me.mmarz11.aod.handlers.player.LoginHandler;
import me.mmarz11.aod.handlers.player.PermissionHandler;
import me.mmarz11.aod.handlers.player.PlayerLogoutHandler;
import me.mmarz11.aod.handlers.player.PlayerTypeHandler;
import me.mmarz11.aod.handlers.player.PortalHandler;
import me.mmarz11.aod.handlers.player.RespawnHandler;
import me.mmarz11.aod.handlers.player.StatHandler;
import me.mmarz11.aod.handlers.player.TeamkillHandler;
import me.mmarz11.aod.handlers.round.KitHandler;
import me.mmarz11.aod.handlers.round.MapHandler;
import me.mmarz11.aod.handlers.round.ScoreboardHandler;
import me.mmarz11.aod.handlers.round.TimerHandler;

public class Handlers {
	private AoD plugin;
	
	public ScoreboardHandler scoreboardHandler;
	public TimerHandler timerHandler;
	public PlayerTypeHandler playerTypeHandler;
	public PlayerLogoutHandler playerLogoutHandler;
	public ConfigHandler configHandler;
	public MapHandler mapHandler;
	public TeamkillHandler teamkillHandler;
	public LoginHandler loginHandler;
	public PortalHandler portalHandler;
	public KitHandler kitHandler;
	public HungerHandler hungerHandler;
	public MobHandler mobHandler;
	public BuildBreakHandler buildBreakHandler;
	public RespawnHandler respawnHandler;
	public StatHandler statHandler;
	public PermissionHandler permissionHandler;
	public ArrowHandler arrowHandler;
	
	public Handlers(AoD plugin) {
		this.plugin = plugin;
	}
	
	public void init() {
		scoreboardHandler = new ScoreboardHandler(plugin);
		timerHandler = new TimerHandler(plugin);
		playerTypeHandler = new PlayerTypeHandler(plugin);
		playerLogoutHandler = new PlayerLogoutHandler(plugin);
		configHandler = new ConfigHandler(plugin);
		mapHandler = new MapHandler(plugin);
		teamkillHandler = new TeamkillHandler(plugin);
		loginHandler = new LoginHandler(plugin);
		portalHandler = new PortalHandler(plugin);
		kitHandler = new KitHandler(plugin);
		hungerHandler = new HungerHandler(plugin);
		mobHandler = new MobHandler(plugin);
		buildBreakHandler = new BuildBreakHandler(plugin);
		respawnHandler = new RespawnHandler(plugin);
		statHandler = new StatHandler(plugin);
		permissionHandler = new PermissionHandler(plugin);
		arrowHandler = new ArrowHandler(plugin);
	}
}
