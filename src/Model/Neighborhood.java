package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Neighborhoods are cells in cities, people who are in the same neighborhood randomly interact
 */
public class Neighborhood {

    public List<Person> residents;
    int width, height;

    /**
     * Update our neighborhood
     * Return any residents who are leaving the neighborhood so city can deal with them
     */
    public Person[] updatePositions (double dt) {
        return moveResidents(dt);
    }

    public int updateInfections (Virus virus, double dt) {
        updateResidents(dt);
        return infectResidents(virus);
    }

    private void updateResidents (double dt) {
        for (Person p : residents)
            p.update(dt);
    }

    private Person[] moveResidents (double dt) {
        List<Person> departingResidents = new ArrayList<>();
        for (Person p : residents) {
            p.position = p.position.add(p.velocity.scale(dt));
            if (p.position.x < 0 || p.position.x > width || p.position.y < 0 || p.position.y > height)
                departingResidents.add(p);
        }
        residents.removeAll(departingResidents);
        return departingResidents.toArray(new Person[0]);
    }

    public Neighborhood (Person[] residents, int width, int height) {
        this.residents = Arrays.asList(residents);
        this.width = width;
        this.height = height;
    }

    public Neighborhood (int residents) {
        this.width = 1;
        this.height = 1;
        this.residents = new ArrayList<>();
        for (int i = 0; i < residents; i++) {
            this.residents.add(new Person(Util.randomPosition(), Util.randomVelocity()));
        }
    }


    // Infect residents in the neighborhood
    private int infectResidents (Virus virus) {
        int newlyInfected = 0;
        double infectedRatio = (double) getInfected() / residents.size();
        for (Person p : residents)
            newlyInfected += p.infectionStep(infectedRatio, virus) ? 1 : 0;
        return newlyInfected;
    }

    // Count number of infected people
    public int getInfected () {
        return (int) residents.stream().filter((x) -> (x.isSick())).count();
    }

    public List<Person> getResidents () {
        return this.residents;
    }
}
