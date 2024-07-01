package src;

public class Particle {
    public float mass;
    public Vector oldPosn;
    public Vector posn;
    public Vector vel;

    public Vector move(float deltaT) {
        Vector a = Vector.skalar_mult(Physics.gravity, 1/deltaT);;
        vel = Vector.skalar_mult(Vector.vector_sub(posn, oldPosn), 1/deltaT);
        vel = Vector.vector_add(vel, Vector.skalar_mult(a, deltaT));
    }
}
