package net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.behaviors.LookAroundBehavior;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.behaviors.LookBehavior;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public abstract class PlayerCompanion extends MockMobEntity implements Ownable {

    private int idleTime;
    private float orbitArc;
    private Entity owner;
    public static final float SPEED = 1.5f;
    public static final float FOLLOW_DISTANCE = 5.0f;

    protected boolean bookRender;

    private LookBehavior currentLookBehavior;

    public PlayerCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public PlayerCompanion(EntityType<?> type, World world, PlayerEntity owner, boolean gui) {
        this(type, world);

        this.idleTime = 0;
        this.orbitArc = 0.0f;
        this.noClip = true;
        this.owner = owner;
        this.setPosition(owner.getPos());
        this.bookRender = gui;
    }

    public boolean isBookRender() {
        return this.bookRender;
    }

    @Override
    public void baseTick() {
        if (!this.getWorld().isClient()) {
            if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
                if (!player.isAlive() || player.getWorld() != this.getWorld()) {
                    this.discard();
                    return;
                }
            }
            else {
                this.discard();
            }
        }

        super.baseTick();
    }

    @Override
    protected void tickMovement() {

        lookBehavior();

        if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
            this.moveAround(player);
        }
        this.move(MovementType.SELF, this.getVelocity());

        super.tickMovement();
    }

    protected void lookBehavior() {
        if (this.idleTime <= 500) {
            if (this.currentLookBehavior == null || this.currentLookBehavior.isFinished()) {
                this.currentLookBehavior = new LookAroundBehavior(this);
                this.currentLookBehavior.start();
            }

            this.currentLookBehavior.tick();
        }
    }


    private void moveAround(PlayerEntity player) {
        double distanceToTarget = this.getPos().distanceTo(player.getPos());

        if (distanceToTarget > FOLLOW_DISTANCE * 4) {
            this.setPosition(player.getPos());
        }
        else if (distanceToTarget > FOLLOW_DISTANCE * 2.5) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), SPEED * 2);
            this.idleTime = 0;
        } else if (distanceToTarget > FOLLOW_DISTANCE) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), SPEED);
            this.idleTime = 0;
        } else {
            this.handleIdleMovement(player);
        }

        doPassiveAction(player, findNearestHostile(player));
    }

    protected abstract void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile);

    protected void handleIdleMovement(PlayerEntity player) {
        this.idleTime++;
        if (this.idleTime <= 500) {

            Vec3d lookDirection = player.getRotationVec(1.0F).normalize();

            double yawRad = Math.toRadians(player.getYaw());

            double offsetFront = -0.6;
            double offsetRight = -1;

            double offsetX = offsetRight * MathHelper.cos((float) yawRad) - offsetFront * MathHelper.sin((float) yawRad);
            double offsetZ = offsetRight * MathHelper.sin((float) yawRad) + offsetFront * MathHelper.cos((float) yawRad);

            Vec3d shoulderPos = player.getPos().add(
                    offsetX,
                    player.getEyeHeight(player.getPose()) + 0.1,
                    offsetZ).add(lookDirection);

            if (this.getPos().subtract(shoulderPos).horizontalLength() > 0.5) {
                this.setVelocityTowards(shoulderPos, SPEED * 0.1);
                this.idleTime = 0;
            }
            else {
                this.setVelocityTowards(shoulderPos, 0);
            }
        }
        else {
            this.circleAround(player, 2.5, 0.1);
        }
    }

    public void circleAround(Entity entity, double radius, double speed) {
        double angleVariance = 0.01;
        double positionVariance = 0.01;

        double angleIncrement = (0.5 * speed) + (Math.random() * angleVariance - angleVariance / 2);

        this.orbitArc += angleIncrement;
        if (this.orbitArc > 2 * Math.PI) {
            this.orbitArc -= 2 * Math.PI;
        }

        double randomRadiusX = radius + (Math.random() * positionVariance - positionVariance / 2);
        double randomRadiusZ = radius + (Math.random() * positionVariance - positionVariance / 2);

        double targetX = entity.getX() + randomRadiusX * Math.cos(this.orbitArc);
        double targetZ = entity.getZ() + randomRadiusZ * Math.sin(this.orbitArc);
        double targetY = MathHelper.lerp(0.1, this.getY(), entity.getEyeY() - 0.75);
        Vec3d targetPosition = new Vec3d(targetX, targetY, targetZ);

        this.setVelocityTowards(targetPosition, speed);
        this.getLookControl().lookAt(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
    }

    public void setVelocityTowards(Vec3d targetPosition, double speed) {
        Vec3d direction = targetPosition.subtract(this.getPos()).normalize();
        Vec3d desiredVelocity = direction.multiply(speed);

        Vec3d currentVelocity = this.getVelocity();
        Vec3d interpolatedVelocity = currentVelocity.lerp(desiredVelocity, 0.1);

        this.setVelocity(interpolatedVelocity);
    }

    public LivingEntity findNearestHostile(PlayerEntity player) {
        List<LivingEntity> nearbyEntities = player.getWorld().getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(10),
                entity -> entity instanceof Monster && entity.isAlive());
        return nearbyEntities.stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(player))).orElse(null);
    }

    public Entity findNearestEntity(PlayerEntity player) {
        List<Entity> nearbyEntities = player.getWorld().getEntitiesByClass(Entity.class, player.getBoundingBox().expand(10),
                entity -> entity.isAlive());
        return nearbyEntities.stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(player))).orElse(null);
    }

    public Text getDescription() {
        return Text.translatable(this.getType().getTranslationKey() + ".description",
                Text.translatable(FamiliarFriends.Current_Keybinding_Key).getString());
    }

    public Text getAuthor() {
        return Text.translatable("gui.familiar_friends.author").append(
                Text.translatable(this.getType().getTranslationKey() + ".author"));
    }

    public boolean isLocked(PlayerEntity player) {
        return !((CompanionPlayerData) player).isCompanionUnlocked(this);
    }

//    public abstract void doKeyEvent(PlayerEntity player);

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }
}
