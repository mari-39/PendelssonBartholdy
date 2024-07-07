package src;

import java.util.ArrayList;

public class Physics {
    public static final Vector gravity = new Vector(0, 9.8066F);
    public static final float deltaT = 0.1F;
    static ArrayList<Particle> p = new ArrayList<>();

    public static void makep1() {
        Particle p1 = new Particle();
        p1.mass = Float.POSITIVE_INFINITY;
        p1.posn = new Vector(70, 70);
        p1.oldPosn = p1.posn;
        p.add(p1);
    }

    public static void makep2() {
        Particle p2 = new Particle();
        p2.mass = 1F;
        p2.posn = new Vector(70, 95);
        p2.oldPosn = p2.posn;
        p.add(p2);
    }

    public static void makep3() {
        Particle p3 = new Particle();
        p3.mass = 1F;
        p3.posn = new Vector(71, 120);
        p3.oldPosn = p3.posn;
        p.add(p3);
    }


    public static ArrayList<Vector> method() {
        for (int j = 0; j < 3; j++) {
            Particle temp = p.get(j);
            temp.move(deltaT);
        }
        // NO, use the result of move in solve !
        // do I need this ?
        DistanceConstraint dc = new DistanceConstraint(p.get(0), p.get(1));
        DistanceConstraint dc2 = new DistanceConstraint(p.get(1), p.get(2));
        ArrayList<Vector> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
             dc.solve();
             dc2.solve();
        }
        result.add(p.get(0).posn);
        result.add(p.get(1).posn);
        result.add(p.get(2).posn);
        System.out.println(p.get(2).posn.x);
        return result;
    }
}
    // move - Method should be called here
    // solve method should be called after move (several times)





