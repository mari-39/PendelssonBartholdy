package src;


public class Vector {
    float x, y;

public Vector(float x, float y) {
    this.x = x;
    this.y = y;
}

public static Vector vector_add(Vector a, Vector b) {
    Vector result = new Vector(a.x + b.x, a.y + b.y);
    return result;
}

public static Vector vector_sub(Vector a, Vector b) {
    Vector result = new Vector(a.x - b.x, a.y - b.y);
        return result;
    }

public static Vector vector_mult(Vector a, Vector b) {
    Vector result = new Vector(a.x * b.x, a.y * b.y);
    return result;
}

public float punktprod(Vector a, Vector b) {
    float res = vector_mult(a, b).x + vector_mult(a, b).y;
return res;
}

public static float betr(Vector a) {
    float res = (float) Math.sqrt(Math.pow(a.x, 2) + Math.pow(a.y, 2));
    return res;
}

public static float dist_betr(Vector a, Vector b) {
    Vector diff = vector_sub(a, b);
    float res = betr(diff);
    return res;
}

public static Vector skalar_mult(Vector a, float b) {
    Vector res = new Vector(a.x * b, a.y * b);
    return res;
}

public static Vector norm(Vector a) {
    Vector res = skalar_mult(a, 1 / betr(a));
    return res;
}
}
