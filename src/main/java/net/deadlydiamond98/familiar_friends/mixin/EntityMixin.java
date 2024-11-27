package net.deadlydiamond98.familiar_friends.mixin;

import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Unique
    public Entity getPlayer() {
        return ((Entity)(Object)this);
    }

    @Inject(method = "setOnFireFromLava", at = @At(value = "HEAD"), cancellable = true)
    private void negateLavaDamage(CallbackInfo ci) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (((CompanionPlayerData) player).getCompanion() != null) {
                PlayerCompanion companion = ((CompanionPlayerData) player).getCompanion();
                if (companion instanceof CompanionCubeCompanion && player.canWalkOnFluid(Fluids.LAVA.getDefaultState())) {
                    ci.cancel();
                }
            }
        }
    }

}
