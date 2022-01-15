package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Neighborhoods are cells in cities, people who are in the same neighborhood randomly interact
 */
public class Neighborhood {

    private List<Person> residents;
    int width, height;

    public Neighborhood (Person[] residents, int width, int height) {
        this.residents = Arrays.asList(residents);
        this.width = width;
        this.height = height;
    }

    public Neighborhood (int residents) {
        this.residents = new ArrayList<>();
        for (int i = 0; i < residents; i++) {
            this.residents.add(new Person(Util.randomPosition()));
        }
    }

    public List<Person> getResidents () {
        return this.residents;
    }
}
