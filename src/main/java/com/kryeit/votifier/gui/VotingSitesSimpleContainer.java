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
                this.setItem(slot.slot(), item);
            }
        }
    }
}