package net.deadlydiamond98.familiar_friends.common.entities.companions;

import net.deadlydiamond98.familiar_friends.FamiliarFriendsConfig;
import net.deadlydiamond98.familiar_friends.init.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.common.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class HerobrineCompanion extends PlayerCompanion {

    public HerobrineCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public HerobrineCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Herobrine_Companion, world, owner, gui);
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

        boolean enoughHealth = hasNoHealthLimitation(player, 1);
        boolean noCooldown = hasNoCooldown(player);

        if (enoughHealth && noCooldown) {
            double range = FamiliarFriendsConfig.Herobrine.teleportDistance;

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

                double distance = startPos.distanceTo(hitResult.getPos());

                damagePlayer((float) range, distance, player);
                teleportPlayer(hitResult, player);
                setCooldownSeconds(FamiliarFriendsConfig.Herobrine.teleportCooldown);
            }
        }
    }
    private void damagePlayer(float range, double distance, PlayerEntity player) {

        float calculatedDamage = (float) ((distance / range) * 15.0f);

        float currentHealth = player.getHealth();

        float maxDamage = currentHealth - 1.0f;
        if (calculatedDamage > maxDamage) {
            calculatedDamage = maxDamage;
        }

        if (calculatedDamage > 0 && FamiliarFriendsConfig.Herobrine.shouldDamagePlayer) {
            player.damage(player.getDamageSources().fall(), calculatedDamage);
        }
    }

    private void teleportPlayer(BlockHitResult hitResult, PlayerEntity player) {
        BlockPos hitPos = hitResult.getBlockPos();
        Direction hitSide = hitResult.getSide();
        BlockPos againstBlockPos = hitPos.offset(hitSide);

        if (!player.getWorld().isClient()) {
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS);
            player.teleport(againstBlockPos.getX(), againstBlockPos.getY(), againstBlockPos.getZ());
            player.onLanding();
        }
    }

    @Override
    public int getCost() {
        return FamiliarFriendsConfig.Herobrine.cost;
    }

    @Override
    public boolean isEnabled() {
        return FamiliarFriendsConfig.Herobrine.enabled;
    }
}
