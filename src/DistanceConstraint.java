package src;

public class DistanceConstraint extends Constraint {
    private final float fixedDist;

    final Particle p1;
    final Particle p2;

    public DistanceConstraint(Particle p1, Particle p2, float fixedDist) {
        this.p1 = p1;
        this.p2 = p2;
        this.fixedDist = fixedDist;
    }

    // Offset particles of constraint relative to their mass so that the constraint is resolved.
    @Override
    public void solve() {
        Vector direction = p1.posn.sub(p2.posn).normalized();
        float currDist = p1.posn.dist(p2.posn);
        float totalMass = p1.mass + p2.mass;

        float error = currDist - this.fixedDist;
        p1.posn = p1.posn.add(direction.times(- error * (p2.mass / totalMass)));
        p2.posn = p2.posn.add(direction.times(error * (p1.mass / totalMass)));
    }
}
