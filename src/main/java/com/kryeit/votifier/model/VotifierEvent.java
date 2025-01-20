package com.kryeit.votifier.model;

import com.kryeit.votifier.Votifier;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.NeoForge;

public class VotifierEvent extends Event implements IModBusEvent {
	private final Vote vote;

	public VotifierEvent(Vote vote) {
		this.vote = vote;
	}

	public Vote getVote() {
		return vote;
	}

	public static void post(Vote vote) {
		NeoForge.EVENT_BUS.post(new VotifierEvent(vote));
	}
}