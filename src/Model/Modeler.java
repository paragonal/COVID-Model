package Model;

import javax.xml.bind.SchemaOutputResolver;

public class Modeler implements Runnable{

    private City city;
    private Virus virus;
    private double updateTime;
    private double dt;
    private double lastUpdate;
    private long startTime;

    public static void main(String args[]) {
        City city = new City("Test City", 10, 10, 1000000);
        Virus virus = new Virus(1, 50, "Test virus");

        city.initializeSickness(0,0, 5, virus);

        Modeler m = new Modeler(city, virus, 0.001, 10);
        Thread logicThread = new Thread(m, "Logic thread");
        logicThread.start();
    }

    /**
     * @param city City in question
     * @param virus Virus in question
     * @param dt Time step for people moving
     * @param updateTime How often in milliseconds the model will update
     */
    public Modeler (City city, Virus virus, double dt, double updateTime) {
        this.city = city;
        this.virus = virus;
        this.dt = dt;
        this.updateTime = updateTime;
        lastUpdate = 0;
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        int totalInfected = 1;

        while (true) {
            if (System.currentTimeMillis() - lastUpdate > updateTime) {
                int newlyInfected = city.update(virus, dt);
                totalInfected += newlyInfected;
                System.out.println("New infections:\t" + newlyInfected + "\tTotal sick:\t" + totalInfected);
                lastUpdate = System.currentTimeMillis();
            }
        }
    }
}
