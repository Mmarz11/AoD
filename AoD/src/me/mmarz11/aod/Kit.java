package me.mmarz11.aod;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Kit {
	public String name;

	public ItemStack[] inventory;

	public Kit(String name, List<String> list) {
		this.name = name;
		
		inventory = new ItemStack[36];
		
		int count = 0;
		for (String string : list) {
			String[] split = string.split("\\|");
			String[] itemSplit = split[0].split("\\.");
			
			Material material = null;
			int amount = 1;
			short damage = 0;
			if (itemSplit.length > 1) {
				damage = Short.valueOf(itemSplit[1]);
			}
			if (split.length > 1) {
				amount = Integer.valueOf(split[1]);
			}
			material = Material.getMaterial(itemSplit[0]);
			
			inventory[count++] = new ItemStack(material, amount, damage);
		}
	}
}
