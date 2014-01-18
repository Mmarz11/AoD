package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.Timer;
import me.mmarz11.aod.handlers.round.TimerHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerHandler implements Listener {
	private AoD plugin;
	
	public HungerHandler(AoD plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
		TimerHandler handler = this.plugin.handlers.timerHandler;
		if (handler.getCurrentTimer() != Timer.ROUND) {
			event.setCancelled(true);
		}
	}
}
