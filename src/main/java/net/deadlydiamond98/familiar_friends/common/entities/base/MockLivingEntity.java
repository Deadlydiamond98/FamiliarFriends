package net.deadlydiamond98.familiar_friends.common.entities.base;

import net.deadlydiamond98.koalalib.common.entity.LerpedMovmentEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MockLivingEntity extends LerpedMovmentEntity implements Ownable {
    public final LimbAnimator limbAnimator;
    public float lastHandSwingProgress;
    public float handSwingProgress;
    public float bodyYaw;
    public float prevBodyYaw;
    public float headYaw;
    public float prevHeadYaw;
    protected float lookDirection;
    protected float prevLookDirection;

    private int headSteps;
    private float serverHeadYaw;

    protected NonLivingLookControl lookControl;
    private LookAroundRandomlyBehavior currentLookBehavior;
    private int idlingTime;
    private float orbitArc;

    @Nullable
    private UUID ownerUuid;
    @Nullable private Entity owner;
    

    public MockLivingEntity(EntityType<?> type, World world) {
        super(type, world);
        this.limbAnimator = new LimbAnimator();
        this.lookControl = new NonLivingLookControl(this);
    }

    public void updateLimbs() {
        float f = (float)MathHelper.magnitude(this.getX() - this.prevX, 0, this.getZ() - this.prevZ);
        this.updateLimbs(f * 0.5f);
    }

    protected void updateLimbs(float posDelta) {
        float f = Math.min(posDelta * 4.0f, 1.0f);
        this.limbAnimator.updateLimbs(f, 0.4f);
    }

    public float getHandSwingProgress(float tickDelta) {
        float f = this.handSwingProgress - this.lastHandSwingProgress;
        if (f < 0.0F) {
            ++f;
        }
        return this.lastHandSwingProgress + f * tickDelta;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.lastHandSwingProgress = this.handSwingProgress;
        this.prevLookDirection = this.lookDirection;
        this.prevBodyYaw = this.bodyYaw;
        this.prevHeadYaw = this.headYaw;
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getWorld().getProfiler().pop();
    }

    protected float turnHead(float bodyRotation, float headRotation) {
        float f = MathHelper.wrapDegrees(bodyRotation - this.bodyYaw);
        this.bodyYaw += f * 0.3F;
        float g = MathHelper.wrapDegrees(this.getYaw() - this.bodyYaw);
        float h = 50;
        if (Math.abs(g) > h) {
            this.bodyYaw += g - (float)MathHelper.sign(g) * h;
        }

        boolean bl = g < -90.0F || g >= 90.0F;
        if (bl) {
            headRotation *= -1.0F;
        }

        return headRotation;
    }

    @Override
    public void tick() {
        super.tick();

        double d = this.getX() - this.prevX;
        double e = this.getZ() - this.prevZ;
        float f = (float)(d * d + e * e);
        float g = this.bodyYaw;
        float h = 0.0f;
        if (f > 0.0025000002f) {
            h = (float)Math.sqrt(f) * 3.0f;
            float l = (float)MathHelper.atan2(e, d) * 57.295776f - 90.0f;
            float m = MathHelper.abs(MathHelper.wrapDegrees(this.getYaw()) - l);
            g = 95.0f < m && m < 265.0f ? l - 180.0f : l;
        }
        if (this.handSwingProgress > 0.0f) {
            g = this.getYaw();
        }
        this.getWorld().getProfiler().push("headTurn");
        h = this.turnHead(g, h);
        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("rangeChecks");
        while (this.getYaw() - this.prevYaw < -180.0f) {
            this.prevYaw -= 360.0f;
        }
        while (this.getYaw() - this.prevYaw >= 180.0f) {
            this.prevYaw += 360.0f;
        }
        while (this.bodyYaw - this.prevBodyYaw < -180.0f) {
            this.prevBodyYaw -= 360.0f;
        }
        while (this.bodyYaw - this.prevBodyYaw >= 180.0f) {
            this.prevBodyYaw += 360.0f;
        }
        while (this.getPitch() - this.prevPitch < -180.0f) {
            this.prevPitch -= 360.0f;
        }
        while (this.getPitch() - this.prevPitch >= 180.0f) {
            this.prevPitch += 360.0f;
        }
        while (this.headYaw - this.prevHeadYaw < -180.0f) {
            this.prevHeadYaw -= 360.0f;
        }
        while (this.headYaw - this.prevHeadYaw >= 180.0f) {
            this.prevHeadYaw += 360.0f;
        }
        this.lookDirection += h;

        this.getWorld().getProfiler().pop();

        lookBehavior();

        if (!this.getWorld().isClient()) {
            if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
                if (!player.isAlive() || player.getWorld() != this.getWorld()) {
                    this.discard();
                    return;
                }

                if (player.getCurrentCompanion() == this) {
                    handleMovement(player);
                    this.velocityDirty = true;
                } else {
                    discard();
                }
            }
            else {
                this.discard();
            }
        }
        this.move(MovementType.SELF, this.getVelocity());

        this.lookControl.tick();
        this.updateLimbs();
    }

    @Override
    protected void tickSyncedMovement() {
        if (this.headSteps > 0) {
            this.headYaw += (float)MathHelper.wrapDegrees(this.serverHeadYaw - (double)this.headYaw) / (float)this.headSteps;
            --this.headSteps;
        }

        super.tickSyncedMovement();
    }

    @Override
    public void updateTrackedHeadRotation(float yaw, int interpolationSteps) {
        this.serverHeadYaw = yaw;
        this.headSteps = interpolationSteps;
    }

    protected void handleMovement(PlayerEntity owner) {
        double distanceToTarget = this.getPos().distanceTo(owner.getPos());

        if (distanceToTarget > 20) {
            this.setPosition(owner.getPos());
        }

        if (this.idlingTime++ <= 500) {
            this.setVelocity(this.getVelocity().multiply(0.8f));

            Vec3d getPlayerShoulderPos = getTargetPosition(owner, -0.6, 1);
            Vec3d direction = getPlayerShoulderPos.subtract(this.getPos()).normalize();
            double distance = getPlayerShoulderPos.distanceTo(this.getPos());

            if (distance > 0.5) {
                double multiplier = distance > 2 ? 0.05 : 0.025;

                this.setVelocity(this.getVelocity().add(direction.multiply(multiplier * distance)));
                this.idlingTime = 0;
            }
        } else {
            if (distanceToTarget > 5) {
                this.idlingTime = 0;
            } else {
                circleAround(owner, 2.5, 0.1);
            }
        }
    }

    protected Vec3d getTargetPosition(PlayerEntity player, double front, double side) {
        Vec3d lookDirection = player.getRotationVector().normalize();

        float rad = (float) Math.toRadians(player.getHeadYaw());
        double offsetX = front * Math.cos(rad) + side * Math.sin(rad);
        double offsetZ = front * Math.sin(rad) - side * Math.cos(rad);

        return player.getEyePos().add(offsetX, 0, offsetZ).add(lookDirection.x, 0, lookDirection.z);
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

    protected void lookBehavior() {
        if (this.idlingTime <= 500) {
            if (this.currentLookBehavior == null || this.currentLookBehavior.isFinished()) {
                this.currentLookBehavior = new LookAroundRandomlyBehavior(this);
                this.currentLookBehavior.start();
            }

            this.currentLookBehavior.tick();
        }
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (this.owner != null && !this.owner.isRemoved()) {
            return this.owner;
        }
        if (this.ownerUuid != null && this.getWorld() instanceof ServerWorld) {
            this.owner = ((ServerWorld)this.getWorld()).getEntity(this.ownerUuid);
            return this.owner;
        }
        return null;
    }

    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUuid = entity.getUuid();
            this.owner = entity;
        }
    }

    @Override
    protected void initDataTracker() {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
            this.owner = null;
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
    }

    public NonLivingLookControl getLookControl() {
        return this.lookControl;
    }

    public int getMaxLookYawChange() {
        return 10;
    }

    public int getMaxLookPitchChange() {
        return 40;
    }

    public int getMaxHeadRotation() {
        return 75;
    }
}