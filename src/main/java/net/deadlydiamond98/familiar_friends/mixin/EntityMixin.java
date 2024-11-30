package net.deadlydiamond98.familiar_friends.mixin;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.entities.companions.CompanionCubeCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow protected boolean firstUpdate;

    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @Shadow public abstract boolean isOnFire();

    @Unique
    public Entity getPlayer() {
        return ((Entity)(Object)this);
    }

    @Inject(method = "isInLava", at = @At(value = "HEAD"), cancellable = true)
    private void inLava(CallbackInfoReturnable<Boolean> cir) {
        if (this.getPlayer() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion instanceof CompanionCubeCompanion) {
                    cir.setReturnValue(!this.firstUpdate &&
                            this.fluidHeight.getDouble(FluidTags.LAVA) > 0.4);
                }
            }
        }
    }

}
