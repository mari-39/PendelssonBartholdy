package src;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Physics {
    public static final Vector gravity = new Vector(0, 9.8066F);
    public static final float deltaT = 0.1F;
    static ArrayList<Particle> particles = new ArrayList<>();
    static ArrayList<Constraint> constraints = new ArrayList<>();


    public static void initParticles()
    {
        Particle particle1 = new Particle(300, 300, 900000f);
        Particle particle2 = new Particle(300.01f, 250, 1);
        Particle particle3 = new Particle(300, 200, 1);

        particles.add(particle1);
        particles.add(particle2);
        particles.add(particle3);

        DistanceConstraint constraint12 =
                new DistanceConstraint(particle1, particle2, particle1.posn.dist(particle2.posn));
        DistanceConstraint constraint23 =
                new DistanceConstraint(particle2, particle3, particle2.posn.dist(particle3.posn));

        Particle part4 = new Particle(300, 300, 1);
        particles.add(part4);
        BoxConstraint boxConstraint = new BoxConstraint(part4, 200, 400, 400, 200);
        CollisionConstraint collisionConstraint = new CollisionConstraint(particle3, part4);

        constraints.add(constraint12);
        constraints.add(constraint23);
        constraints.add(boxConstraint);
        constraints.add(collisionConstraint);
    }

    public static ArrayList<Vector> method() {
        // Move all particles one step
        for (Particle particle : particles) {
            particle.move(deltaT);
        }

        // Solve all constraints multiple times (converges to optimal solution)
        for (int i = 0; i < 20; i++) {
            for (Constraint constraint : constraints) {
                constraint.solve();
            }
        }

        // Map List of Particles to list of Positions of those particles and return
        return particles.stream().map((particle) -> particle.posn).collect(Collectors.toCollection(ArrayList::new));
    }
}