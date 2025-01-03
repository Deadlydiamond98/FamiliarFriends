package net.deadlydiamond98.familiar_friends.entities.abstractcompanionclasses.behaviors;

import net.deadlydiamond98.familiar_friends.entities.PlayerCompanion;

import java.util.Random;

public class LookAroundBehavior implements LookBehavior {
    private final PlayerCompanion companion;
    private final Random random;
    private int lookAroundTime;
    private double deltaX;
    private double deltaZ;

    public LookAroundBehavior(PlayerCompanion companion) {
        this.companion = companion;
        this.random = new Random();
    }

    @Override
    public void start() {
        double d = 2 * Math.PI * this.random.nextDouble();
        this.deltaX = Math.cos(d);
        this.deltaZ = Math.sin(d);
        this.lookAroundTime = 20 + this.random.nextInt(20);
    }

    @Override
    public void tick() {
        if (this.lookAroundTime > 0) {
            --this.lookAroundTime;
            this.companion.getLookControl().lookAt(
                    this.companion.getX() + this.deltaX,
                    this.companion.getY(),
                    this.companion.getZ() + this.deltaZ
            );
        }
    }

    @Override
    public boolean isFinished() {
        return this.lookAroundTime <= 0;
    }
}
