package Model;

import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer implements Runnable {

    private JFrame scene;
    private BufferedImage bufferedImage;
    private Graphics bufferedGraphics;

    private JPanel currentImage;
    private double updateTime;
    private double lastUpdate;
    private City model;
    private int width, height;
    private int drawUnit;

    public Renderer (City city, double updateTime, int width, int height, int drawUnit) {
        this.width = width;
        this.height = height;
        this.drawUnit = drawUnit;

        this.scene= new JFrame();
        this.currentImage = new JPanel();
        this.bufferedImage = new BufferedImage(width+50,height+50, BufferedImage.TYPE_INT_RGB);
        this.bufferedGraphics = bufferedImage.createGraphics();

        this.scene.add(currentImage);
        this.scene.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.scene.setSize(width+50,height+50);
        this.scene.setVisible(true);

        this.updateTime = updateTime;
        this.model = city;
        this.lastUpdate = 0;
    }

    /**
     *
     * @param city
     */
    public void drawCity(City city) {
        Graphics g = bufferedGraphics;
        g.setColor(Color.WHITE);
        g.fillRect(0,0, width + 50,height + 50);

        for (int i = 0; i < city.width; i++) {
            for (int j = 0; j < city.height; j++) {
                Neighborhood neighborhood = city.getNeighborhood(i,j);
                g.setColor(Color.BLACK);
                g.drawRect(i * drawUnit, j * drawUnit, drawUnit, drawUnit);

                for (Person p : neighborhood.residents) {
                    if (p.sick)
                        g.setColor(Color.RED);
                    else if (p.recovered)
                        g.setColor(Color.BLUE);
                    else
                        g.setColor(Color.GREEN);
                    g.fillOval(i*drawUnit+(int) (drawUnit * p.position.x / neighborhood.width),
                            j*drawUnit+(int) (drawUnit * p.position.y / neighborhood.height),5,5);
                }
            }
        }
        currentImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
    }


    @Override
    public void run() {
        while (true) {
            if (System.currentTimeMillis() - lastUpdate > updateTime) {

                drawCity(model);

                lastUpdate = System.currentTimeMillis();
            }
        }
    }
}
