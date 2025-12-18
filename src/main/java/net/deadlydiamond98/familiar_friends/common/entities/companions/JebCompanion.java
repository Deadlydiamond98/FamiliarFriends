package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class JebCompanion extends PlayerCompanion {
    public JebCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public JebCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Jeb_Companion, world, owner, gui);
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {
        boolean noCooldown = hasNoCooldown(player);

        if (noCooldown) {
            double range = FamiliarFriendsConfig.Jeb.smiteDistance;

            Vec3d startPos = player.getCameraPosVec(1.0F);
            Vec3d lookVec = player.getRotationVec(1.0F);
            Vec3d endPos = startPos.add(lookVec.multiply(range));

            BlockHitResult hitResult = player.getWorld().raycast(new RaycastContext(
                    startPos,
                    endPos,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    player
            ));

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos hitPos = hitResult.getBlockPos();
                Direction hitSide = hitResult.getSide();
                BlockPos againstBlockPos = hitPos.offset(hitSide);

                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(player.getWorld());
                if (lightning != null) {
                    lightning.refreshPositionAfterTeleport(Vec3d.ofCenter(againstBlockPos));
                    player.getWorld().spawnEntity(lightning);
                    setCooldownSeconds(FamiliarFriendsConfig.Jeb.lightningCooldown);
                }
            }
        }
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Jeb.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Jeb.enabled;
    }

}
