package Model;

import java.util.Arrays;
import java.util.List;


/**
 * Cities are where the basic simulation takes place, each city has many residents
 * and many neighborhoods where people reside and move around.
 */
class City {

    private Neighborhood[][] neighborhoods;
    private String cityName;

    int width, height;

    public City(String cityName, int width, int height, int population) {
        this.cityName = cityName;
        this.neighborhoods = new Neighborhood[width][height];
        this.width = width;
        this.height = height;

        int populationDensity = population / (width * height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.neighborhoods[i][j] = new Neighborhood(populationDensity);
            }
        }
    }

    /**
     * Update neighborhoods then move individuals from one neighborhood to another, this may result in double updating
     * @param dt
     */
    public int update(Virus virus, double dt) {
        int totalInfected = 0;
        for (int i = 0; i < neighborhoods.length; i++) {
            for (int j = 0; j < neighborhoods[0].length; j++) {
                totalInfected += neighborhoods[i][j].updateInfections(virus, dt);
            }
        }
        // Update positions and move people around.
        for (int j = 0; j < neighborhoods.length; j++) {
            for (int i = 0; i < neighborhoods[0].length; i++) {
                Person[] transients = neighborhoods[i][j].updatePositions(dt);
                for (Person p : transients) {
                    // Get needed change in neighborhood since we're garunteed these people have to move
                    int di = (int) Math.floor(p.position.x / neighborhoods[i][j].width);
                    int dj = (int) Math.floor(p.position.y / neighborhoods[i][j].height);
                    if (i+di < 0 || i + di > width - 1)
                        di = 0;
                    if (j+dj < 0 || j + dj > height - 1)
                        dj = 0;

                    // Math to make people move neighborhoods and gain random orthogonal component of velocity
                    Neighborhood destination = neighborhoods[i + di][j + dj];
                    destination.residents.add(p);
                    if (di > 0) {
                        p.position.x = 0;
                        p.velocity.y = Util.rng.nextDouble()-.5;
                    }
                    if (di < 0) {
                        p.position.x = destination.width;
                        p.velocity.y = Util.rng.nextDouble()-.5;
                    }
                    if (dj > 0) {
                        p.position.y = 0;
                        p.velocity.x = Util.rng.nextDouble()-.5;
                    }
                    if (dj < 0) {
                        p.position.y = destination.height;
                        p.velocity.x = Util.rng.nextDouble()-.5;
                    }
                    p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);

                    if (di == 0 && dj == 0) {
                        if (p.position.x < 0) {
                            p.position.x = 0;
                            p.velocity.x *= -1;
                        }
                        if (p.position.x > destination.width) {
                            p.position.x = destination.width;
                            p.velocity.x *= -1;
                        }
                        if (p.position.y < 0) {
                            p.position.y = 0;
                            p.velocity.y *= -1;
                        }
                        if (p.position.y > destination.height) {
                            p.position.y = destination.height;
                            p.velocity.y *= -1;
                        }
                    }




//                    if (p.velocity.x < 0) {
//                        p.position.x = destination.width;
//                        p.velocity.y = Util.rng.nextDouble()-.5;
//                        if (di == 0) {
//                            p.position.x = 0;
//                            p.velocity.x *= -1;
//                        }
//                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
//                    } else if (p.velocity.x > 0) {
//                        p.position.x = 0;
//                        p.velocity.y = Util.rng.nextDouble()-.5;
//                        if (di == 0) {
//                            p.position.x = destination.height;
//                            p.velocity.x *= -1;
//                        }
//                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
//                    } else if (p.velocity.y < 0) {
//                        p.position.y = destination.height;
//                        p.velocity.x = Util.rng.nextDouble()-.5;
//                        if (dj == 0) {
//                            p.position.y = 0;
//                            p.velocity.y *= -1;
//                        }
//                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
//                    } else if (p.velocity.y > 0) {
//                        p.position.y = 0;
//                        p.velocity.x = Util.rng.nextDouble()-.5;
//                        if (dj == 0) {
//                            p.position.y = destination.width;
//                            p.velocity.y *= -1;
//                        }
//                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
//                    }
                }
            }
        }
        return totalInfected;
    }

    public void initializeSickness (int i, int j, int numToInfect, Virus virus) {
        for (int l = 0; l < Math.min(neighborhoods[i][j].residents.size(), numToInfect); l++) {
            neighborhoods[i][j].residents.get(l).makeSick(virus);
        }
    }

    public Neighborhood getNeighborhood(int i, int  j) {
        return neighborhoods[i][j];
    }

    public String getCityName() {
        return this.cityName;
    }
}