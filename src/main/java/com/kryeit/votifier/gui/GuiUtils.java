package com.kryeit.votifier.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kryeit.votifier.MinecraftServerSupplier;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.ResolvableProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.kryeit.votifier.config.GuiConfigReader.MENU_SLOTS;

public class GuiUtils {

    public static VotingSiteSlot getVotingSiteSlot(int slot) {
        List<VotingSiteSlot> slots = MENU_SLOTS;

        for (VotingSiteSlot vosingSlot : slots) {
            if (vosingSlot.slot() == slot) {
                return vosingSlot;
            }
        }

        return null;
    }

    public static ItemStack formatItemStack(JsonObject object) {
        ItemStack item;
        if (!object.has("item")) {
            item = new ItemStack(Items.PLAYER_HEAD);

            if (object.has("head")) {
                item = GuiUtils.getCustomHead(object.get("head").getAsString());
            }
        } else {
            String itemString = object.get("item").getAsString();
            item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemString)).getDefaultInstance();
        }

        if (object.has("name")) {
            item.set(DataComponents.ITEM_NAME, getFormatted(object.get("name").getAsString()));
        }

        JsonArray loreArray = object.has("lore") ? (JsonArray) object.get("lore") : new JsonArray();
        List<Component> lore = new ArrayList<>();
        for (JsonElement loreElement : loreArray) {
            lore.add(GuiUtils.getFormatted(loreElement.getAsString()));
        }
        item.set(DataComponents.LORE, new ItemLore(lore));

        if (object.has("enchanted")) {
            item.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, object.get("enchanted").getAsBoolean());
        }

        if (object.has("count")) {
            item.setCount(object.get("count").getAsInt());
        }

        if (object.has("rarity")) {
            item.set(DataComponents.RARITY, Rarity.valueOf(object.get("rarity").getAsString().toUpperCase()));
        }

        if (!object.has("show-attributes") || !object.get("show-attributes").getAsBoolean()) {
            item.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        }

        return item;
    }

    public static MutableComponent getFormatted(String text) {
        String formattedText = text.replace('&', '§');
        return Component.literal(formattedText);
    }

    public static ItemStack getCustomHead(String texture) {
        ItemStack skull = new ItemStack(Items.PLAYER_HEAD);
        GameProfile profile = new GameProfile(UUID.randomUUID(), "CustomHead");
        profile.getProperties().put("textures", new Property("textures", texture));

        ResolvableProfile resolvableProfile = new ResolvableProfile(profile);

        skull.set(DataComponents.PROFILE, resolvableProfile);

        return skull;
    }

    public static void executeCommandAsServer(String command) {
        // Create a command source that represents the server
        CommandSourceStack source = MinecraftServerSupplier.getServer().createCommandSourceStack();

        // Parse the command
        ParseResults<CommandSourceStack> parseResults = MinecraftServerSupplier.getServer().getCommands().getDispatcher().parse(new StringReader(command), source);

        // Execute the command
        MinecraftServerSupplier.getServer().getCommands().performCommand(parseResults, command);
    }
}
