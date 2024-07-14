package src;

import javafx.event.ActionEvent;

import java.util.ArrayList;

public class Physics {
    public static final Vector gravity = new Vector(0, 9.8066F);

    public static final float defaultDeltaTime = 0.003f;
    public static float deltaT = 0.003F;

    static ArrayList<Particle> particles = new ArrayList<>();
    static ArrayList<Constraint> constraints = new ArrayList<>();

    public static void setup1(ActionEvent event)
    {
        particles.clear();
        constraints.clear();

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
        RubberbandConstraint fixParticle1 = new RubberbandConstraint(particle1, particle1.posn, 1);

        constraints.add(fixParticle1);
        constraints.add(constraint12);
        constraints.add(constraint23);
    }

    public static void setup2(ActionEvent event)
    {
        particles.clear();
        constraints.clear();

        Vector corner1 = new Vector(20, 580);
        Vector corner2 = new Vector(580, 20);

        int gridWidth = 5;
        int gridHeight = 5;
        int cellSize = 30;
        for (int xInd = 0; xInd < gridWidth; xInd++)
        {
            for (int yInd = 0; yInd < gridHeight; yInd++)
            {
                int x = 300 - gridWidth/2 * cellSize + xInd*cellSize;
                int y = 300 - gridHeight/2 * cellSize + yInd*cellSize;
                Particle particle = new Particle(x, y, 1);
                particles.add(particle);
            }
        }

        // Add boxconstraints
        for (Particle particle: particles)
        {
            BoxConstraint boxConstraint = new BoxConstraint(particle, corner1, corner2);
            constraints.add(boxConstraint);
        }

        for (Particle particle: particles)
        {
            for (Particle otherParticle: particles)
            {
                if (particle == otherParticle)
                    continue;
                CollisionConstraint collisionConstraint = new CollisionConstraint(particle, otherParticle);
                constraints.add(collisionConstraint);
            }
        }
    }

    //This code ist disgusting and we know that...
    public static void setup5(ActionEvent event)
    {
        particles.clear();
        constraints.clear();

        Vector corner1 = new Vector(20, 580);
        Vector corner2 = new Vector(580, 20);

        int gridWidth = 10;
        int gridHeight = 10;
        int cellSize = 30;
        for (int xInd = 0; xInd < gridWidth; xInd++)
        {
            for (int yInd = 0; yInd < gridHeight; yInd++)
            {
                int x = 300 - gridWidth/2 * cellSize + xInd*cellSize;
                int y = 300 - gridHeight/2 * cellSize + yInd*cellSize;
                Particle particle = new Particle(x, y, 1);
                particles.add(particle);
            }
        }

        // Add boxconstraints
        for (Particle particle: particles)
        {
            BoxConstraint boxConstraint = new BoxConstraint(particle, corner1, corner2);
            constraints.add(boxConstraint);
        }

        for (Particle particle: particles)
        {
            for (Particle otherParticle: particles)
            {
                if (particle == otherParticle)
                    continue;
                CollisionConstraint collisionConstraint = new CollisionConstraint(particle, otherParticle);
                constraints.add(collisionConstraint);
            }
        }
    }

    public static void setup3(ActionEvent event)
    {
        particles.clear();
        constraints.clear();

        Particle center = new Particle(300, 300, 1);
        Particle other = new Particle(450, 300, 1);
        RubberbandConstraint fixCenter = new RubberbandConstraint(center, center.posn, 1);
        AngleConstraint angleConstraint = new AngleConstraint(center, other, 0, 0.00001f);
        DistanceConstraint fixedDist = new DistanceConstraint(center, other, center.posn.dist(other.posn));

        center.radius = 15f;
        center.color = "#8822AA";

        particles.add(center);
        particles.add(other);

        constraints.add(fixCenter);
        constraints.add(angleConstraint);
        constraints.add(fixedDist);
    }

    public static void setup4(ActionEvent event)
    {
        particles.clear();
        constraints.clear();

        float radius = 150;
        int partCount = 30;

        for (int i = 0; i < partCount; i++)
        {
            float angle = (float) (Math.PI - i*Math.PI/partCount);
            Vector pos = new Vector((float) (300 + Math.cos(angle)*radius), (float) (300 + Math.sin(angle)*radius));
            Particle particle = new Particle(pos, 1);

            if (i == 0 || i == partCount-1)
            {
                RubberbandConstraint fixPoint = new RubberbandConstraint(particle, particle.posn, 1);
                constraints.add(fixPoint);
            }
            particles.add(particle);
        }

        for (int i = 1; i < partCount; i++)
        {
            Particle p1 = particles.get(i-1);
            Particle p2 = particles.get(i);
            DistanceConstraint dist = new DistanceConstraint(p1, p2, p1.posn.dist(p2.posn));
            dist.rope = true;
            constraints.add(dist);
        }
    }

    public static void method() {
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
    }

}