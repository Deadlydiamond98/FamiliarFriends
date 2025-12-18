package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.init.CompanionBlocks;
import net.deadlydiamond98.familiar_friends.events.client.CompanionClientTickEvent;
import net.deadlydiamond98.familiar_friends.init.client.CompanionEntityRendering;
import net.deadlydiamond98.familiar_friends.init.client.CompanionKeybindings;
import net.deadlydiamond98.familiar_friends.networking.CompanionPackets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FamiliarFriendsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CompanionPackets.registerS2CPackets();
		CompanionKeybindings.register();

		CompanionClientTickEvent.register();
		CompanionEntityRendering.register();

		BlockRenderLayerMap.INSTANCE.putBlock(CompanionBlocks.Cirno_Ice, RenderLayer.getTranslucent());
	}
}