package net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.Control;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class NonLivingLookControl implements Control {
    protected final MockMobEntity entity;
    protected float maxYawChange;
    protected float maxPitchChange;
    protected int lookAtTimer;
    protected double x;
    protected double y;
    protected double z;

    public NonLivingLookControl(MockMobEntity entity) {
        this.entity = entity;
    }

    public void lookAt(Vec3d direction) {
        this.lookAt(direction.x, direction.y, direction.z);
    }

    public void lookAt(Entity entity) {
        this.lookAt(entity.getX(), getLookingHeightFor(entity), entity.getZ());
    }

    public void lookAt(Entity entity, float maxYawChange, float maxPitchChange) {
        this.lookAt(entity.getX(), getLookingHeightFor(entity), entity.getZ(), maxYawChange, maxPitchChange);
    }

    public void lookAt(double x, double y, double z) {
        this.lookAt(x, y, z, (float)this.entity.getMaxLookYawChange(), (float)this.entity.getMaxLookPitchChange());
    }

    public void lookAt(double x, double y, double z, float maxYawChange, float maxPitchChange) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.maxYawChange = maxYawChange;
        this.maxPitchChange = maxPitchChange;
        this.lookAtTimer = 2;
    }

    public void tick() {
        if (this.shouldStayHorizontal()) {
            this.entity.setPitch(0.0F);
        }

        if (this.lookAtTimer > 0) {
            --this.lookAtTimer;

            this.getTargetYaw().ifPresent((targetYaw) -> {
                float maxHeadRotation = (float) this.entity.getMaxHeadRotation();
                float headYawDifference = MathHelper.subtractAngles(this.entity.bodyYaw, targetYaw);

                if (Math.abs(headYawDifference) <= maxHeadRotation) {
                    this.entity.headYaw = this.changeAngle(this.entity.headYaw, targetYaw, this.maxYawChange);
                } else {
                    this.entity.bodyYaw = this.changeAngle(this.entity.bodyYaw, targetYaw, this.maxYawChange);
                    float clampedHeadYaw = MathHelper.clampAngle(targetYaw, this.entity.bodyYaw, maxHeadRotation);
                    this.entity.headYaw = this.changeAngle(this.entity.headYaw, clampedHeadYaw, this.maxYawChange);
                }
            });

            this.getTargetPitch().ifPresent((pitch) -> {
                this.entity.setPitch(this.changeAngle(this.entity.getPitch(), pitch, this.maxPitchChange));
            });
        } else {
            this.entity.headYaw = this.changeAngle(this.entity.headYaw, this.entity.bodyYaw, 10.0F);
        }

        this.clampHeadYaw();
    }

    protected void clampHeadYaw() {
        this.entity.headYaw = MathHelper.clampAngle(this.entity.headYaw, this.entity.bodyYaw, (float) this.entity.getMaxHeadRotation());
    }

    protected boolean shouldStayHorizontal() {
        return true;
    }

    public boolean isLookingAtSpecificPosition() {
        return this.lookAtTimer > 0;
    }

    public double getLookX() {
        return this.x;
    }

    public double getLookY() {
        return this.y;
    }

    public double getLookZ() {
        return this.z;
    }

    protected Optional<Float> getTargetPitch() {
        double d = this.x - this.entity.getX();
        double e = this.y - this.entity.getEyeY();
        double f = this.z - this.entity.getZ();
        double g = Math.sqrt(d * d + f * f);
        return !(Math.abs(e) > 9.999999747378752E-6) && !(Math.abs(g) > 9.999999747378752E-6) ? Optional.empty() : Optional.of((float)(-(MathHelper.atan2(e, g) * 57.2957763671875)));
    }

    protected Optional<Float> getTargetYaw() {
        double d = this.x - this.entity.getX();
        double e = this.z - this.entity.getZ();
        return !(Math.abs(e) > 9.999999747378752E-6) && !(Math.abs(d) > 9.999999747378752E-6) ? Optional.empty() : Optional.of((float)(MathHelper.atan2(e, d) * 57.2957763671875) - 90.0F);
    }

    protected float changeAngle(float from, float to, float max) {
        float f = MathHelper.subtractAngles(from, to);
        float g = MathHelper.clamp(f, -max, max);
        return from + g;
    }

    private static double getLookingHeightFor(Entity entity) {
        return entity instanceof LivingEntity ? entity.getEyeY() : (entity.getBoundingBox().minY + entity.getBoundingBox().maxY) / 2.0;
    }
}
