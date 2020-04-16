package de.marvin2k0.guiapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiApi extends JavaPlugin
{
    public static Plugin PLUGIN;

    @Override
    public void onEnable()
    {
        this.getCommand("create").setExecutor(new TestCommands());

        setUp(this);
    }

    /**
     * Method needs to be called in 'onEnable'-Method
     *
     * @param plugin Class that extends from 'JavaPlugin'
     */
    public static void setUp(Plugin plugin)
    {
        PLUGIN = plugin;

        plugin.getServer().getPluginManager().registerEvents(new CancelListener(), plugin);
    }

    /**
     * Method that is being called whenever a new gui is registered.
     *
     * @param gui GuiInventory you want to register.
     * @return True if everything worked, false if something went wrong (e.g. gui-name already exists)
     */
    public static void registerGui(GuiInventory gui)
    {
        FileConfiguration config = gui.getConfig();

        config.set("name", gui.getName());

        ItemStack[] itemList = gui.getInventory().getContents();
        List<String> items = new ArrayList<String>();

        int i = 0;

        for (ItemStack item : itemList)
        {
            if (item != null)
            {
                String itemName = item.getItemMeta().getDisplayName();
                String label = i + "-" + item.getType().toString() + (itemName != null ? "-" + itemName : "");

                if (itemName != null)
                {
                    if (!itemName.contains("-0"))
                    {
                        label += "-1";
                    }
                }

                items.add(label);
            }

            i++;
        }

        config.set("items", items);

        gui.saveConfig();
    }

    public static boolean isGuiInventory(String name)
    {
        return getGuiFromName(name) != null;
    }

    public static GuiInventory getGuiFromName(String name)
    {
        File file = new File(PLUGIN.getDataFolder().getPath() + "/guis/" + name + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists())
            return null;

        List<String> items = config.getStringList("items");
        int size = items.size();

        Inventory content = Bukkit.createInventory(null, size, name);

        for (int i = 0; i < content.getSize(); i++)
        {
            String itemType = items.get(i).split("-")[1];

            if (config.getStringList("guiItems").contains(items.get(i).substring(items.get(i).split("-")[0].length() + 3 + items.get(i).split("-")[1].length() - 1)))
            {
                ItemStack item = new ItemStack(Material.getMaterial(itemType));
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(items.get(i).substring(items.get(i).split("-")[0].length() + 3 + items.get(i).split("-")[1].length() - 1));
                item.setItemMeta(itemMeta);

                content.setItem(i, item);
            }
            else
            {
                String itemName = items.get(i).split("-")[2];

                ItemStack item = new ItemStack(Material.getMaterial(itemType));
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(itemName);
                item.setItemMeta(itemMeta);

                content.setItem(i, item);
            }
        }

        GuiInventory gui = GuiInventory.createInventory(content);

        return gui;
    }
}
