package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.*;
import net.minecraft.client.MinecraftClient;

import java.util.HashMap;

public class CompanionBookAdd {

    public static final HashMap<Integer, PlayerCompanion> companions = new HashMap<>();

    public static void addCompanions() {
        MinecraftClient client = MinecraftClient.getInstance();

        companions.clear();

        addCompanion(new CreeperCompanion(client.player.getWorld(), client.player, true));
        addCompanion(new ChickenCompanion(client.player.getWorld(), client.player, true));
        addCompanion(new IronGolemCompanion(client.player.getWorld(), client.player, true));
        addCompanion(new NaviCompanion(client.player.getWorld(), client.player, true));
        addCompanion(new OcelotCompanion(client.player.getWorld(), client.player, true));
    }

    public static void addCompanion(PlayerCompanion companion) {
        companions.put(companions.size(), companion);
    }
}
