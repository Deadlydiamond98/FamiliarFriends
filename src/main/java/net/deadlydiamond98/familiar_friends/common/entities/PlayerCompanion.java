package net.deadlydiamond98.familiar_friends.common.entities;

import net.deadlydiamond98.familiar_friends.common.entities.base.MockLivingEntity;
import net.deadlydiamond98.familiar_friends.init.CompanionSounds;
import net.deadlydiamond98.familiar_friends.util.mixinterfaces.CompanionPlayerData;
import net.deadlydiamond98.familiar_friends.util.TimeUnitHelper;
import net.deadlydiamond98.koalalib.init.KoalaLibSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

public abstract class PlayerCompanion extends MockLivingEntity {
    private int cooldown;
    private int costClient;
    private boolean enabledClient;
    protected boolean bookRender;

    public PlayerCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public PlayerCompanion(EntityType<?> type, World world, PlayerEntity owner, boolean gui) {
        this(type, world);

        this.setOwner(owner);
        this.setPosition(owner.getPos());

        this.costClient = 0;
        this.enabledClient = false;

        this.cooldown = 0;
        this.noClip = true;
        this.bookRender = gui;
    }

    /**
     * Anything here will be run every tick
     * @param player The player the companion belongs to
     * @param nearestHostile The nearest hostile mob
     */
    public void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {}

    /**
     * Anything here will be run whenever the player presses the action key
     * @param player The player the companion belongs to
     */
    public void doKeyEvent(PlayerEntity player) {}

    /**
     * Anything here will be run whenever the player attacks a target
     *
     * @param player The player the companion belongs to
     * @param target The target that is being attacked
     * @param amount The damage delt to attackers
     */
    public void onAttack(PlayerEntity player, LivingEntity target, float amount) {}

    /**
     * Anything here will be run whenever right before the player is about to die
     * @param player The player the companion belongs to
     */
    public void onPlayerDeath(PlayerEntity player) {}

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
     * Returns a Fluid Tag that specifies fluids that can be walked on, or null
     */
    public @Nullable TagKey<Fluid> walkableFluids() {
        return null;
    }

    public boolean canWalkOnPowderSnow() {
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
        List<Entity> nearbyEntities = player.getWorld().getEntitiesByClass(Entity.class, player.getBoundingBox().expand(10), Entity::isAlive);
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
            player.getWorld().playSound(null, player.getBlockPos(), KoalaLibSounds.CONSOLE_CRAFT_FAIL, SoundCategory.PLAYERS, 0.5f, 1.0f);
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

    @Override
    public void tick() {
        super.tick();
        if (this.cooldown > 0) {
            this.cooldown--;
        }
    }

    public Text getDescription(String keybinding) {
        return Text.translatable(this.getType().getTranslationKey() + ".description", keybinding);
    }

    public Text getAuthor() {
        return Text.translatable("gui.familiar_friends.author").append(Text.translatable(this.getType().getTranslationKey() + ".author"));
    }

    public Text getCostLang(int cost) {
        return Text.translatable("gui.familiar_friends.cost", cost).setStyle(Style.EMPTY.withColor(0x478e47));
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
}
