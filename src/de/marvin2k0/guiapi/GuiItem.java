package de.marvin2k0.guiapi;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiItem extends ItemStack
{
    private GuiItemAction itemAction;

    public GuiItem(GuiItemAction itemAction, Material material, int amount, short damage)
    {
        super(material, amount, damage);

        this.itemAction = itemAction;
    }

    enum GuiItemAction {
        NOTHING, CLOSE, OPEN_NEW_WINDOW;
    }
}
