package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class NotchCompanion extends PlayerCompanion {
    public NotchCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public NotchCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Notch_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

        double range = 100.0;

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
            }
        }
    }

    @Override
    public int getCost() {
        return 20;
    }

}
