package net.deadlydiamond98.familiar_friends.util;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public interface CompanionPlayerData {
    void setCompanionStatus(boolean value);
    boolean hasCompanion();

    void unlockCompanion(String companion);
    boolean isCompanionUnlocked(PlayerCompanion companion);
    void removeAllCompanions();
    void syncUnlockedList(List<String> unlockedCompanions);
}
