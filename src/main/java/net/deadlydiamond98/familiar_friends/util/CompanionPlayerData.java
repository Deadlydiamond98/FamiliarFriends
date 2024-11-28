package net.deadlydiamond98.familiar_friends.util;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;

import java.util.List;

public interface CompanionPlayerData {

    // These are all used in the PlayerMixin, call them with player.(insert method)

    void unlockCompanion(String companion);
    void lockCompanion(String companion);
    boolean isCompanionUnlocked(PlayerCompanion companion);
    void lockAllCompanions();
    void syncUnlockedList(List<String> unlockedCompanions);
    void syncCurrentCompanion(String companion);
    String currentCompanion();
    void equipCompanion(PlayerCompanion companion);
    void unequipCompanion(PlayerCompanion companion);
    void doCompanionKeybind();
    PlayerCompanion getCompanion();

}
