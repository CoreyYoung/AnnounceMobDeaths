package announcemobdeaths;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
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
		if (event.getEntity() instanceof Player) {
			return;
		}
		
		if (event.getEntity() instanceof Monster && event.getEntity().getCustomName() == null) {
			return;
		}
		
		LivingEntity entity = event.getEntity();
		if (entity.getKiller() instanceof Player) {
			Player player = entity.getKiller();
			String message = "";
			String entityName = entity.getType().toString().substring(0, 1).toUpperCase() + entity.getType().toString().substring(1).toLowerCase();

			if (entity.getCustomName() == null) {
				if (entityName.toLowerCase().charAt(0) == 'a'
						|| entityName.toLowerCase().charAt(0) == 'e'
						|| entityName.toLowerCase().charAt(0) == 'i'
						|| entityName.toLowerCase().charAt(0) == 'o'
						|| entityName.toLowerCase().charAt(0) == 'u'
						|| entityName.toLowerCase().charAt(0) == 'y') {
					message = player.getDisplayName() + " killed an " + entityName + ".";
				} else {
					message = player.getDisplayName() + " killed a " + entityName + ".";
				}
			} else {
				message = player.getDisplayName() + " killed " + entity.getCustomName() + " the " + entityName + ".";
			}

			getServer().broadcastMessage(message);
		}
	}
}
