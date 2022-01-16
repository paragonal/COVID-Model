package Model;

public class Position {
    public double x,y;

    public Position (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position clone () {
        return new Position(x, y);
    }

    public Position add (Position other) {
        return new Position(this.x + other.x, this.y + other.y);
    }

    public Position scale (double factor) {
        return new Position(this.x * factor, this.y * factor);
    }

    public double mag() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Position unit() {
        return this.clone().scale(1 / this.mag());
    }
}
