package src;

import javax.swing.*;

public class BoxConstraint extends Constraint {

    private final Particle particle;
    private final Vector corner1;
    private final Vector corner2;

    public BoxConstraint(Particle particle, Vector corner1, Vector corner2) {
        this.particle = particle;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public BoxConstraint(Particle particle, float x1, float y1, float x2, float y2)
    {
        this(particle, new Vector(x1, y1), new Vector(x2, y2));
    }

    @Override
    public void solve() {
        float minX = Math.min(corner1.x, corner2.x);
        float maxX = Math.max(corner1.x, corner2.x);
        float minY = Math.min(corner1.y, corner2.y);
        float maxY = Math.max(corner1.y, corner2.y);

        Vector pos = particle.posn;

        float xError = Math.min(pos.x-minX, 0) + Math.max(pos.x-maxX, 0);
        float yError = Math.min(pos.y-minY, 0) + Math.max(pos.y-maxY, 0);
        particle.posn = particle.posn.add(new Vector(-xError, -yError));
    }

}
