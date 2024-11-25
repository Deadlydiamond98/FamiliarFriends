package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.ChickenCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CreeperCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.IronGolemCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.NaviCompanion;
import net.minecraft.client.MinecraftClient;

import java.util.HashMap;

public class CompanionBookAdd {

    public static final HashMap<Integer, PlayerCompanion> companions = new HashMap<>();

    public static void addCompanions() {
        MinecraftClient client = MinecraftClient.getInstance();

        addCompanion(new CreeperCompanion(client.player.getWorld(), client.player, true));
        addCompanion(new ChickenCompanion(client.player.getWorld(), client.player, true));
        addCompanion(new IronGolemCompanion(client.player.getWorld(), client.player, true));
        addCompanion(new NaviCompanion(client.player.getWorld(), client.player, true));
    }

    public static void addCompanion(PlayerCompanion companion) {
        companions.put(companions.size(), companion);
    }
}
