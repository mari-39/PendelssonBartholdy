package src;

import java.util.ArrayList;

public class Physics {
    public static final Vector gravity = new Vector(0, 9.8066F);
    public ArrayList<Particle> p;

    public ArrayList<Vector> method() {
        for (int j = 0; j < p.size(); j++) {
            Particle temp = p.get(j);
            temp.move(float deltaT);
        }
        // NO, use the result of move in solve !
        // do I need this ?
        DistanceConstraint dc = new DistanceConstraint(p);
        ArrayList<Vector> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
             result = dc.solve();
        }
        return result;
    }
}
    // move - Method should be called here
    // solve method should be called after move (several times)





