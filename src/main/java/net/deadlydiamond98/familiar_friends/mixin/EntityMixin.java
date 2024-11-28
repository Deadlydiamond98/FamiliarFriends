package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.vanilla.SpiderCompanion;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Unique
    public Entity getPlayer() {
        return ((Entity)(Object)this);
    }

    @Inject(method = "canClimb", at = @At(value = "HEAD"), cancellable = true)
    private void climbWithSpider(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (((CompanionPlayerData) player).getCompanion() != null) {
                PlayerCompanion companion = ((CompanionPlayerData) player).getCompanion();
                if (companion instanceof SpiderCompanion) {
                    cir.setReturnValue(true);
                    cir.cancel();
                }
            }
        }
    }

}
