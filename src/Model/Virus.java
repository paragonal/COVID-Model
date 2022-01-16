package Model;

public class Virus {

    public double infectivity, recoveryTime;
    public String identifier;

    public Virus (double infectivity, double recoveryTime, String identifier) {
        this.infectivity = infectivity;
        this.recoveryTime = recoveryTime;
        this.identifier = identifier;
    }



}
