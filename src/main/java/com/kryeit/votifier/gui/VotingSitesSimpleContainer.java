package com.kryeit.votifier.gui;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VotingSitesSimpleContainer extends SimpleContainer {

    public VotingSitesSimpleContainer(int size, List<VotingSiteSlot> slots) {
        super(size);

        for (VotingSiteSlot slot : slots) {
            if (slot.slot() >= 0 && slot.slot() < size) {
                ItemStack item = slot.item();

                if (slot.name() == null) {
                    item.set(DataComponents.ITEM_NAME, null);
                } else {
                    item.set(DataComponents.ITEM_NAME, Component.literal(slot.name()));
                }

                String lore = slot.lore();
                if (lore != null && !lore.isEmpty()) {
                    List<Component> loreComponents = Arrays.stream(lore.split("\n"))
                            .map(Component::literal)
                            .collect(Collectors.toList());
                    item.set(DataComponents.LORE, new ItemLore(loreComponents));
                }

                item.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, slot.enchanted());

                this.setItem(slot.slot(), item);
            }
        }
    }
}