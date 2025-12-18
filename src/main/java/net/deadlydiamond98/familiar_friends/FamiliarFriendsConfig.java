package net.deadlydiamond98.familiar_friends;

import net.deadlydiamond98.koalalib.config.CFGProperties;

public class FamiliarFriendsConfig {
    public static class Main {
        @CFGProperties(min = 0)
        public static int cooldownTime = 60;
        public static boolean unequipCurrentOnDeath = false;
        public static boolean lockCompanionsOnDeath = false;
    }

    public static class OneUp {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 10;
    }

    public static class Allay {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 10;
        @CFGProperties(min = 0, max = 10)
        public static double itemPickupRange = 4.0;
    }

    public static class CaveSpider {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 25;
        @CFGProperties(min = 0, max = 1)
        public static double poisonChance = 0.5;
        @CFGProperties(min = 0)
        public static int poisonDuration = 3;
    }

    public static class Chicken {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 3;
        @CFGProperties(min = 0)
        public static int slowFallDuration = 43;
    }

    public static class Cirno {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 19;
        public static boolean enableFrostWalker = true;
        @CFGProperties(min = 0)
        public static int projectileCooldown = 5;
    }

    public static class CompanionCube {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 25;
        public static boolean grantFireImmunity = false;
    }

    public static class Creeper {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 20;
        public static boolean lockOnDeath = true;
    }

    public static class Goat {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 3;
    }

    public static class Herobrine {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 25;
        public static boolean shouldDamagePlayer = true;
        @CFGProperties(min = 0, max = 100)
        public static double teleportDistance = 100.0;
        @CFGProperties(min = 0)
        public static int teleportCooldown = 15;
    }

    public static class IronGolem {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 12;
        @CFGProperties(min = 0)
        public static int attackCooldown = 80;
        @CFGProperties(min = 0)
        public static double attackDamage = 4.0;
    }

    public static class Jeb {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 20;
        @CFGProperties(min = 0, max = 100)
        public static double smiteDistance = 100.0;
        @CFGProperties(min = 0)
        public static int lightningCooldown = 15;
    }

    public static class Lemon {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 15;
        @CFGProperties(min = 0)
        public static double damage = 5.0;
        @CFGProperties(min = 0)
        public static int slownessDuration = 100;
        @CFGProperties(min = 0)
        public static int abilityCooldown = 15;
    }

    public static class MrSaturn {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 12;
        @CFGProperties(min = 0, max = 1)
        public static double smaaashAttackChance = 0.06;
        @CFGProperties(min = 0)
        public static double knockback = 3;
        @CFGProperties(min = 0)
        public static double extraDamage = 2;
    }

    public static class Navi {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 18;
        @CFGProperties(min = 0, max = 100)
        public static int senseRange = 20;
    }

    public static class Ocelot {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 8;
        @CFGProperties(min = 1)
        public static int speedLevel = 1;
    }

    public static class Rana {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 18;
        @CFGProperties(min = 1)
        public static int jumpBoostLevel = 2;
        public static boolean preventFallDamage = true;
    }

    public static class Skeleton {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 13;
        @CFGProperties(min = 0)
        public static int arrowFireCooldown = 120;
    }

    public static class SnowGolem {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 8;
        @CFGProperties(min = 0)
        public static int snowballFireCooldown = 20;
    }

    public static class Spider {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 20;
    }

    public static class Squid {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 14;
        @CFGProperties(min = 0)
        public static int waterBreathingDuration = 103;
    }

    public static class Tater {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 14;
        @CFGProperties(min = 0)
        public static int soundCooldown = 15;
        @CFGProperties(min = 0, max = 1)
        public static double hungerStealChance = 0.5;
    }

    public static class Vampire {
        @CFGProperties(hasDesc = false)
        public static boolean enabled = true;
        @CFGProperties(min = 0, hasDesc = false)
        public static int cost = 12;
        @CFGProperties(min = 0)
        public static int healAmount = 1;
    }
}
