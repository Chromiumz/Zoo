package com.chromium.bigtest;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.chromium.command.Zoo;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		Logger.log("&aBigTest has enabled");
		
		getServer().getPluginManager().registerEvents(this, this);
		
		this.getCommand("zoo").setExecutor(new Zoo());

	}
	
	public void onDisable() {
		Logger.log("&cBigTest has disabled");
	}
	
}
