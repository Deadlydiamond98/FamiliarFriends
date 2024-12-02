package net.deadlydiamond98.familiar_friends.util;

public class TimeUnitHelper {
    public static String getCooldownUnitText(float cooldown) {
        int seconds = Math.max(1, Math.round(cooldown)) / 20;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        if (hours >= 1) {
            return hours > 1 ? "hours" : "hour";
        } else if (minutes >= 1) {
            return minutes > 1 ? "minutes" : "minute";
        } else {
            return seconds > 1 ? "seconds" : "second";
        }
    }

    public static int calculateCooldownUnit(float cooldown) {
        int seconds = Math.max(1, Math.round(cooldown)) / 20;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        if (hours >= 1) {
            return hours;
        }
        else if (minutes >= 1) {
            return minutes;
        }
        else {
            return seconds;
        }
    }
}
