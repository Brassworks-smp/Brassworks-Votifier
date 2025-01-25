package com.kryeit.votifier.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kryeit.votifier.gui.GuiUtils;
import com.kryeit.votifier.gui.VotingSiteSlot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GuiConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuiConfigReader.class);

    public static List<VotingSiteSlot> MENU_SLOTS = new ArrayList<>();

    public static void readFile(Path path) throws IOException {
        String config = readOrCopyFile(path.resolve("voting_sites.json"), "/voting_sites.json");
        JsonArray slotsArray = JsonParser.parseString(config).getAsJsonArray();

        readMenuSlots(slotsArray);
    }

    public static void readMenuSlots(JsonArray slotsArray) {
        for (JsonElement element : slotsArray) {
            JsonObject slotObject = element.getAsJsonObject();

            // Parse required fields with null checks
            int slot = slotObject.has("slot") ? slotObject.get("slot").getAsInt() : -1;
            if (slot == -1) {
                throw new RuntimeException("Missing required field: slot");
            }

            String link = slotObject.has("link") ? slotObject.get("link").getAsString() : "https://minecraft.net";

            ItemStack itemStack = GuiUtils.formatItemStack(slotObject);

            VotingSiteSlot menuSlot = new VotingSiteSlot(slot, itemStack, link);
            MENU_SLOTS.add(menuSlot);
        }
    }

    public static String readOrCopyFile(Path path, String exampleFile) throws IOException {
        File file = path.toFile();
        if (!file.exists()) {
            LOGGER.info("File does not exist, attempting to copy from resources: " + exampleFile);
            InputStream stream = GuiConfigReader.class.getResourceAsStream(exampleFile);
            if (stream == null) {
                LOGGER.error("Cannot load example file: " + exampleFile);
                throw new NullPointerException("Cannot load example file");
            }

            //noinspection ResultOfMethodCallIgnored
            file.getParentFile().mkdirs();
            Files.copy(stream, path);
            LOGGER.info("File copied to: " + path.toString());
        } else {
            LOGGER.info("File already exists: " + path.toString());
        }
        return Files.readString(path);
    }
}
