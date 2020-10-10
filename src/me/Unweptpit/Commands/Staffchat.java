package me.Unweptpit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Unweptpit.Picnicstaff.PicnicStaff;

public class Staffchat implements CommandExecutor {

	//Constructor
	private final PicnicStaff main;
	
	public Staffchat(PicnicStaff main){
		this.main = main;
	}
	
	//Instances
	
	//Variables and Arrays
	
	//Methods
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("staffchat") || label.equalsIgnoreCase("sc")) {
			
			if(args.length == 0) {
				sender.sendMessage("Correct usage: /staffchat <message>");
				return false;
			}

			//If player uses /sc
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!(player.hasPermission(main.getConfig().getString("permissions.permissionStaffchat")))) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("permissions.nopermission")));
					return false;
				}
					
				String message = null;
				for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
					if (onlinePlayers.hasPermission(main.getConfig().getString("permissions.permissionStaffchat"))) {
				        	  
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < args.length; i++){
							sb.append(args[i]).append(" ");
						}
				        	   
						message = sb.toString().trim();
				        	  
						onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6Staffchat&8] &7" + player.getDisplayName() + ":&c " + message));
					}
				}
				if (message != null) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6Staffchat&8] &7" + player.getDisplayName() + ":&c " + message));
				}
				return true;
			}
			
			
			//If console uses /sc
			else {

				String message = null;
				for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
					if (onlinePlayers.hasPermission(main.getConfig().getString("permissions.permissionStaffchat"))) {
						
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < args.length; i++){
							sb.append(args[i]).append(" ");
						}
			        	   
						message = sb.toString().trim();
						onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6Staffchat&8] &7Console:&c " + message));
					}
					
				}
				if (message != null) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6Staffchat&8] &7Console:&c " + message));
				}
				return true;
			}
			
		}
		return false;	
	} //end of onCommand
} //end of class