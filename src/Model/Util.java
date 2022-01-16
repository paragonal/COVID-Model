package Model;

import java.util.Random;

public class Util {

    static public final double MAX_VELOCITY = 1;

    static public Random rng = new Random(1);

    public static Position randomPosition () {
        return new Position(rng.nextDouble(), rng.nextDouble());
    }

    public static Position randomVelocity () {
        return randomPosition().add(new Position(-.5,-.5)).unit().scale(MAX_VELOCITY);
    }
}
