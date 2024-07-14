package src;

public class AngleConstraint extends Constraint {

    public Particle particle1;
    public Particle particle2;

    float angle;
    float stiffness;

    public AngleConstraint(Particle particle1, Particle particle2, float angle, float stiffness)
    {
        this.particle1 = particle1;
        this.particle2 = particle2;
        this.angle = angle;
        this.stiffness = stiffness;

        System.out.println(360*getAngle(new Vector(1, 0.5f))/(2*3.141592f));
        System.out.println(360*getAngle(new Vector(-0.5f, 1))/(2*3.141592f));
    }

    @Override
    public void solve() {
        Vector dir = particle2.posn.sub(particle1.posn);
        float correctionAngle = (angle - getAngle(dir)) * stiffness;
        float totalMass = particle1.mass + particle2.mass;
        float relMult = particle2.mass/totalMass;

        Vector rotation = new Vector((float) Math.cos(correctionAngle), (float) Math.sin(correctionAngle));

        Vector middle = particle1.posn.add(dir.times(relMult));
        Vector deltaTo1 = particle1.posn.sub(middle);
        Vector deltaTo2 = particle2.posn.sub(middle);

        deltaTo1 = deltaTo1.complexTimes(rotation);
        deltaTo2 = deltaTo2.complexTimes(rotation);

        particle1.posn = middle.add(deltaTo1);
        particle2.posn = middle.add(deltaTo2);
    }

    private float getAngle(Vector direction)
    {
        float rawAngle = (float) Math.asin(direction.y/direction.abs());
        float x = direction.x;
        float y = direction.y;
        if (x > 0 && y > 0)
            return rawAngle;
        if (x < 0 && y > 0)
            return (float)Math.PI - rawAngle;
        if (x < 0 && y < 0)
            return (float)Math.PI + rawAngle;
        else
            return 2*(float)Math.PI - rawAngle;
    }

}
