package net.deadlydiamond98.familiar_friends.util;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;

public interface CompanionPlayerData {
    void setCompanionStatus(boolean value);
    boolean hasCompanion();

    void unlockCompanion(PlayerCompanion companion);
    boolean isCompanionUnlocked(PlayerCompanion companion);
}
