package net.deadlydiamond98.familiar_friends.util;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;

import java.util.List;

public interface CompanionPlayerData {
    void setCompanionStatus(boolean value);
    boolean hasCompanion();

    void unlockCompanion(String companion);
    void revokeCompanion(String companion);
    boolean isCompanionUnlocked(PlayerCompanion companion);
    void removeAllCompanions();
    void syncUnlockedList(List<String> unlockedCompanions);
    void syncCurrentCompanion(String companion);
    String currentCompanion();
    void grantCompanion(PlayerCompanion companion);
}
