package de.marvin2k0.guiapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class CancelListener implements Listener
{
    @EventHandler
    public void onGuiClick(InventoryClickEvent event)
    {
        Inventory inventory = event.getClickedInventory();

        if (inventory != null)
        {
            if (GuiApi.isGuiInventory(inventory.getName()))
            {
                event.setCancelled(true);

                GuiInventory gui = GuiApi.getGuiFromName(inventory.getName());


                if (gui.isGuiItem(event.getCurrentItem().getItemMeta().getDisplayName()))
                {

                    String nextPanel = "";

                    for (String str : gui.getConfig().getStringList("guiItems"))
                    {
                        if (str.contains(event.getCurrentItem().getItemMeta().getDisplayName()))
                        {
                            nextPanel = str.split("-")[2];
                        }
                    }

                    event.getWhoClicked().openInventory(GuiApi.getGuiFromName(nextPanel).getClearInventory());
                }
            }
        }
    }
}
