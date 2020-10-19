package me.Unweptpit.Features;


import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import me.Unweptpit.Picnicstaff.PicnicStaff;
import net.md_5.bungee.api.ChatColor;



public class XrayNotifier implements Listener {
	private final PicnicStaff main;
	
	public XrayNotifier(PicnicStaff main){
		this.main = main;
	}
	
	
	
	
	public HashMap<UUID, HashMap<String, Integer>> map = new HashMap<>(); 
	
	@EventHandler()
	public void onBlockBreak(BlockBreakEvent e) { // Check the block mined, if there are no blocks of the same type in the surrounding area send a message saying how many blocks the player mined.
		Player player = (Player) e.getPlayer();
		String block = e.getBlock().getType().toString();
		UUID uuid = player.getUniqueId();
		
		if(!(player.hasPermission(main.getConfig().getString("permissions.permissionXray")))) { //Check if player needs to be checked for xray
			for (String j : main.getConfig().getStringList("Blocks")) {  //Loop through the blocks in the config
				if (j.equalsIgnoreCase(block)) {  // If mined block matches a block in config
					if (map.containsKey(uuid) == false) {
						HashMap<String, Integer> map2 = new HashMap<>();
						map.put(uuid, map2);

					}
					
					if (map.get(uuid).get(block) == null) {
						map.get(uuid).put(block, 1);
					}
					else {
						int count = map.get(uuid).get(block);
						map.get(uuid).put(block, (count + 1));
					}
				
					String name = main.getConfig().getString("Names." + block);
					Location loc = e.getBlock().getLocation();
					World world = loc.getWorld();
					
					if (LoopBlocks(e.getBlock(), loc, world)) {
						for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
							if (onlinePlayers.hasPermission(main.getConfig().getString("permissions.permissionStaff"))) {
								int i = map.get(uuid).get(block);
								onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4Xray&8] &f" + player.getDisplayName() + "&c has mined &f" + i + " " + name));
								Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4Xray&8] &f" + player.getDisplayName() + "&c has mined &f" + i + " " + name));
								map.get(uuid).put(block, 0);
							}
						}
						int i = map.get(uuid).get(block);
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4Xray&8] &f" + player.getDisplayName() + "&c has mined &f" + i + " " + name));
						map.get(uuid).put(block, 0);
					}
				}
			}   
		}
	}
	
	
	public Boolean LoopBlocks(Block block, Location loc, World world) { //Check all blocks around block to see if they match
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		Block b1 = new Location(world, x + 1, y, z).getBlock();
		Block b2 = new Location(world, x + 1, y + 1, z).getBlock();
		Block b3 = new Location(world, x + 1, y - 1, z).getBlock();
		Block b4 = new Location(world, x + 1, y, z + 1).getBlock();
		Block b5 = new Location(world, x + 1, y, z - 1).getBlock();
		Block b6 = new Location(world, x + 1, y + 1, z + 1).getBlock();
		Block b7 = new Location(world, x + 1, y - 1, z - 1).getBlock();
		Block b8 = new Location(world, x + 1, y - 1, z + 1).getBlock();
		Block b9 = new Location(world, x + 1, y + 1, z - 1).getBlock();
		Block b10 = new Location(world, x - 1, y + 1, z).getBlock();
		Block b11 = new Location(world, x - 1, y - 1, z).getBlock();
		Block b12 = new Location(world, x - 1, y, z + 1).getBlock();
		Block b13 = new Location(world, x - 1, y, z - 1).getBlock();
		Block b14 = new Location(world, x - 1, y + 1, z + 1).getBlock();
		Block b15 = new Location(world, x - 1, y - 1, z - 1).getBlock();
		Block b16 = new Location(world, x - 1, y + 1, z - 1).getBlock();
		Block b17 = new Location(world, x - 1, y - 1, z + 1).getBlock();
		Block b18 = new Location(world, x - 1, y, z).getBlock();
		Block b19 = new Location(world, x, y + 1, z).getBlock();
		Block b20 = new Location(world, x, y + 1, z + 1).getBlock();
		Block b21 = new Location(world, x, y + 1, z - 1).getBlock();
		Block b22 = new Location(world, x, y - 1, z).getBlock();
		Block b23 = new Location(world, x, y - 1, z + 1).getBlock();
		Block b24 = new Location(world, x, y - 1, z - 1).getBlock();
		Block b25 = new Location(world, x, y, z + 1).getBlock();
		Block b26 = new Location(world, x, y, z - 1).getBlock();
		
		if(!(
				b1.getType() == block.getType() ||
				b2.getType() == block.getType() ||
				b3.getType() == block.getType() ||
				b4.getType() == block.getType() ||
				b5.getType() == block.getType() ||
				b6.getType() == block.getType() ||
				b7.getType() == block.getType() ||
				b8.getType() == block.getType() ||
				b9.getType() == block.getType() ||
				b10.getType() == block.getType() ||
				b11.getType() == block.getType() ||
				b12.getType() == block.getType() ||
				b13.getType() == block.getType() ||
				b14.getType() == block.getType() ||
				b15.getType() == block.getType() ||
				b16.getType() == block.getType() ||
				b17.getType() == block.getType() ||
				b18.getType() == block.getType() ||
				b19.getType() == block.getType() ||
				b20.getType() == block.getType() ||
				b21.getType() == block.getType() ||
				b22.getType() == block.getType() ||
				b23.getType() == block.getType() ||
				b24.getType() == block.getType() ||
				b25.getType() == block.getType() ||
				b26.getType() == block.getType()
				)) {
			return true;
		}
		
		return false;
	}
}































