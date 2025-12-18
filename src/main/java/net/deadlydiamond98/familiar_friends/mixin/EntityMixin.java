package net.deadlydiamond98.familiar_friends.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow protected boolean firstUpdate;
    @Shadow protected Object2DoubleMap<TagKey<Fluid>> fluidHeight;

    @Unique
    public Entity familiar_friends$getEntity() {
        return ((Entity)(Object)this);
    }

    @ModifyReturnValue(method = "isInLava", at = @At(value = "RETURN"))
    private boolean familiar_friends$isInLava(boolean original) {
        if (this.familiar_friends$getEntity() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion.walkableFluids() == FluidTags.LAVA) {
                    return !this.firstUpdate && this.fluidHeight.getDouble(FluidTags.LAVA) > 0.4;
                }
            }
        }
        return original;
    }

    @ModifyReturnValue(method = "isTouchingWater", at = @At("RETURN"))
    private boolean familiar_friends$isTouchingWater(boolean original) {
        if (this.familiar_friends$getEntity() instanceof PlayerEntity player) {
            if (player.getCompanion() != null) {
                PlayerCompanion companion = player.getCompanion();
                if (companion.walkableFluids() == FluidTags.WATER) {
                    return !this.firstUpdate && this.fluidHeight.getDouble(FluidTags.WATER) > 0.4;
                }
            }
        }
        return original;
    }

}
