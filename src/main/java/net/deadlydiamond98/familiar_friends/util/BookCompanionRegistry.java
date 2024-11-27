package net.deadlydiamond98.familiar_friends.util;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.*;

import java.util.ArrayList;
import java.util.List;

public class BookCompanionRegistry {

    public static final List<Class<? extends PlayerCompanion>> COMPANIONS = new ArrayList<>();

    public static void addCompanions() {
        addCompanion(CreeperCompanion.class);
        addCompanion(ChickenCompanion.class);
        addCompanion(IronGolemCompanion.class);
        addCompanion(NaviCompanion.class);
        addCompanion(OcelotCompanion.class);
        addCompanion(SquidCompanion.class);
        addCompanion(OneUpMushroomCompanion.class);
        addCompanion(CompanionCubeCompanion.class);
        addCompanion(NotchCompanion.class);
    }

    /**
     *
     * @param companionClass This is the Companion to add to the book
     *
     */

    public static void addCompanion(Class<? extends PlayerCompanion> companionClass) {
        COMPANIONS.add(companionClass);
    }
}
