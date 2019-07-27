package co.lotc.pitautofill;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FillSignListener implements Listener {

	private final static String SIGN_IDENTIFIER = "[Refill]";

	@EventHandler
	public void OnSignRightclick(PlayerInteractEvent event) {

		Block block = event.getClickedBlock();

		// RClick && Either Sign Type
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
			(block.getType().equals(Material.WALL_SIGN) ||
			 block.getType().equals(Material.SIGN))) {

			Sign sign = (Sign) block.getState();
			if (sign.getLine(1).equalsIgnoreCase(SIGN_IDENTIFIER)) {
				event.getPlayer().sendMessage(PitAutofill.PREFIX + PitList.fillPit(sign.getLine(2).replace(' ', '_')));
			}
		}
	}

	// Updates a sign as needed if it matches upon update.
	@EventHandler
	public void onSignPlaced(SignChangeEvent event) {
		if (SIGN_IDENTIFIER.equalsIgnoreCase(event.getLine(1))) {
			if (event.getPlayer().hasPermission("pit.edit")) {
				event.setLine(1, SIGN_IDENTIFIER);
				event.setLine(2, WordUtils.capitalizeFully(event.getLine(2).replace('_', ' ')));
			} else {
				event.setCancelled(true);
				event.getPlayer().sendMessage(PitAutofill.PREFIX + "You cannot create a refill sign.");
			}
		}
	}

}