package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.events.CompanionClientTickEvent;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.deadlydiamond98.familiar_friends.entities.CompanionEntityRendering;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class FamiliarFriendsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CompanionClientTickEvent.registerTickEvent();
		CompanionClientPackets.registerC2SPackets();
		CompanionEntityRendering.registerCompanionRenderers();
	}
}