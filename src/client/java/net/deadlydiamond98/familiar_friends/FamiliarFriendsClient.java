package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.blocks.CompanionBlocks;
import net.deadlydiamond98.familiar_friends.events.CompanionClientTickEvent;
import net.deadlydiamond98.familiar_friends.networking.CompanionClientPackets;
import net.deadlydiamond98.familiar_friends.screens.CompanionBookScreen;
import net.deadlydiamond98.familiar_friends.entities.CompanionEntityRendering;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class FamiliarFriendsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CompanionClientTickEvent.registerTickEvent();
		CompanionClientPackets.registerC2SPackets();
		CompanionEntityRendering.registerCompanionRenderers();

		BlockRenderLayerMap.INSTANCE.putBlock(CompanionBlocks.Cirno_Ice, RenderLayer.getTranslucent());
	}
}