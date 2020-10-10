package me.Unweptpit.Commands;



import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.Unweptpit.Features.StaffItems;
import me.Unweptpit.Picnicstaff.PicnicStaff;
import net.md_5.bungee.api.ChatColor;

public class StaffMode implements CommandExecutor,Listener {
	
	//Constructor
	private final PicnicStaff main;
	
	public StaffMode(PicnicStaff main){
		this.main = main;
	}
	
	//Instances
	StaffItems staffItems = new StaffItems();
	
	//Variables and Arrays
	static Map<UUID, ItemStack[]> savedinventory = new HashMap<UUID, ItemStack[]>();
	Map<String, Long> cooldowns = new HashMap<String, Long>();
	PersistentDataContainer playerdata;
	
	//Methods
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("staffmode") || label.equalsIgnoreCase("sm")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Please join the server to use this command");
				return false;
			}
			
			Player player = (Player) sender;
			playerdata = player.getPersistentDataContainer();
			
			
			if (!(player.hasPermission(main.getConfig().getString("permissions.permissionStaffmode")))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("permissions.nopermission")));
				return false;
			}
			
			if (args.length == 0) {
				if (!(playerdata.has(new NamespacedKey(main, "Staffmode"), PersistentDataType.INTEGER))){
					playerdata.set(new NamespacedKey(main, "Staffmode"), PersistentDataType.INTEGER ,  1);
					setStaffmode(player, true);
					return true;
				}
				
				if (playerdata.has(new NamespacedKey(main, "Staffmode"), PersistentDataType.INTEGER)){
					playerdata.remove(new NamespacedKey(main, "Staffmode"));
					setStaffmode(player, false);
					return true;
				}	
			}
			
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("vanish")) {
					Vanish(player);
				}
				else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7/sm or /sm vanish"));
				}
			}
				
				
		}
		
		// TODO Auto-generated method stub
		return false;
	}

	@EventHandler(ignoreCancelled=false)
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		
		Action action = e.getAction();
		
		 if ( (action ==  Action.RIGHT_CLICK_AIR ) || (action == Action.RIGHT_CLICK_BLOCK || action == null ) ) {
		
			//creative mode item
			if (player.getInventory().getItemInMainHand().isSimilar(staffItems.Creative()) && e.getPlayer().hasPermission(main.getConfig().getString("permissions.permissionStaffmode"))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7You are now in creative mode"));
				player.setGameMode(GameMode.CREATIVE);
			}
		
			//spectator mode item
			if (player.getInventory().getItemInMainHand().isSimilar(staffItems.Spectator()) && e.getPlayer().hasPermission(main.getConfig().getString("permissions.permissionStaffmode"))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7You are now in spectator mode"));
				player.setGameMode(GameMode.SPECTATOR);
			}
		
			//vanish item
			if (player.getInventory().getItemInMainHand().isSimilar(staffItems.Vanish()) && e.getPlayer().hasPermission(main.getConfig().getString("permissions.permissionStaffmode"))) {
				Vanish(player);
			}
		
			//coreprotect item
			if (player.getInventory().getItemInMainHand().isSimilar(staffItems.Coreprotect()) && e.getPlayer().hasPermission(main.getConfig().getString("permissions.permissionStaffmode"))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Coreprotect toggled"));
				player.performCommand("co i");
			}
		 }
		
	}

	//Event for handling right clicking a player
	
	@EventHandler()
	public void onPlayerInteract(PlayerInteractEntityEvent e) {
		Player player = e.getPlayer();
		if(e.getHand() == EquipmentSlot.HAND) return;
		
		if (e.getRightClicked() instanceof Player) {
			Player clickedPlayer = (Player) e.getRightClicked();
			
			//kill item
			
			if (player.getInventory().getItemInMainHand().isSimilar(staffItems.Kill()) && e.getPlayer().hasPermission(main.getConfig().getString("permissions.permissionStaffmode"))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7You killed &f" + clickedPlayer.getDisplayName()));
				clickedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7You were killed by a staffmember"));
				clickedPlayer.setHealth(0.0);
			}
			
			//clearinventory item
			if (player.getInventory().getItemInMainHand().isSimilar(staffItems.Clearinventory()) && e.getPlayer().hasPermission(main.getConfig().getString("permissions.permissionStaffmode"))) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7You cleared &f" + clickedPlayer.getDisplayName() + "&7's inventory"));
				clickedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Your inventory was cleared by a staffmember"));
				clickedPlayer.getInventory().clear();
			}
			
		
			//examine item
			if (player.getInventory().getItemInMainHand().isSimilar(staffItems.Examine()) && e.getPlayer().hasPermission(main.getConfig().getString("permissions.permissionStaffmode"))) {
			
				if (!(Cooldown(player))){
					cooldowns.put(player.getName(), System.currentTimeMillis() + (1 * 1000));
			
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &7Examined Player:"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6- Username: &f" + clickedPlayer.getDisplayName()));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6- Gamemode: &f" + clickedPlayer.getGameMode().toString()));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6- Location: &f" + "X: " + clickedPlayer.getLocation().getBlockX() + " Y: " + clickedPlayer.getLocation().getBlockY() + " Z: " + clickedPlayer.getLocation().getBlockZ()));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6- Health: &f" + clickedPlayer.getHealth()));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6- Playtime: &f" +  (float) clickedPlayer.getStatistic(Statistic.PLAY_ONE_MINUTE)/20/60/60 + " Hours"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6- UUID: &f" + clickedPlayer.getUniqueId().toString()));
					if (e.getPlayer().hasPermission(main.getConfig().getString("messages.permissionExamineIp"))) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6- IP &f" + clickedPlayer.getAddress().getHostString()));
					}
				}
			}
		
		}		
	}



	//Cooldown so staff can't spam examine tool
	private boolean Cooldown(Player player) {
		if (cooldowns.containsKey(player.getName())) {
			if (cooldowns.get(player.getName()) > System.currentTimeMillis()){
				long timeleft = (cooldowns.get(player.getName()) - System.currentTimeMillis())/1000;
				player.sendMessage(ChatColor.RED + "Please wait " + timeleft + " seconds");
				return true;
			}
			if (cooldowns.get(player.getName()) < System.currentTimeMillis()) {
				cooldowns.remove(player.getName());
				return false;
			}	
		}
		return false;
	}
	
	//Vanish toggler
	public void Vanish(Player player) {
		playerdata = player.getPersistentDataContainer();
		
			if (!(playerdata.has(new NamespacedKey(main, "Hidden"), PersistentDataType.INTEGER))){
				playerdata.set(new NamespacedKey(main, "Hidden"), PersistentDataType.INTEGER ,  1);	
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &fVanish &aEnabled"));
				for (Player other : Bukkit.getServer().getOnlinePlayers()) {
					other.hidePlayer(main,player);
				}
			}
			else {
				playerdata.remove(new NamespacedKey(main, "Hidden"));
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &fVanish &4Disabled"));
				for (Player other : Bukkit.getServer().getOnlinePlayers()) {
					other.showPlayer(main,player);
				}
			}
	}
	
	//Vanish
	public void Vanish(Player player, boolean state) {
		playerdata = player.getPersistentDataContainer();
		if (state == true) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &fVanish &aEnabled"));
			playerdata.set(new NamespacedKey(main, "Hidden"), PersistentDataType.INTEGER ,  1);	
			for (Player other : Bukkit.getServer().getOnlinePlayers()) {
				other.hidePlayer(main,player);
			}
		}
		else {
			playerdata.remove(new NamespacedKey(main, "Hidden"));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &fVanish &4Disabled"));
			for (Player other : Bukkit.getServer().getOnlinePlayers()) {
				other.showPlayer(main,player);
			}
		}
	}
	
	
	
	//Enabling or disabling staff mode
	public void setStaffmode(Player player, boolean state) {
		
		if (state == true) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &fStaffmode &aEnabled"));
			Vanish(player, true);
			
			ItemStack[] inventory = player.getInventory().getContents();
			ItemStack[] saveInventory = new ItemStack[inventory.length];
			
			for(int i = 0; i < inventory.length; i++)
			{
			    if(inventory[i] != null)
			    {
			        saveInventory[i] = inventory[i].clone();
			    }
			}
			savedinventory.put(player.getUniqueId(), saveInventory);
			player.getInventory().clear();
			
			player.getInventory().addItem(staffItems.Examine());
			player.getInventory().addItem(staffItems.Kill());
			player.getInventory().addItem(staffItems.Clearinventory());
			player.getInventory().addItem(staffItems.Coreprotect());
			player.getInventory().addItem(staffItems.Vanish());
			player.getInventory().addItem(staffItems.Spectator());
			player.getInventory().addItem(staffItems.Creative());

		
		}
		
		
		if (state == false) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cPicnicStaff&8] &fStaffmode &4Disabled"));
			Vanish(player, false);
			player.getInventory().clear();
				
			ItemStack[] inventory = savedinventory.get(player.getUniqueId());	
			
				if (inventory != null) {
					player.getInventory().setContents(inventory);
				}
				else {
					player.sendMessage("Inventory couldn't be found");
				}
		}
	}

	
	public static ItemStack[] returnInventory(Player player) {
		return savedinventory.get(player.getUniqueId());
	}
	
}


































