package net.deadlydiamond98.familiar_friends.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.common.entities.companions.vanilla.GoatCompanion;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @ModifyReturnValue(method = "canWalkOnPowderSnow", at = @At("RETURN"))
    private static boolean familiar_friends$canWalkOnPowderSnow(boolean original, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                return original || companion.canWalkOnPowderSnow();
            }
        }
        return original;
    }
}
