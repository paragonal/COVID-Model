package Model;

import java.util.Random;

public class Util {

    static public Random rng = new Random(1);

    public static Position randomPosition () {
        return new Position(rng.nextDouble(), rng.nextDouble());
    }

}
