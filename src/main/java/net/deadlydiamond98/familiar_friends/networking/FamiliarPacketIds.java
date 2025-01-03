package net.deadlydiamond98.familiar_friends.networking;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.util.Identifier;

public class FamiliarPacketIds {
    public static final Identifier UnlockCompanion = new Identifier(FamiliarFriends.MOD_ID, "unlock_companion_packet");
    public static final Identifier EquipCompanion = new Identifier(FamiliarFriends.MOD_ID, "equip_companion_packet");
    public static final Identifier UnequipCompanion = new Identifier(FamiliarFriends.MOD_ID, "unequip_companion_packet");
    public static final Identifier ActionCompanion = new Identifier(FamiliarFriends.MOD_ID, "action_companion_packet");
    public static final Identifier KeybindingCompanion = new Identifier(FamiliarFriends.MOD_ID, "keybinding_companion_packet");
    public static final Identifier CompanionRequest = new Identifier(FamiliarFriends.MOD_ID, "companion_request_packet");

    public static final Identifier CompanionPlayerSync = new Identifier(FamiliarFriends.MOD_ID, "companion_player_sync_packet");
    public static final Identifier CompanionSync = new Identifier(FamiliarFriends.MOD_ID, "companion_sync_packet");
    public static final Identifier OpenBook = new Identifier(FamiliarFriends.MOD_ID, "open_companion_book_packet");
}
