package Model;

import java.util.Arrays;
import java.util.List;


/**
 * Cities are where the basic simulation takes place, each city has many residents
 * and many neighborhoods where people reside and move around.
 */
class City {

    private List<Neighborhood> neighborhoods;
    private String cityName;

    public City(String cityName, Neighborhood[] neighborhoods) {
        this.cityName = cityName;
        this.neighborhoods = Arrays.asList(neighborhoods);
    }

    public String getCityName() {
        return this.cityName;
    }
}