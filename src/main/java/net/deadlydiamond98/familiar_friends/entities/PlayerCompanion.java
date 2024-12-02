package net.deadlydiamond98.familiar_friends.entities;

import net.deadlydiamond98.familiar_friends.FamiliarFriends;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.MockMobEntity;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.behaviors.LookAroundBehavior;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.behaviors.LookBehavior;
import net.deadlydiamond98.familiar_friends.networking.CompanionServerPackets;
import net.deadlydiamond98.familiar_friends.sounds.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.CompanionPlayerData;
import net.deadlydiamond98.familiar_friends.util.TimeUnitHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public abstract class PlayerCompanion extends MockMobEntity implements Ownable {

    public static final float SPEED = 1.5f;
    public static final float FOLLOW_DISTANCE = 5.0f;

    private int idleTime;
    private float orbitArc;
    private Entity owner;
    private int cooldown;

    private int costClient;
    private boolean enabledClient;
    protected boolean bookRender;

    private LookBehavior currentLookBehavior;

    public PlayerCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public PlayerCompanion(EntityType<?> type, World world, PlayerEntity owner, boolean gui) {
        this(type, world);

        this.costClient = 0;
        this.enabledClient = false;

        this.cooldown = 0;
        this.idleTime = 0;
        this.orbitArc = 0.0f;
        this.noClip = true;
        this.owner = owner;
        this.setPosition(owner.getPos());
        this.bookRender = gui;
    }

    /**
     * Anything here will be run every tick
     * @param player The player the companion belongs to
     * @param nearestHostile The nearest hostile mob
     */
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {

    }
    /**
     * Anything here will be run whenever the player presses the action key
     * @param player The player the companion belongs to
     */
    public void doKeyEvent(PlayerEntity player) {

    }

    /**
     * Anything here will be run whenever the player attacks a target
     *
     * @param player The player the companion belongs to
     * @param target The target that is being attacked
     * @param amount The damage delt to attackers
     */
    public void onAttack(PlayerEntity player, LivingEntity target, float amount) {

    }

    /**
     * Anything here will be run whenever right before the player is about to die
     * @param player The player the companion belongs to
     */
    public void onPlayerDeath(PlayerEntity player) {

    }

    /**
     * Anything here will be run whenever the player takes damage
     * @param source The source of the taken damage
     * @param amount The amount of damage the player is taking
     * @param player The player the companion belongs to
     * @return returns false by default, if set to true, damage will be ignored
     */
    public boolean onDamaged(DamageSource source, float amount, PlayerEntity player) {
        return false;
    }

    /**
     * @return if this is true, the player will be allowed the ability to climb up walls
     */
    public boolean canClimbWalls() {
        return false;
    }

    /**
     * @param player The companion's player
     * @return Returns the Hostile Entity that is closest to the player
     */
    public LivingEntity findNearestHostile(PlayerEntity player) {
        List<LivingEntity> nearbyEntities = player.getWorld().getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(10),
                entity -> entity instanceof Monster && entity.isAlive());
        return nearbyEntities.stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(player))).orElse(null);
    }

    /**
     * @param player The companion's player
     * @return Returns the Living Entity that is closest to the player
     */
    public Entity findNearestEntity(PlayerEntity player) {
        List<Entity> nearbyEntities = player.getWorld().getEntitiesByClass(Entity.class, player.getBoundingBox().expand(10),
                entity -> entity.isAlive());
        return nearbyEntities.stream().min(Comparator.comparingDouble(entity -> entity.squaredDistanceTo(player))).orElse(null);
    }


    /**
     * Used for determining how many XP levels a companion will cost in the Book
     */
    public abstract int getCost();

    /**
     * Used for determining if a companion can show up in the book, used for configs to disable a companion!
     */
    public abstract boolean isEnabled();

    /**
     * @param player The companion's player
     * @param minimum The Minimum Health the player needs for the action
     * @return returns true if the player's hunger is greater than the minimum, otherwise this will return false and
     * warn the player that their health is too low
     */
    public boolean hasNoHealthLimitation(PlayerEntity player, int minimum) {
        return hasNoLimitationOf(player.getHealth(), minimum, player, Text.translatable("cooldown.familiar_friends.lowstat",
                Text.translatable("cooldown.familiar_friends.health").getString()), true);
    }

    /**
     * @param player The companion's player
     * @param minimum The Minimum Hunger the player needs for the action
     * @return returns true if the player's hunger is greater than the minimum, otherwise this will return false and
     * warn the player that their hunger is too low
     */
    public boolean hasNoHungerLimitation(PlayerEntity player, int minimum) {
        return hasNoLimitationOf(player.getHungerManager().getFoodLevel(), minimum, player, Text.translatable("cooldown.familiar_friends.lowstat",
                Text.translatable("cooldown.familiar_friends.hunger").getString()), true);
    }

    /**
     * @param currentValue The value you want to check for
     * @param threshold The minimum value current value can be
     * @param player The companion's player
     * @param translation The Text to be displayed if false
     * @param trueIfCreative sets the return value to true if the player is in creative or spectator
     * @return returns true if the current value is greater than the threshold, otherwise this will return false,
     * and will display the translation along with a failure sound
     */
    public boolean hasNoLimitationOf(float currentValue, float threshold, PlayerEntity player, Text translation, boolean trueIfCreative) {
        if (trueIfCreative && (player.isCreative() || player.isSpectator())) {
            return true;
        }
        if (currentValue <= threshold) {
            player.getWorld().playSound(null, player.getBlockPos(), CompanionSounds.Action_Failed, SoundCategory.PLAYERS, 0.5f, 1.0f);
            player.sendMessage(translation, true);
        }
        return !(currentValue <= threshold);
    }

    /**
     * @param player The companion's player
     * @return returns true if the companion isn't on cooldown (when cooldown is 0), if false, will display the
     * cooldown on screen in seconds, minutes, or hours depending on how long the cooldown is
     */
    public boolean hasNoCooldown(PlayerEntity player) {
        String cooldownUnit = TimeUnitHelper.getCooldownUnitText(this.cooldown);
        int time = TimeUnitHelper.calculateCooldownUnit(this.cooldown);

        return hasNoLimitationOf(1, this.cooldown, player, Text.translatable("cooldown.familiar_friends.cooldown",
                time, Text.translatable("cooldown.familiar_friends." + cooldownUnit).getString()), false);
    }

    /**
     *
     * @return get the current companion cooldown
     */
    public int getCooldown() {
        return this.cooldown;
    }

    /**
     * set the companion cooldown (in ticks)
     * @param cooldown the cooldown in ticks
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * set the companion cooldown (in seconds)
     * @param cooldown the cooldown in seconds
     */
    public void setCooldownSeconds(int cooldown) {
        this.cooldown = cooldown * 20;
    }

    /**
     * set the companion cooldown (in minutes)
     * @param cooldown the cooldown in minutes
     */
    public void setCooldownMinutes(int cooldown) {
        this.cooldown = cooldown * 20 * 60;
    }

    // Most of the stuff past here shouldn't be modified, it makes the companion follow the player
    // and handles translation file stuff

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

        if (this.cooldown > 0) {
            this.cooldown--;
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

    public Text getDescription() {
        return Text.translatable(this.getType().getTranslationKey() + ".description",
                Text.translatable(FamiliarFriends.Current_Keybinding_Key).getString());
    }

    public Text getAuthor() {
        return Text.translatable("gui.familiar_friends.author").append(
                Text.translatable(this.getType().getTranslationKey() + ".author"));
    }

    public Text getCostLang(int cost) {
        return Text.translatable("gui.familiar_friends.cost", cost).withColor(0x478e47);
    }

    public boolean isBookRender() {
        return this.bookRender;
    }

    public boolean isLocked(PlayerEntity player) {
        return !((CompanionPlayerData) player).isCompanionUnlocked(this);
    }

    public void syncClientData(int cost, boolean enabledClient) {
        this.costClient = cost;
        this.enabledClient = enabledClient;
    }

    public boolean getEnabledClient() {
        return this.enabledClient;
    }

    public int getCostClient() {
        return this.costClient;
    }

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
