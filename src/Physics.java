package src;

import java.util.ArrayList;

public class Physics {
    public static final Vector gravity = new Vector(0, 9.8066F);
    public static final float deltaT = 0.1F;
    static ArrayList<Particle> p = new ArrayList<>();
    static ArrayList<Constraints> c = new ArrayList<>();

    public static void makep1() {
        Particle p1 = new Particle();
        p1.mass = 90000F;
        p1.posn = new Vector(300, 70);
        p1.oldPosn = p1.posn;
        p.add(p1);
    }

    public static void makep2() {
        Particle p2 = new Particle();
        p2.mass = 1F;
        p2.posn = new Vector(325, 70);
        p2.oldPosn = p2.posn;
        DistanceConstraint dc = new DistanceConstraint(p.get(0), p2,
                Vector.dist_betr(p.get(0).posn, p2.posn));
        c.add(dc);
        p.add(p2);
    }

    public static void makep3() {
        Particle p3 = new Particle();
        p3.mass = 1F;
        p3.posn = new Vector(321, 120);
        p3.oldPosn = p3.posn;
        DistanceConstraint dc2 = new DistanceConstraint(p.get(0), p3,
                Vector.dist_betr(p.get(0).posn, p3.posn));
        c.add(dc2);
        p.add(p3);
    }


    public static ArrayList<Vector> method() {
        for (int j = 0; j < p.size(); j++) {
            Particle temp = p.get(j);
            temp.move(deltaT);
        }
        // NO, use the result of move in solve !
        // do I need this ?

        ArrayList<Vector> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
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





