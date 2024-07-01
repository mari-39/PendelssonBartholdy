package src;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DistanceConstraint extends Constraints {
    public static final float fixedDist = 25F;
    public Particle p1, p2; // abstract classes have no fields in that sense
    public ArrayList<Particle> particles;
    public Vector dist_vec = Vector.vector_sub(p1.posn, p2.posn); // will need to make this into ArrayLists too
    public float currDist = Vector.dist_betr(p1.posn, p2.posn); // same here

    public DistanceConstraint(ArrayList<Particle> p) {
        this.particles = p;
    }
    @Override
    public ArrayList<Vector> solve() {
        ArrayList<Vector> arr = new ArrayList<Vector>();
        if (currDist == fixedDist) {
            arr.add(p1.posn);
            arr.add(p2.posn);
            return arr;
        } else {
                float offset = Math.abs(currDist - fixedDist);
                for (int j = 0; j < particles.size(); j++) {
                    Particle p = particles.get(j);
                    p.posn = Vector.vector_add(p2.posn, Vector.skalar_mult(Vector.norm(dist_vec), offset));
                    p.oldPosn = p.posn; // ???
                    arr.add(p.posn);
                }
                return arr;
        }
    }
}
