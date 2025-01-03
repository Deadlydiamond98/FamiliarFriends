package net.deadlydiamond98.familiar_friends.entities.companions;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.TeleportTarget;
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
            float range = 100.0f;

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

                damagePlayer(range, distance, player);
                teleportPlayer(hitResult, player);
                setCooldownSeconds(15);
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

        if (calculatedDamage > 0) {
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
        return CompanionConfig.herobrineCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.herobrineEnabled;
    }
}
