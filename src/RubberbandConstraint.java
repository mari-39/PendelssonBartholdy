package src;

public class RubberbandConstraint extends Constraint {

    private final Particle particle;
    private final Vector position;

    private final float stiffness;

    public RubberbandConstraint(Particle particle, Vector position, float stiffness)
    {
        this.particle = particle;
        this.position = position;
        this.stiffness = stiffness;
    }

    @Override
    public void solve() {
        particle.posn = particle.posn.add(position.sub(particle.posn).times(stiffness));
        particle.oldPosn = particle.posn;
    }

}
