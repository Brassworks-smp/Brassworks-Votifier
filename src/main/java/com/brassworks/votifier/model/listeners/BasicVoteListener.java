/*
 * Copyright (C) 2011 Vex Software LLC
 * This file is part of Votifier.
 * 
 * Votifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Votifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Votifier.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.brassworks.votifier.model.listeners;

import com.brassworks.votifier.Votifier;
import com.brassworks.votifier.model.VotifierEvent;
import com.brassworks.votifier.utils.Utils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import static com.brassworks.votifier.Votifier.LOGGER;
import static com.brassworks.votifier.config.ConfigReader.COMMAND;

/**
 * A basic vote listener for demonstration purposes.
 * 
 * @author Blake Beaupain
 */
@EventBusSubscriber(modid = Votifier.MODID)
public class BasicVoteListener {

	@SubscribeEvent
	public static void onVoteReceived(VotifierEvent event) {
		LOGGER.info("Received: " + event.getVote());

		String command = COMMAND.replace("%player%", event.getVote().getUsername());

		if (!command.isEmpty())
			Utils.executeCommandAsServer(command);
	}
}
