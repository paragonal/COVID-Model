package Model;

import java.util.Arrays;
import java.util.List;

/**
 * Neighborhoods are cells in cities, people who are in the same neighborhood randomly interact
 */
public class Neighborhood {

    private List<Person> residents;

    public Neighborhood (Person[] residents) {
        this.residents = Arrays.asList(residents);
    }

    public List<Person> getResidents () {
        return this.residents;
    }
}
