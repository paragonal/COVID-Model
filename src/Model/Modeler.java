package Model;

import javax.xml.bind.SchemaOutputResolver;

public class Modeler implements Runnable{

    private City city;
    private Virus virus;
    private double updateTime;
    private double dt;
    private double lastUpdate;
    private long startTime;
    private Renderer renderer;

    public static void main(String args[]) {
        City city = new City("Test City", 20, 20, 10000);
        Virus virus = new Virus(1, 10, "Test virus");

        city.initializeSickness(5,5, 1, virus);

        Renderer r = new Renderer(city, 10);

        Modeler m = new Modeler(city, virus, 0.01, 50, r);
        Thread logicThread = new Thread(m, "Logic thread");
        logicThread.start();

        //Renderer r = new Renderer(city, 10);
        //Thread renderThread = new Thread(r, "Render thread");
        //renderThread.start();


    }

    /**
     * @param city City in question
     * @param virus Virus in question
     * @param dt Time step for people moving
     * @param updateTime How often in milliseconds the model will update
     */
    public Modeler (City city, Virus virus, double dt, double updateTime, Renderer renderer) {
        this.city = city;
        this.virus = virus;
        this.dt = dt;
        this.updateTime = updateTime;
        lastUpdate = 0;
        this.renderer = renderer;
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
                renderer.drawCity(city, 20);
                lastUpdate = System.currentTimeMillis();
            }
        }
    }
}
