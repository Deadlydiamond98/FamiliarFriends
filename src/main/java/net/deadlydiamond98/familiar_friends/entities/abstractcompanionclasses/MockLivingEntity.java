package net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
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

public class MockLivingEntity extends Entity {

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
    protected int bodyTrackingIncrements;
    protected double serverX;
    protected double serverY;
    protected double serverZ;
    protected double serverYaw;
    protected double serverPitch;
    protected double serverHeadYaw;
    protected int headTrackingIncrements;

    public MockLivingEntity(EntityType<?> type, World world) {
        super(type, world);
        this.limbAnimator = new LimbAnimator();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    public void updateLimbs(boolean flutter) {
        float f = (float) MathHelper.magnitude(this.getX() - this.prevX, flutter ? this.getY() - this.prevY : 0.0, this.getZ() - this.prevZ);
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
        super.baseTick();
        this.lastHandSwingProgress = this.handSwingProgress;
        this.prevLookDirection = this.lookDirection;
        this.prevBodyYaw = this.bodyYaw;
        this.prevHeadYaw = this.headYaw;
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.getWorld().getProfiler().pop();
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

        if (!this.isRemoved()) {
            this.tickMovement();
        }

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
    }

    protected void tickMovement() {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.bodyTrackingIncrements = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }

        if (this.bodyTrackingIncrements > 0) {
            this.lerpPosAndRotation(this.bodyTrackingIncrements, this.serverX, this.serverY, this.serverZ, this.serverYaw, this.serverPitch);
            --this.bodyTrackingIncrements;
        } else if (!this.canMoveVoluntarily()) {
            this.setVelocity(this.getVelocity().multiply(0.98));
        }

        if (this.headTrackingIncrements > 0) {
            this.lerpHeadYaw(this.headTrackingIncrements, this.serverHeadYaw);
            --this.headTrackingIncrements;
        }

        Vec3d vec3d = this.getVelocity();
        double d = vec3d.x;
        double e = vec3d.y;
        double f = vec3d.z;
        if (Math.abs(vec3d.x) < 0.003) {
            d = 0.0;
        }

        if (Math.abs(vec3d.y) < 0.003) {
            e = 0.0;
        }

        if (Math.abs(vec3d.z) < 0.003) {
            f = 0.0;
        }

        this.setVelocity(d, e, f);
    }

    protected void lerpHeadYaw(int headTrackingIncrements, double serverHeadYaw) {
        this.headYaw = (float)MathHelper.lerpAngleDegrees(1.0 / (double)headTrackingIncrements, (double)this.headYaw, serverHeadYaw);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}

