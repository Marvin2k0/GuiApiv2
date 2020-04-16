package de.marvin2k0.guiapi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TestCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("§cNur fuer Spieler!");

            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1)
        {
            player.sendMessage("§cBitte benutze /" + label + " <name>");

            return true;
        }

        String name = ChatColor.translateAlternateColorCodes('&', args[0]);

        Inventory content = Bukkit.createInventory(null, 27, name);

        for (int i = 0; i < content.getSize(); i++)
        {
            ItemStack placeholder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
            ItemMeta placeHolderMeta = placeholder.getItemMeta();
            placeHolderMeta.setDisplayName(" ");
            placeholder.setItemMeta(placeHolderMeta);

            content.setItem(i, placeholder);
        }

        GuiItem guiItem = new GuiItem(GuiItem.GuiItemAction.OPEN_NEW_WINDOW, Material.GREEN_GLAZED_TERRACOTTA, 1, (short) 0);
        ItemMeta guiItemMeta = guiItem.getItemMeta();
        guiItemMeta.setDisplayName("§aNaechste Seite-0-§6gelb");
        guiItem.setItemMeta(guiItemMeta);

        content.setItem(26, guiItem);

        GuiInventory gui = GuiInventory.createInventory(content);

        player.openInventory(gui.getClearInventory());

        return true;
    }
}
