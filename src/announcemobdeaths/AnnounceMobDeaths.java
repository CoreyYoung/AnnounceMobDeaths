package announcemobdeaths;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AnnounceMobDeaths extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		Player killer = entity.getKiller();
		
		// Capitalize first letter of mob type and replace underscores with spaces.
		String entityType = entity.getType().toString().substring(0, 1).toUpperCase() + entity.getType().toString().substring(1).toLowerCase();
		entityType = entityType.replace('_', ' ');
		
		if (entity instanceof Player || killer == null) {
			return;
		}
		
		if (entity instanceof Tameable) {
			Tameable tameable = (Tameable) entity;
			
			if (tameable.getOwner() != null) {
				String owner = tameable.getOwner().getName();
				
				if (entity.getCustomName() == null) {
					getServer().broadcastMessage(killer.getDisplayName() + " killed " + owner + "'s " + entityType + ".");
				} else {
					getServer().broadcastMessage(killer.getDisplayName() + " killed " + entity.getCustomName() + ", " + owner + "'s " + entityType + ".");
				}
				
				return;
			}
		}
		
		if (entity.getCustomName() != null) {
			String message = killer.getDisplayName() + " killed " + entity.getCustomName() + " the " + entityType + ".";

			getServer().broadcastMessage(message);
		}
	}
}
