package net.deadlydiamond98.familiar_friends.entities;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public abstract class PlayerCompanion extends Entity implements Ownable {
    private int idleTime;
    private float currentAngle;
    public static final float SPEED = 1.5f;
    public static final float FOLLOW_DISTANCE = 5.0f;
    private Entity owner;
    private Vec3d prevOwnerPos;
    public final LimbAnimator limbAnimator;
    public float lastHandSwingProgress;
    public float handSwingProgress;
    public boolean handSwinging;
    public Hand preferredHand;
    public int handSwingTicks;
    protected float prevStepBobbingAmount;
    protected float stepBobbingAmount;
    public float bodyYaw;
    public float prevBodyYaw;
    public float headYaw;
    public float prevHeadYaw;
    protected float lookDirection;
    protected float prevLookDirection;

    protected boolean bookRender;

    public PlayerCompanion(EntityType<?> type, World world) {
        super(type, world);
        this.limbAnimator = new LimbAnimator();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    public PlayerCompanion(EntityType<?> type, World world, PlayerEntity owner, boolean gui) {
        this(type, world);

        this.idleTime = 0;
        this.currentAngle = 0.0f;
        this.owner = owner;
        this.prevOwnerPos = owner.getPos();
        this.setPosition(owner.getPos());
        this.noClip = true;

        this.bookRender = gui;
    }

    public boolean isBookRender() {
        return this.bookRender;
    }

    public void updateLimbs(boolean flutter) {
        float f = (float)MathHelper.magnitude(this.getX() - this.prevX, flutter ? this.getY() - this.prevY : 0.0, this.getZ() - this.prevZ);
        this.updateLimbs(f);
    }

    protected void updateLimbs(float posDelta) {
        float f = Math.min(posDelta * 4.0F, 1.0F);
        this.limbAnimator.updateLimbs(f, 0.4F);
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
        this.lastHandSwingProgress = this.handSwingProgress;
        super.baseTick();
    }

    private int getHandSwingDuration() {
        return 6;
    }

    public void swingHand(Hand hand) {
        this.swingHand(hand, false);
    }

    public void swingHand(Hand hand, boolean fromServerPlayer) {
        if (!this.handSwinging || this.handSwingTicks >= this.getHandSwingDuration() / 2 || this.handSwingTicks < 0) {
            this.handSwingTicks = -1;
            this.handSwinging = true;
            this.preferredHand = hand;
            if (this.getWorld() instanceof ServerWorld) {
                EntityAnimationS2CPacket entityAnimationS2CPacket = new EntityAnimationS2CPacket(this, hand == Hand.MAIN_HAND ? 0 : 3);
                ServerChunkManager serverChunkManager = ((ServerWorld)this.getWorld()).getChunkManager();
                if (fromServerPlayer) {
                    serverChunkManager.sendToNearbyPlayers(this, entityAnimationS2CPacket);
                } else {
                    serverChunkManager.sendToOtherNearbyPlayers(this, entityAnimationS2CPacket);
                }
            }
        }

    }

    protected void tickHandSwing() {
        int i = this.getHandSwingDuration();
        if (this.handSwinging) {
            ++this.handSwingTicks;
            if (this.handSwingTicks >= i) {
                this.handSwingTicks = 0;
                this.handSwinging = false;
            }
        } else {
            this.handSwingTicks = 0;
        }

        this.handSwingProgress = (float)this.handSwingTicks / (float)i;
    }

    protected float turnHead(float bodyRotation, float headRotation) {
        float f = MathHelper.wrapDegrees(bodyRotation - this.bodyYaw);
        this.bodyYaw += f * 0.3F;
        float g = MathHelper.wrapDegrees(this.getYaw() - this.bodyYaw);
        float h = 50;
        if (Math.abs(g) > h) {
            this.bodyYaw += g - (float)MathHelper.sign((double)g) * h;
        }

        boolean bl = g < -90.0F || g >= 90.0F;
        if (bl) {
            headRotation *= -1.0F;
        }

        return headRotation;
    }

    public float lerpYaw(float delta) {
        return MathHelper.lerp(delta, this.prevBodyYaw, this.bodyYaw);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient()) {
            if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
                if (!player.isAlive() || player.getWorld() != this.getWorld()) {
                    this.discard();
                    return;
                }

                this.prevOwnerPos = player.getPos();
                this.velocityDirty = true;
            }
            else {
                this.discard();
            }
        }
        else {
            this.updateTrackedPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch(), 20);
        }

        if (this.getOwner() != null && this.getOwner() instanceof PlayerEntity player) {
            this.moveAround(player);
        }

        this.move(MovementType.SELF, this.getVelocity());

        this.updateLimbs(false);

        double d = this.getX() - this.prevX;
        double e = this.getZ() - this.prevZ;
        float f = (float)(d * d + e * e);
        float g = this.bodyYaw;
        float h = 0.0F;
        this.prevStepBobbingAmount = this.stepBobbingAmount;
        float k = 0.0F;
        float l;
        if (f > 0.0025000002F) {
            k = 1.0F;
            h = (float)Math.sqrt((double)f) * 3.0F;
            l = (float)MathHelper.atan2(e, d) * 57.295776F - 90.0F;
            float m = MathHelper.abs(MathHelper.wrapDegrees(this.getYaw()) - l);
            if (95.0F < m && m < 265.0F) {
                g = l - 180.0F;
            } else {
                g = l;
            }
        }

        if (this.handSwingProgress > 0.0F) {
            g = this.getYaw();
        }

        this.getWorld().getProfiler().push("headTurn");
        h = this.turnHead(g, h);
        this.getWorld().getProfiler().pop();

        this.getWorld().getProfiler().pop();
        this.getWorld().getProfiler().push("rangeChecks");

        while(this.getYaw() - this.prevYaw < -180.0F) {
            this.prevYaw -= 360.0F;
        }

        while(this.getYaw() - this.prevYaw >= 180.0F) {
            this.prevYaw += 360.0F;
        }

        while(this.bodyYaw - this.prevBodyYaw < -180.0F) {
            this.prevBodyYaw -= 360.0F;
        }

        while(this.bodyYaw - this.prevBodyYaw >= 180.0F) {
            this.prevBodyYaw += 360.0F;
        }

        while(this.getPitch() - this.prevPitch < -180.0F) {
            this.prevPitch -= 360.0F;
        }

        while(this.getPitch() - this.prevPitch >= 180.0F) {
            this.prevPitch += 360.0F;
        }

        while(this.headYaw - this.prevHeadYaw < -180.0F) {
            this.prevHeadYaw -= 360.0F;
        }

        while(this.headYaw - this.prevHeadYaw >= 180.0F) {
            this.prevHeadYaw += 360.0F;
        }

        this.getWorld().getProfiler().pop();
        this.lookDirection += h;

        this.prevLookDirection = this.lookDirection;
        this.prevBodyYaw = this.bodyYaw;
        this.prevHeadYaw = this.headYaw;
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        this.getWorld().getProfiler().pop();
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

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

    protected abstract void doPassiveAction(PlayerEntity player, Entity nearestHostile);

    private void handleIdleMovement(PlayerEntity player) {
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

        this.currentAngle += angleIncrement;
        if (this.currentAngle > 2 * Math.PI) {
            this.currentAngle -= 2 * Math.PI;
        }

        double randomRadiusX = radius + (Math.random() * positionVariance - positionVariance / 2);
        double randomRadiusZ = radius + (Math.random() * positionVariance - positionVariance / 2);

        double targetX = entity.getX() + randomRadiusX * Math.cos(this.currentAngle);
        double targetZ = entity.getZ() + randomRadiusZ * Math.sin(this.currentAngle);
        double targetY = MathHelper.lerp(0.1, this.getY(), entity.getEyeY() - 0.75);
        Vec3d targetPosition = new Vec3d(targetX, targetY, targetZ);

        this.setVelocityTowards(targetPosition, speed);
    }

    public void setVelocityTowards(Vec3d targetPosition, double speed) {
        Vec3d direction = targetPosition.subtract(this.getPos()).normalize();
        Vec3d desiredVelocity = direction.multiply(speed);

        Vec3d currentVelocity = this.getVelocity();
        Vec3d interpolatedVelocity = currentVelocity.lerp(desiredVelocity, 0.1);

        this.setVelocity(interpolatedVelocity);
        float yaw = (float) (Math.atan2(interpolatedVelocity.z, interpolatedVelocity.x) * (180 / Math.PI)) - 270;
        this.setBodyYaw(yaw);
    }

    public Entity findNearestHostile(PlayerEntity player) {
        List<Entity> nearbyEntities = player.getWorld().getEntitiesByClass(Entity.class, player.getBoundingBox().expand(10),
                entity -> entity instanceof Monster && entity.isAlive());
        return nearbyEntities.stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(player))).orElse(null);
    }

    public Text getDescription() {
        return Text.translatable(this.getType().getTranslationKey() + ".description");
    }

    public Text getAuthor() {
        return Text.translatable(this.getType().getTranslationKey() + ".author");
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
    }
}
