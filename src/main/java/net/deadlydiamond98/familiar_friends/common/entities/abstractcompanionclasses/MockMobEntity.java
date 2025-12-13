package net.deadlydiamond98.familiar_friends.common.entities.abstractcompanionclasses;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class MockMobEntity extends MockLivingEntity {

    protected NonLivingLookControl lookControl;

    public MockMobEntity(EntityType<?> type, World world) {
        super(type, world);
        this.lookControl = new NonLivingLookControl(this);
    }

    public NonLivingLookControl getLookControl() {
        return this.lookControl;
    }

    @Override
    public void tick() {
        super.tick();
        this.lookControl.tick();
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
