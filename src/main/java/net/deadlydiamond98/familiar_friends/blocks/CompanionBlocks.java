package net.deadlydiamond98.familiar_friends.blocks;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class CompanionBlocks {

    public static final Block Cirno_Ice = registerBlock("cirno_ice",
            new CirnoFrostedIce(AbstractBlock.Settings.copy(Blocks.FROSTED_ICE).breakInstantly().dropsNothing()
                    .sounds(BlockSoundGroup.GLASS).nonOpaque()));


    public static Block registerBlock(String blockName, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(FamiliarFriends.MOD_ID, blockName), block);
    }

    public static void registerBlocks() {
        FamiliarFriends.LOGGER.debug("Registering Blocks for" + FamiliarFriends.MOD_ID);
    }
}
