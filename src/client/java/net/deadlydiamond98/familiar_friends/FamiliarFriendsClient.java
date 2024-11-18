package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.renderer.TestPlayerCompanionEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class FamiliarFriendsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerModelLayers();
	}

	private void registerEntityRenderers() {
		EntityRendererRegistry.register(CompanionEntities.Player_Companion, TestPlayerCompanionEntityRenderer::new);
	}

	public void registerModelLayers() {

	}
}