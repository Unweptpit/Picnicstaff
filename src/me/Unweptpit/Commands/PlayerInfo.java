package me.Unweptpit.Commands;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Unweptpit.Commands.NameLookup.PreviousPlayerNameEntry;
import me.Unweptpit.Picnicstaff.PicnicStaff;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;


public class PlayerInfo implements CommandExecutor {
	
	//Constructor
	private final PicnicStaff main;
	
	public PlayerInfo(PicnicStaff main){
		this.main = main;
	}
	
	//Instances
	
	//Variables and Arrays
	
	//Methods
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission(main.getConfig().getString("permissions.permissionGetinfo")))){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("permissions.nopermission")));
			return true;
		}
		
		if (args.length == 0) {
			sender.sendMessage("Correct usage: /getinfo <name>");
		}
		
		if (args.length >= 1) {
			
			Player player = Bukkit.getPlayer(args[0]);
			//If player equals null that means the player is offline, so we check the offlineplayers stats
			if(player == null) {
				@SuppressWarnings("deprecation")
				OfflinePlayer offplayer = Bukkit.getOfflinePlayer(args[0]);
				UUID uuid = offplayer.getUniqueId();
				String playeruuid = uuid.toString();
				
				
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Playerinfo:"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Username:"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f" + offplayer.getName()));
				
				messageUUID(playeruuid, sender);
				messagePreviousNames(playeruuid, player, sender);
				}
			
			else {
				UUID uuid = player.getUniqueId();
				String playeruuid = uuid.toString();
				
				
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Playerinfo:"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Username:"));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f" + player.getName()));
				if (sender.hasPermission(main.getConfig().getString("permissions.permissionExamineIp"))) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6IP: &f" + ((Player) player).getAddress().getHostString()));
				}
				
				messageUUID(playeruuid, sender);
				messagePreviousNames(playeruuid, player, sender);
			}
		}
		return false;
	}

	private void messagePreviousNames(String playeruuid, Player player, CommandSender sender) {
		playeruuid = playeruuid.replaceAll("-", "");
		PreviousPlayerNameEntry[] oldnames = null;
		try {
			oldnames = NameLookup.getPlayerPreviousNames(playeruuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Previous names: "));
		for(PreviousPlayerNameEntry entry : oldnames){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f - " + entry.getPlayerName()));
		}
		
	}

	private void messageUUID(String UUID, CommandSender sender) {
		
		if (sender instanceof Player) {
			Player cmdPlayer = (Player) sender;
			TextComponent id = new TextComponent(UUID);
			id.setColor(ChatColor.WHITE);
			id.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, UUID));
			id.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Click here to copy UUID")));
			cmdPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6UUID: "));
			cmdPlayer.spigot().sendMessage(id);
		}
		else {
			sender.sendMessage("UUID: " + UUID);
		}
		
	}

}
