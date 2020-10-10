package me.Unweptpit.Features;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class StaffItems {
	//Constructor
	
	//Instances
	
	//Variables and Arrays
	
	//Methods
	
	public ItemStack Kill() {
		String itemLore = "Right click on a player to kill them";
		ItemStack CpsCounter = new ItemStack(Material.STICK);
		ItemMeta CpsCounterMeta = CpsCounter.getItemMeta();
		List<String> CpsCounterLore = new ArrayList<String>();
		
				
		CpsCounterLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + itemLore);
		CpsCounterMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Kill Stick"));
		CpsCounterMeta.setLore(CpsCounterLore);
		CpsCounter.setItemMeta(CpsCounterMeta);
		
		return  CpsCounter;
						
	}
	
	public ItemStack Examine() {
		String itemLore = "Right click on a player to examine them";
		ItemStack Examine = new ItemStack(Material.BLAZE_ROD);
		ItemMeta ExamineMeta = Examine.getItemMeta();
		List<String> ExamineLore = new ArrayList<String>();
		
				
		ExamineLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + itemLore);
		ExamineMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Examiner"));
		ExamineMeta.setLore(ExamineLore);
		Examine.setItemMeta(ExamineMeta);
		
		return  Examine;
						
	}
	
	public ItemStack Vanish() {
		String itemLore = "Right click to toggle vanish";
		ItemStack Examine = new ItemStack(Material.FEATHER);
		ItemMeta ExamineMeta = Examine.getItemMeta();
		List<String> ExamineLore = new ArrayList<String>();
		
				
		ExamineLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + itemLore);
		ExamineMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Vanish"));
		ExamineMeta.setLore(ExamineLore);
		Examine.setItemMeta(ExamineMeta);
		
		return  Examine;
						
	}
	
	public ItemStack Creative() {
		String itemLore = "Right click to go into creative mode";
		ItemStack Examine = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta ExamineMeta = Examine.getItemMeta();
		List<String> ExamineLore = new ArrayList<String>();
		
				
		ExamineLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + itemLore);
		ExamineMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Creative Mode"));
		ExamineMeta.setLore(ExamineLore);
		Examine.setItemMeta(ExamineMeta);
		
		return  Examine;
						
	}
	
	public ItemStack Spectator() {
		String itemLore = "Right click to go into spectator mode";
		ItemStack Examine = new ItemStack(Material.IRON_NUGGET);
		ItemMeta ExamineMeta = Examine.getItemMeta();
		List<String> ExamineLore = new ArrayList<String>();
		
				
		ExamineLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + itemLore);
		ExamineMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Spectator Mode"));
		ExamineMeta.setLore(ExamineLore);
		Examine.setItemMeta(ExamineMeta);
		
		return  Examine;
						
	}
	
	public ItemStack Clearinventory() {
		String itemLore = "Right click on a player to clear his/her inventory";
		ItemStack Examine = new ItemStack(Material.FLINT);
		ItemMeta ExamineMeta = Examine.getItemMeta();
		List<String> ExamineLore = new ArrayList<String>();
		
				
		ExamineLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + itemLore);
		ExamineMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7ClearInventory"));
		ExamineMeta.setLore(ExamineLore);
		Examine.setItemMeta(ExamineMeta);
		
		return  Examine;
						
	}
	
	public ItemStack Coreprotect() {
		String itemLore = "Right click to toggle /co i";
		ItemStack Examine = new ItemStack(Material.SLIME_BALL);
		ItemMeta ExamineMeta = Examine.getItemMeta();
		List<String> ExamineLore = new ArrayList<String>();
		
				
		ExamineLore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + itemLore);
		ExamineMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Coreprotect Inspect"));
		ExamineMeta.setLore(ExamineLore);
		Examine.setItemMeta(ExamineMeta);
		
		return  Examine;
						
	}
	
	
}
