package me.hybridplague.doatools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class DoATools extends JavaPlugin {

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new DoAEvents(this), this);
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (label.equalsIgnoreCase("doashears")) {
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("doatools.use")) {
				return false;
			}
			
			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "/doashears <player>");
				return true;
			}
			
			OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
			if (!t.hasPlayedBefore() && !t.isOnline()) {
				p.sendMessage(ChatColor.RED + t.getName() + " not found");
				return true;
			}
			getShears(p, t.getName());
			p.sendMessage(ChatColor.GREEN + "You have been given Shears for " + t.getName());
			
			
			
		}
		
		if (label.equalsIgnoreCase("doatools")) {
			if (!(sender instanceof Player)) {
				return false;
			}
			
			Player p = (Player) sender;
			
			if (!p.hasPermission("doatools.use")) {
				return false;
			}
			
			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "/doatools <add | list | remove> <player>");
				return true;
			} else if (args.length == 1) {
				switch (args[0].toUpperCase()) {
				case "LIST":
					list(p);
					break;
				default:
					p.sendMessage(ChatColor.RED + "/doatools <add | list | remove> <player>");
					break;
				}
				return true;
			} else if (args.length >= 2) {
				OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
				if (!t.hasPlayedBefore() && !t.isOnline()) {
					p.sendMessage(ChatColor.RED + t.getName() + " not found");
					return true;
				}
				List<String> toolList = this.getConfig().getStringList("OwnerList");
				switch (args[0].toUpperCase()) {
				case "LIST":
					list(p);
					break;
				case "ADD":
					
					if (toolList.contains(t.getUniqueId().toString())) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + t.getName() + " already has tools! If this is a mistake, remove them from the list with /doatools remove <player>"));
						return true;
					}
					
					toolList.add(t.getUniqueId().toString());
					this.getConfig().set("OwnerList", toolList);
					this.saveConfig();
					getTools(p, t.getName());
					p.sendMessage(ChatColor.GREEN + "You have been given tools for " + t.getName());
					break;
				case "REMOVE":
					if (!toolList.contains(t.getUniqueId().toString())) {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + t.getName() + " is not on the list."));
						return true;
					}
					toolList.remove(t.getUniqueId().toString());
					this.getConfig().set("OwnerList", toolList);
					this.saveConfig();
					p.sendMessage(ChatColor.RED + t.getName() + " has been removed from the list");
					break;
				default:
					p.sendMessage(ChatColor.RED + "/doatools <add | list | remove> <player>");
					break;
				}
			}
			
		}
		return false;
	}
	
	public void getShears(Player p, String op) {
		
		ItemStack item = new ItemStack(Material.SHEARS);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		lore.add("");
		lore.add(ChatColor.RED + "Owner: " + op);
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Shears"));
		meta.setLore(lore);
		meta.setUnbreakable(true);
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		if (p.getInventory().firstEmpty() == -1) {
			p.getWorld().dropItemNaturally(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
	}
	
	public void getTools(Player p, String op) {
		
		ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		lore.add("");
		lore.add(ChatColor.RED + "Owner: " + op);
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Pickaxe"));
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
		meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		if (p.getInventory().firstEmpty() == -1) {
			p.getWorld().dropItemNaturally(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
		
		item.setType(Material.NETHERITE_AXE);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Axe"));
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
		meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		if (p.getInventory().firstEmpty() == -1) {
			p.getWorld().dropItemNaturally(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}

		item.setType(Material.NETHERITE_SHOVEL);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Shovel"));
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 10, true);
		meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		if (p.getInventory().firstEmpty() == -1) {
			p.getWorld().dropItemNaturally(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
		
		item.setType(Material.SHEARS);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Shears"));
		meta.setLore(lore);
		meta.setUnbreakable(true);
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		if (p.getInventory().firstEmpty() == -1) {
			p.getWorld().dropItemNaturally(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
		
	}
	
	public void list(Player p) {
		List<String> l = new ArrayList<String>();
		for (String list : this.getConfig().getStringList("OwnerList")) {
			UUID id = UUID.fromString(list);
			l.add(ChatColor.AQUA + Bukkit.getOfflinePlayer(id).getName());
		}
		String result = String.join("&7, &b", l);
		
		if (l.size() == 0) {
			p.sendMessage(ChatColor.RED + "No owners yet!");
			return;
		}
		
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', result));
	}
	
}
