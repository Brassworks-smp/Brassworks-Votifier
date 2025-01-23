package com.kryeit.votifier.gui;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

import java.util.List;

public class VotingSitesSimpleContainer extends SimpleContainer {

    public VotingSitesSimpleContainer(int size, List<VotingSiteSlot> slots) {
        super(size);

        for (VotingSiteSlot slot : slots) {
            if (slot.slot() >= 0 && slot.slot() < size) {
                ItemStack item = slot.item();

                if (slot.name() == null) {
                    item.set(DataComponents.ITEM_NAME, null);
                } else {
                    item.set(DataComponents.ITEM_NAME, GuiUtils.getFormatted(slot.name()));
                }

                List<Component> lore = slot.lore();
                item.set(DataComponents.LORE, new ItemLore(lore));

                item.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, slot.enchanted());

                this.setItem(slot.slot(), item);
            }
        }
    }
}