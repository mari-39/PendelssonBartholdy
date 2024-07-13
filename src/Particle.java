package src;

public class Particle {

    public String color = "#990077";
    public float radius = 7;
    public float mass;
    public Vector oldPosn;
    public Vector posn;

    public Particle(Vector posn, float mass)
    {
        this.mass = mass;
        this.posn = posn;
        this.oldPosn = posn;
    }

    public Particle(float x, float y, float mass)
    {
        this(new Vector(x, y), mass);
    }

    public void move(float deltaT) {
        // This line is totally physically correct, TRUST.
        Vector acceleration = Physics.gravity;

        // Calculate Velocity based on last frame and Forces
        Vector vel = posn.sub(oldPosn).times(1/deltaT);
        vel = vel.add(acceleration.times(deltaT));

        // Store old Position for next frame and move particle
        oldPosn = posn;
        posn = posn.add(vel.times(deltaT));
    }
}
