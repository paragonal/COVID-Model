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
        for (int i = 0; i < neighborhoods.length; i++) {
            for (int j = 0; j < neighborhoods[0].length; j++) {
                Person[] transients = neighborhoods[i][j].updatePositions(dt);
                for (Person p : transients) {
                    // Get needed change in neighborhood since we're garunteed these people have to move
                    int di = (int) Math.floor(p.position.y / neighborhoods[i][j].height);
                    int dj = (int) Math.floor(p.position.x / neighborhoods[i][j].width);
                    if (i+di < 0 || i + di > height - 1)
                        di = 0;
                    if (j+dj < 0 || j + dj > width - 1)
                        dj = 0;

                    // Math to make people move neighborhoods and gain random orthogonal component of velocity
                    Neighborhood destination = neighborhoods[i + di][j + dj];
                    destination.residents.add(p);
                    if (p.velocity.y < 0) {
                        p.position.y = destination.height;
                        p.velocity.x = Util.rng.nextDouble()-.5;
                        if (di == 0) {
                            p.position.y = 0;
                            p.velocity.y *= -1;
                        }
                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
                    } else if (p.velocity.y > 0) {
                        p.position.y = 0;
                        p.velocity.x = Util.rng.nextDouble()-.5;
                        if (di == 0) {
                            p.position.y = destination.height;
                            p.velocity.y *= -1;
                        }
                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
                    }

                    if (p.velocity.x < 0) {
                        p.position.x = destination.width;
                        p.velocity.y = Util.rng.nextDouble()-.5;
                        if (dj == 0) {
                            p.position.x = 0;
                            p.velocity.x *= -1;
                        }
                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
                    } else if (p.velocity.x > 0) {
                        p.position.x = 0;
                        p.velocity.y = Util.rng.nextDouble()-.5;
                        if (dj == 0) {
                            p.position.x = destination.width;
                            p.velocity.x *= -1;
                        }
                        p.velocity = p.velocity.unit().scale(Util.MAX_VELOCITY);
                    }
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

    public String getCityName() {
        return this.cityName;
    }
}