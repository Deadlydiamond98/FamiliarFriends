package net.deadlydiamond98.familiar_friends.init;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class CompanionTags {
    public static final TagKey<Block> NAVI_RADAR = block("sensed_by_navi");

    private static TagKey<Block> block(String name) {
        return getTag(RegistryKeys.BLOCK, name);
    }

    private static <T> TagKey<T> getTag(RegistryKey<? extends Registry<T>> registry, String name) {
        return TagKey.of(registry, new Identifier(FamiliarFriends.MOD_ID, name));
    }
}
