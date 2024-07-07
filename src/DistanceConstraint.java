package src;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DistanceConstraint extends Constraints {
    public float fixedDist;
    public Particle p1, p2; // abstract classes have no fields in that sense
//    public ArrayList<Particle> particles;
//    public ArrayList<Vector> dist_vecs = new ArrayList<>();
     // same here


    public DistanceConstraint(Particle p1, Particle p2, float fixedDist) {
        this.p1 = p1;
        this.p2 = p2;
        this.fixedDist = fixedDist;
    }

    @Override
    public void solve() {
        Vector dist_vec = Vector.vector_sub(p1.posn, p2.posn);
        float currDist = Vector.dist_betr(p1.posn, p2.posn);
        float mass_comp = p1.mass + p2.mass;
        if (currDist == fixedDist) {
            return;
        } else {
                float offset = Math.abs(currDist - fixedDist);
                Vector v = Vector.skalar_mult(Vector.norm(dist_vec), - offset * (p2.mass / mass_comp));
                System.out.println(v.x + " : " + v.y);
                System.out.println(offset);
                p1.posn = Vector.vector_add(p1.posn,
                        Vector.skalar_mult(Vector.norm(dist_vec), - offset * (p2.mass / mass_comp)));
                p2.posn = Vector.vector_add(p2.posn,
                        Vector.skalar_mult(Vector.norm(dist_vec), offset * p1.mass / mass_comp));
        }
    }
}
