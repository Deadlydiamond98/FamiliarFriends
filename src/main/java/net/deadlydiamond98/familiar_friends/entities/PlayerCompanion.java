package net.deadlydiamond98.familiar_friends.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public class PlayerCompanion extends Entity implements Ownable {

    private static final TrackedData<Boolean> visable;
    private int fairyType;
    private int idleTime;
    private boolean fireSound;
    private float currentAngle;
    public static final float SPEED = 1.5f;
    public static final float FOLLOW_DISTANCE = 5.0f;
    private Entity owner;
    private Vec3d prevOwnerPos;

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(visable, true);
    }
    static {
        visable = DataTracker.registerData(PlayerCompanion.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    public PlayerCompanion(EntityType<?> type, World world) {
        super(type, world);
        this.setVisable(true);
    }
    public PlayerCompanion(World world, PlayerEntity owner) {
        this(CompanionEntities.Player_Companion, world);

        this.idleTime = 0;
        this.fireSound = true;
        this.currentAngle = 0.0f;
        this.owner = owner;
        this.prevOwnerPos = owner.getPos();
        this.setPosition(owner.getPos());
        this.setVisable(true);
        this.noClip = true;
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

                this.moveAround(player);

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

        this.move(MovementType.SELF, this.getVelocity());

        this.prevYaw = this.getYaw();
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        this.getWorld().getProfiler().pop();
    }

    private void moveAround(PlayerEntity player) {
        double distanceToTarget = this.getPos().distanceTo(player.getPos());
        Entity nearestHostile = findNearestHostile(player);

        // Targeting Entities
        if (player.getAttacker() != null && player.distanceTo(player.getAttacker()) <= 10) {
            if (this.age % 100 == 0) {
//                playAttentionSound(player);
            }
            this.circleAround(player.getAttacker(), 1.0, 0.5);
            stopIdle(player);
        }
        else if (nearestHostile != null && player.distanceTo(nearestHostile) <= 10) {
            if (this.age % 100 == 0) {
//                playAttentionSound(player);
            }
            this.circleAround(nearestHostile, 1.0, 0.5);
            stopIdle(player);
        }

        // Flying back to player
        else if (distanceToTarget > FOLLOW_DISTANCE * 4) {
            this.setPosition(player.getPos());
        }
        else if (distanceToTarget > FOLLOW_DISTANCE * 2.5) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), SPEED);
        } else if (distanceToTarget > FOLLOW_DISTANCE) {
            this.setVelocityTowards(player.getPos().add(0, 2, 0), SPEED * 0.5);
        } else {
            this.handleIdleMovement(player);
        }
    }

    private void handleIdleMovement(PlayerEntity player) {
        this.idleTime++;
        if (this.idleTime <= 600) {
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
                }
                else {
                    this.setVelocityTowards(shoulderPos, 0);
                }
            }
            else {
                this.circleAround(player, 5.0, 0.1);
            }

            if (this.idleTime % 200 == 0 && this.idleTime >= 200) {
//                playIdleSound(player);
            }
        } else {
            Vec3d targetPos = player.getPos().add(0, player.getEyeHeight(player.getPose()) - 0.5, 0);
            this.setVelocityTowards(targetPos, SPEED * 0.5);
            if (this.getPos().distanceTo(targetPos) <= 0.2) {
                setVisable(false);
                if (this.fireSound) {
//                    playInSound(player);
                    this.fireSound = false;
                }
            }
        }

        if (player.getPos() != this.prevOwnerPos) {
            stopIdle(player);
        }
    }

    private void stopIdle(PlayerEntity player) {
        this.idleTime = 0;
        if (!this.getVisable()) {
            this.setVisable(true);
            this.fireSound = true;
            this.setVelocityTowards(player.getPos().add(0.0, 2.0, 0.0), SPEED * 0.5);
//            playOutSound(player);
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
        double targetY = MathHelper.lerp(0.1, this.getY(), entity.getEyeY() + (Math.sin(age) * 0.5));
        Vec3d targetPosition = new Vec3d(targetX, targetY, targetZ);

        this.setVelocityTowards(targetPosition, speed);
    }

    public void setVelocityTowards(Vec3d targetPosition, double speed) {
        Vec3d direction = targetPosition.subtract(this.getPos()).normalize();
        Vec3d desiredVelocity = direction.multiply(speed);

        Vec3d currentVelocity = this.getVelocity();
        Vec3d interpolatedVelocity = currentVelocity.lerp(desiredVelocity, 0.1);

        this.setVelocity(interpolatedVelocity);
        float yaw = (float) (Math.atan2(interpolatedVelocity.z, interpolatedVelocity.x) * (180 / Math.PI)) - 90;
        this.setYaw(yaw);
    }

    public Entity findNearestHostile(PlayerEntity player) {
        List<Entity> nearbyEntities = player.getWorld().getEntitiesByClass(Entity.class, player.getBoundingBox().expand(10),
                entity -> entity instanceof Monster && entity.isAlive());
        return nearbyEntities.stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(player))).orElse(null);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return this.owner;
    }

    public Boolean getVisable() {
        return this.dataTracker.get(visable);
    }

    private void setVisable(Boolean vis) {
        this.dataTracker.set(visable, vis);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("visable")) {
            this.setVisable(nbt.getBoolean("visable"));
        }
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("visable", this.getVisable());
    }

//    private void playAttentionSound(PlayerEntity player) {
//        if (this.fairyType == 1) {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.NaviAttention, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//        else if (this.fairyType == 2) {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlAttention, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//    }

//    private void playIdleSound(PlayerEntity player) {
//        if (this.fairyType == 1) {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.NaviHello, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//        else if (this.fairyType == 2) {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlSad, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//        else {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.FairyAmbient, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//    }
//
//    private void playOutSound(PlayerEntity player) {
//        if (this.fairyType == 2) {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlOut, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//        else {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.FairyOut, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//    }
//
//    private void playInSound(PlayerEntity player) {
//        if (this.fairyType == 2) {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.TatlIn, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//        else {
//            player.getWorld().playSound(null, this.getBlockPos(), ZeldaSounds.FairyIn, SoundCategory.PLAYERS, 1.0f,
//                    1.0f + (player.getRandom().nextBetween(-2, 2) * 0.05f));
//        }
//    }
}
