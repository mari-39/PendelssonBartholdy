package src;


public class Vector {
    float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector sub(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public Vector compWiseTimes(Vector other) {
        return new Vector(this.x * other.x, this.y * other.y);
    }

    public float abs() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public float dist(Vector other) {
        Vector diff = this.sub(other);
        return diff.abs();
    }

    public Vector times(float scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector normalized() {
        return this.times(1/this.abs());
    }
}
