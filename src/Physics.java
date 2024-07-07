package src;

import java.util.ArrayList;

public class Physics {
    public static final Vector gravity = new Vector(0, 9.8066F);
    public static final float deltaT = 0.1F;
    static ArrayList<Particle> p = new ArrayList<>();
    static ArrayList<Constraints> c = new ArrayList<>();


    public static void initParticles()
    {
        Particle particle1 = new Particle();
        Particle particle2 = new Particle();
        Particle particle3 = new Particle();

        particle1.mass = 900000f;
        particle2.mass = 1f;
        particle3.mass = 1f;

        particle1.posn = new Vector(300, 300);
        particle2.posn = new Vector(350, 300);
        particle3.posn = new Vector(400, 300);

        particle1.oldPosn = particle1.posn;
        particle2.oldPosn = particle2.posn;
        particle3.oldPosn = particle3.posn;

        p.add(particle1);
        p.add(particle2);
        p.add(particle3);

        DistanceConstraint constraint12 =
                new DistanceConstraint(particle1, particle2, Vector.dist_betr(particle1.posn, particle2.posn));
        DistanceConstraint constraint23 =
                new DistanceConstraint(particle2, particle3, Vector.dist_betr(particle2.posn, particle3.posn));

        c.add(constraint12);
        c.add(constraint23);
    }

    public static ArrayList<Vector> method() {
        for (int j = 0; j < p.size(); j++) {
            Particle temp = p.get(j);
            temp.move(deltaT);
        }
        // NO, use the result of move in solve !
        // do I need this ?

        ArrayList<Vector> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int l = 0; l < c.size(); l++) {
                c.get(l).solve();
            }
        }
        result.add(p.get(0).posn);
        result.add(p.get(1).posn);
        result.add(p.get(2).posn);
        return result;
    }
}
    // move - Method should be called here
    // solve method should be called after move (several times)





