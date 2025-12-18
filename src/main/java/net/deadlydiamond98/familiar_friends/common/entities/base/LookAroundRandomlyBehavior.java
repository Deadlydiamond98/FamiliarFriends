package net.deadlydiamond98.familiar_friends.common.entities.base;

import java.util.Random;

public class LookAroundRandomlyBehavior {
    private final MockLivingEntity companion;
    private final Random random;
    private int lookAroundTime;
    private double deltaX;
    private double deltaZ;

    public LookAroundRandomlyBehavior(MockLivingEntity companion) {
        this.companion = companion;
        this.random = new Random();
    }

    public void start() {
        double d = 2 * Math.PI * this.random.nextDouble();
        this.deltaX = Math.cos(d);
        this.deltaZ = Math.sin(d);
        this.lookAroundTime = 20 + this.random.nextInt(20);
    }

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

    public boolean isFinished() {
        return this.lookAroundTime <= 0;
    }
}
