package me.hybridplague.doatools;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class DoAEvents implements Listener {

	//private DoATools main;
	public DoAEvents(DoATools main) {
		//this.main = main;
	}
	
	private Set<ProtectedRegion> checkForRegions(World world, double x, double y, double z) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager manager = container.get(BukkitAdapter.adapt(world));
		
		BlockVector3 bv = BlockVector3.at(x, y, z);
		
		ApplicableRegionSet ars = manager.getApplicableRegions(bv);
		
		if (ars.getRegions().isEmpty()) {
			return null;
		}
		return ars.getRegions();
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
		
		Material mat = p.getInventory().getItemInMainHand().getType();
		ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
		List<String> lore = meta.getLore();
		
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			
			if (mat == Material.NETHERITE_PICKAXE) {
				if (lore.get(1).contains(ChatColor.RED + "Owner: ")) {
					if (!lore.get(1).contains(ChatColor.RED + "Owner: " + p.getName())) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You are not allowed to use other players' DoA Tools!");
						return;
					}
					
					Material block = e.getClickedBlock().getType();
					if (block.equals(Material.GLASS)
							|| block.equals(Material.TINTED_GLASS)
							|| block.equals(Material.BLACK_STAINED_GLASS)
							|| block.equals(Material.WHITE_STAINED_GLASS)
							|| block.equals(Material.RED_STAINED_GLASS)
							|| block.equals(Material.BLUE_STAINED_GLASS)
							|| block.equals(Material.LIGHT_BLUE_STAINED_GLASS)
							|| block.equals(Material.GRAY_STAINED_GLASS)
							|| block.equals(Material.LIGHT_GRAY_STAINED_GLASS)
							|| block.equals(Material.PINK_STAINED_GLASS)
							|| block.equals(Material.PURPLE_STAINED_GLASS)
							|| block.equals(Material.MAGENTA_STAINED_GLASS)
							|| block.equals(Material.LIME_STAINED_GLASS)
							|| block.equals(Material.GREEN_STAINED_GLASS)
							|| block.equals(Material.YELLOW_STAINED_GLASS)
							|| block.equals(Material.BROWN_STAINED_GLASS)
							|| block.equals(Material.CYAN_STAINED_GLASS)
							|| block.equals(Material.ORANGE_STAINED_GLASS)
							
							|| block.equals(Material.GLASS_PANE)
							|| block.equals(Material.TINTED_GLASS)
							|| block.equals(Material.BLACK_STAINED_GLASS_PANE)
							|| block.equals(Material.WHITE_STAINED_GLASS_PANE)
							|| block.equals(Material.RED_STAINED_GLASS_PANE)
							|| block.equals(Material.BLUE_STAINED_GLASS_PANE)
							|| block.equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
							|| block.equals(Material.GRAY_STAINED_GLASS_PANE)
							|| block.equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
							|| block.equals(Material.PINK_STAINED_GLASS_PANE)
							|| block.equals(Material.PURPLE_STAINED_GLASS_PANE)
							|| block.equals(Material.MAGENTA_STAINED_GLASS_PANE)
							|| block.equals(Material.LIME_STAINED_GLASS_PANE)
							|| block.equals(Material.GREEN_STAINED_GLASS_PANE)
							|| block.equals(Material.YELLOW_STAINED_GLASS_PANE)
							|| block.equals(Material.BROWN_STAINED_GLASS_PANE)
							|| block.equals(Material.CYAN_STAINED_GLASS_PANE)
							|| block.equals(Material.ORANGE_STAINED_GLASS_PANE)) {
						e.setCancelled(true);
						
						Location loc = e.getClickedBlock().getLocation();
						loc.getWorld().dropItemNaturally(loc, new ItemStack(block));
						loc.getWorld().playSound(p, Sound.BLOCK_GLASS_BREAK, 5, 1);
						e.getClickedBlock().setType(Material.AIR);
						return;
					}
					return;
				}
			}
			
		}
		
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (mat == Material.NETHERITE_SHOVEL) {
				if (lore.get(1).contains(ChatColor.RED + "Owner: ")) {
					e.setCancelled(true);
					if (!lore.get(1).contains(ChatColor.RED + "Owner: " + p.getName())) {
						p.sendMessage(ChatColor.RED + "You are not allowed to use other players' DoA Tools!");
						return;
					}
					Block b = e.getClickedBlock();
					if (b.getType().equals(Material.COARSE_DIRT)
							|| b.getType().equals(Material.PODZOL)
							|| b.getType().equals(Material.DIRT_PATH)
							|| b.getType().equals(Material.FARMLAND)
							|| b.getType().equals(Material.ROOTED_DIRT)) {
						
						Location loc = b.getLocation();
						
						if (checkForRegions(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()) != null) {
							p.sendMessage(ChatColor.RED + "This feature can only be used in the global region!");
							return;
							
						}
						if (b.getType().equals(Material.FARMLAND)) {
							Material crop = loc.getWorld().getBlockAt(new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ())).getType();
							if (crop == Material.WHEAT
									|| crop == Material.POTATOES
									|| crop == Material.CARROTS
									|| crop == Material.BEETROOTS
									|| crop == Material.PUMPKIN_STEM
									|| crop == Material.MELON_STEM
									|| crop == Material.ATTACHED_MELON_STEM
									|| crop == Material.ATTACHED_PUMPKIN_STEM) {
								p.sendMessage(ChatColor.RED + "This block has a crop on it!");
								return;
							}
						}
						b.setType(Material.DIRT);
						return;
					}
					
				}
			}
		}
		
	}
	
	@EventHandler
	public void onAnvilUse(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		try {
			if (inv instanceof AnvilInventory || inv instanceof GrindstoneInventory) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Pickaxe"))
						|| e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Axe"))
						|| e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Shovel"))
						|| e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&b&lDoA Shears"))) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lError: &fYou are not allowed to modify this item."));
					return;
				}
			}
		} catch (Exception ex) {
			
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		Material mat = p.getInventory().getItemInMainHand().getType();
		ItemMeta meta = p.getInventory().getItemInMainHand().getItemMeta();
		List<String> lore = meta.getLore();
		
		if (mat == Material.NETHERITE_PICKAXE
				|| mat == Material.NETHERITE_AXE
				|| mat == Material.NETHERITE_SHOVEL
				|| mat == Material.SHEARS) {
			if (lore.get(1).contains(ChatColor.RED + "Owner: ")) {
				if (!lore.get(1).contains(ChatColor.RED + "Owner: " + p.getName())) {
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "You are not allowed to use other players' DoA Tools!");
					return;
				}
			}
		}
		
	}
	
}
