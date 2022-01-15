package Model;

public class Person {
    public Position position;

    boolean sick, recovered, vaccinated;

    public Person (Position position) {
        this.position = position;
        this.sick = false;
        this.recovered = false;
        this.vaccinated = false;
    }

}

