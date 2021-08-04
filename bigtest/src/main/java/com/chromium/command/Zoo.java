package com.chromium.command;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.chromium.bigtest.Logger;

public class Zoo implements CommandExecutor {
	
	private static ArrayList<EntityType> peacefulEntity;
    private static ArrayList<EntityType> hostileEntity;
	private static Random random = new Random();
	
	static {
		peacefulEntity = new ArrayList<EntityType>();
		hostileEntity = new ArrayList<EntityType>();
		World world = Bukkit.getWorld("world");
		
		for(EntityType t : EntityType.values()) {
			if(t.isSpawnable()) {
				Entity e;
				try {
					e = world.spawnEntity(world.getSpawnLocation(), t);
				} catch (Exception x) {
					continue;
				}
				if(e instanceof Creature) {
					if(e instanceof Monster) {
						hostileEntity.add(t);
					} else {
						if(t.equals(EntityType.SHULKER) || t.equals(EntityType.HOGLIN)) {
							hostileEntity.add(t);
							continue;
						}
						peacefulEntity.add(t);
					}
				}
				e.remove();
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("com.chromium.zoo")) {
			return false;
		}
		
		Player player;
		Integer amount = 1;
		Boolean hostile = false;
		
		if(sender instanceof Player) {
			player = (Player) sender;
		} else {
			return false;
		}
		
		if(args.length >= 1) {
			try { 
				amount = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				Logger.log("&cInvalid amount provided!");
				return false;
			}
			if(args.length >= 2) {
				hostile = Boolean.parseBoolean(args[1]);
			}
		}
		
		spawnZooAtPlayer(player, amount, hostile);
		return true;
	}
	
	private void spawnZooAtPlayer(Player p, Integer i, Boolean hostile) {
		Block target = p.getTargetBlockExact(16);
		Location location = target.getLocation();
		World world = location.getWorld();
		
		location.add(0, 1, 0);
		
		for(int x = 0; x < i; x++) {
			EntityType type;
			if(hostile && random.nextBoolean()) {
				type = hostileEntity.get(random.nextInt(hostileEntity.size()));
			} else {
				type = peacefulEntity.get(random.nextInt(peacefulEntity.size()));
			}
			world.spawnEntity(location, type);
		}
	}
	
}
