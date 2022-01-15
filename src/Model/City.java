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

    public City(String cityName, int width, int height, int population) {
        this.cityName = cityName;
        this.neighborhoods = new Neighborhood[width][height];
        int populationDensity = population / (width * height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.neighborhoods[i][j] = new Neighborhood(populationDensity);
            }
        }

    }

    public String getCityName() {
        return this.cityName;
    }
}