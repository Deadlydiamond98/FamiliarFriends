package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.deadlydiamond98.familiar_friends.util.config.CompanionConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SkeletonCompanion extends PlayerCompanion {
    public SkeletonCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public SkeletonCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Skeleton_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (nearestHostile == null || player.getWorld().isClient()) {
            return;
        }

        if (this.age % 120 == 0) {
            World world = player.getWorld();

            PersistentProjectileEntity arrow = ProjectileUtil.createArrowProjectile(
                    player, Items.ARROW.getDefaultStack(), 4.0f);
            arrow.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;

            double dX = nearestHostile.getX() - player.getX();
            double dY = nearestHostile.getBodyY(0.5) - player.getBodyY(0.5);
            double dZ = nearestHostile.getZ() - player.getZ();
            double distance = MathHelper.sqrt((float) (dX * dX + dZ * dZ));


            arrow.setVelocity(dX, dY + distance * 0.1, dZ, 1.0F, 0);

            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
            world.spawnEntity(arrow);
        }
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.skeleton");
    }

    @Override
    public int getCost() {
        return CompanionConfig.skeletonCost;
    }

    @Override
    public boolean isEnabled() {
        return CompanionConfig.skeletonEnabled;
    }
}
