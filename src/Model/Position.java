package Model;

public class Position {
    public double x,y;

    public Position (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position add (Position other) {
        return new Position(this.x + other.x, this.y + other.y);
    }

}
