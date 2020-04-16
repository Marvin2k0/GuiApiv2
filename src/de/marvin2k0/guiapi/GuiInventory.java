package de.marvin2k0.guiapi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiInventory
{
    private File file;
    private FileConfiguration config;

    private List<String> guiItems;

    private Inventory inventory;
    private String name;

    private GuiInventory(Inventory inventory)
    {
        this.file = new File(GuiApi.PLUGIN.getDataFolder().getPath() + "/guis/" + inventory.getName() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);

        this.inventory = inventory;
        this.name = inventory.getName();
        this.guiItems = new ArrayList<String>();

        GuiApi.registerGui(this);

        saveConfig();
    }

    public static GuiInventory createInventory(Inventory inventory)
    {
        GuiInventory gui = new GuiInventory(inventory);

        return gui;
    }

    private void addGuiItem(String guiItem)
    {
        if (!guiItems.contains(guiItem))
            guiItems.add(guiItem);

        this.config.set("guiItems", guiItems);

        saveConfig();
    }

    public String getName()
    {
        return inventory.getName();
    }

    public Inventory getInventory()
    {
        for (ItemStack item : inventory.getContents())
        {
            ItemMeta meta = item.getItemMeta();

            if (meta.getDisplayName().contains("-0"))
            {
                this.addGuiItem(item.getItemMeta().getDisplayName());
            }
        }

        return this.inventory;
    }

    public Inventory getClearInventory()
    {
        for (ItemStack item : inventory.getContents())
        {
            ItemMeta meta = item.getItemMeta();

            if (meta.getDisplayName().contains("-0"))
            {
                this.addGuiItem(item.getItemMeta().getDisplayName());

                meta.setDisplayName(meta.getDisplayName().split("-")[0]);
                item.setItemMeta(meta);
            }
        }

        return this.inventory;
    }

    public boolean isGuiItem(String name)
    {
        if (name == null)
            return false;

        for (String str : this.config.getStringList("guiItems"))
        {
            if (str.split("-")[0].equals(name))
                return true;
        }

        return false;
    }

    public FileConfiguration getConfig()
    {
        return this.config;
    }

    public void saveConfig()
    {
        try
        {
            config.save(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
