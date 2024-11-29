package net.deadlydiamond98.familiar_friends.entities.companions.vanilla;

import net.deadlydiamond98.familiar_friends.entities.CompanionEntities;
import net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.PlayerCompanion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class IronGolemCompanion extends PlayerCompanion {

    private static final TrackedData<Integer> ATTACK_TICKS_LEFT;

    public IronGolemCompanion(EntityType<?> type, World world) {
        super(type, world);
    }

    public IronGolemCompanion(World world, PlayerEntity owner, boolean gui) {
        super(CompanionEntities.Iron_Golem_Companion, world, owner, gui);
    }

    @Override
    protected void doPassiveAction(PlayerEntity player, LivingEntity nearestHostile) {

        if (this.getAttackTicksLeft() > 0) {
            this.setAttackTicksLeft(this.getAttackTicksLeft() - 1);
        }

        if (nearestHostile != null) {
            if (this.age % 120 == 0) {
                this.setAttackTicksLeft(10);
                nearestHostile.damage(nearestHostile.getDamageSources().playerAttack(player), 4.0f);
                nearestHostile.setVelocity(nearestHostile.getVelocity().multiply(1, 0, 1).add(0, 1, 0));
                this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void doKeyEvent(PlayerEntity player) {

    }

    @Override
    public void onAttack(PlayerEntity player, LivingEntity target) {

    }

    @Override
    public Text getName() {
        return Text.translatable("entity.minecraft.iron_golem");
    }

    @Override
    public int getCost() {
        return 12;
    }

    public int getAttackTicksLeft() {
        return this.dataTracker.get(ATTACK_TICKS_LEFT);
    }

    public void setAttackTicksLeft(int ticks) {
        this.dataTracker.set(ATTACK_TICKS_LEFT, ticks);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ATTACK_TICKS_LEFT, 0);
    }

    static {
        ATTACK_TICKS_LEFT = DataTracker.registerData(IronGolemCompanion.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
