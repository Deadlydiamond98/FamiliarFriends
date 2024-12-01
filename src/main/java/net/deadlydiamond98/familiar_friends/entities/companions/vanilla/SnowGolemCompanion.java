package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntityTypes;
import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SnowGolemCompanion extends PlayerCompanion {

    public SnowGolemCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public SnowGolemCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntityTypes.Snow_Golem_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {
        if (nearestHostile == null || this.getWorld().isClient()) {
            return;
        }

        if (this.age % 20 == 0) {
            World world = this.getWorld();

            SnowballEntity snowball = new SnowballEntity(world, player);

            // Set the snowball's position
            double dX = nearestHostile.getX() - player.getX();
            double dY = nearestHostile.getBodyY(0.5) - player.getBodyY(0.5);
            double dZ = nearestHostile.getZ() - player.getZ();
            double distance = MathHelper.sqrt((float) (dX * dX + dZ * dZ));

            snowball.setVelocity(dX, dY + distance * 0.1, dZ, 1.0F, 0);

            // Play throw sound
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F / (this.random.nextFloat() * 0.4F + 0.8F));
            world.spawnEntity(snowball);
        }
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.snow_golem");
    }

    @Override
    public int getCost() {
        return 8;
    }
}
