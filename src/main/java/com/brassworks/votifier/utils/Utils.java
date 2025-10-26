package com.brassworks.votifier.utils;

import com.brassworks.votifier.MinecraftServerSupplier;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import net.minecraft.commands.CommandSourceStack;

public class Utils {

    public static void executeCommandAsServer(String command) {
        // Create a command source that represents the server
        CommandSourceStack source = MinecraftServerSupplier.getServer().createCommandSourceStack();

        // Parse the command
        ParseResults<CommandSourceStack> parseResults = MinecraftServerSupplier.getServer().getCommands().getDispatcher().parse(new StringReader(command), source);

        // Execute the command
        MinecraftServerSupplier.getServer().getCommands().performCommand(parseResults, command);
    }
}