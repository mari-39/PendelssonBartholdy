package src;

public class CollisionConstraint extends Constraint{

    private final Particle p1, p2;

    public CollisionConstraint(Particle particle1, Particle particle2)
    {
        this.p1 = particle1;
        this.p2 = particle2;
    }

    @Override
    public void solve() {
        float distance = p1.posn.dist(p2.posn);
        float minDist = p1.radius+p2.radius;
        float error = Math.max(minDist-distance, 0);

        float totalMass = p1.mass+p2.mass;

        Vector direction = p2.posn.sub(p1.posn).normalized();
        p1.posn = p1.posn.add(direction.times(-error * p2.mass/totalMass));
        p2.posn = p2.posn.add(direction.times(error * p1.mass/totalMass));
    }

}

