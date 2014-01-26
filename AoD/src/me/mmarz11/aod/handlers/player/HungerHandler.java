package me.mmarz11.aod.handlers.player;

import me.mmarz11.aod.AoD;
import me.mmarz11.aod.enums.Timer;
import me.mmarz11.aod.handlers.round.TimerHandler;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerHandler implements Listener {
	public HungerHandler() {
		AoD.inst.getServer().getPluginManager().registerEvents(this, AoD.inst);
	}

	@EventHandler
	public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
		Entity entity = event.getEntity();

		TimerHandler handler = AoD.inst.handlers.timerHandler;
		if (handler.getCurrentTimer() != Timer.ROUND) {
			event.setCancelled(true);
		} else {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				if (AoD.inst.handlers.playerTypeHandler.hunters.contains(player
						.getName())) {
					event.setCancelled(true);
				}
			}
		}
	}
}
