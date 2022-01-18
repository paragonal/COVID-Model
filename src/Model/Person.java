package Model;

import java.util.HashSet;
import java.util.Set;

public class Person {
    public Position position;
    public Position velocity;

    public boolean sick, recovered;

    private Set<String> immunities = new HashSet<>();

    private int ticksLived = 0;
    private int tickInfected;
    private Virus currentVirus;

    public Person (Position position, Position velocity) {
        this.position = position;
        this.velocity = velocity;
        this.sick = false;
        this.recovered = false;
    }

    public void update(double dt) {
        this.ticksLived += 1;
        if (sick && this.ticksLived - tickInfected > currentVirus.recoveryTime) {
            sick = false;
            recovered = true;
            currentVirus = null;
        }
        this.position.add(this.velocity.scale(dt));
    }

    /**
     * See whether our person becomes infected, prob of being infected is based on infected ratio ^ (1/infectivity)
     * Has the nice property of higher infectivity always resulting in higher chance of being infected for same ratio
     *
     * @param infectedRatio Ratio of people in neighborhood who are infected
     * @param virus The virus in question
     * @return true if newly infected false otherwise
     */
    public boolean infectionStep(double infectedRatio, Virus virus) {
        if (!sick && !immunities.contains(virus.identifier)
                && Util.rng.nextDouble() < Math.pow(infectedRatio, 1 / virus.infectivity)) {
            makeSick(virus);
            return true;
        }
        return false;
    }

    public void makeSick(Virus virus) {
        this.sick = true;
        this.tickInfected = ticksLived;
        currentVirus = virus;
        immunities.add(currentVirus.identifier);
    }

}

