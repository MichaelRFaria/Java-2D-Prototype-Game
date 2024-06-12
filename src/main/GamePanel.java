package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

// Extend JPanel as it's a class
// Implements Runnable as it's an interface
public class GamePanel extends JPanel implements Runnable {

    // Creating panel for the game
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    // Thread keeps program running until you stop it
    // Useful for redrawing screen
    Thread gameThread;
    Player player = new Player(this,keyH);


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        // Allows GamePanel to receive input
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Instantiate the gameThread;
    public void startGameThread() {

        // Passing GamePanel class into Thread constructor
        gameThread = new Thread(this);
        // Calls run() method
        gameThread.start();
    }

    // When the thread is created the run method is called automatically
    // Game loop created in this method

    // Commented code below is the "sleep" method for the game loop
    /*public void run() {

        // Limiting update/draw loop to 60fps
        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        // Loops as long as the thread exists
        while(gameThread != null) {
            // 1. UPDATE: update variables
            update();

            // 2. DRAW: draw the screen with the updated information
            // repaint() calls the paintComponent method
            repaint();

            // try/catch is needed for the Thread to sleep
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000; // converts from ns to ns as sleep only accepts ms

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime); // Thread is inactive for the time (ms)

                nextDrawTime += drawInterval;

                // auto-generated
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

    // Code below is the "delta" method for the game loop
    public void run() {
        // Limiting update/draw loop to 60fps
        double drawInterval = (double) 1000000000 / FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Loops as long as the thread exists
        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                // 1. UPDATE: update variables
                update();
                // 2. DRAW: draw the screen with the updated information
                // repaint() calls the paintComponent method
                repaint();

                delta--;
            }

        }
    }

    public void update() {

        player.update();
    }

    /* Standard java method to draw on JPanel
    The "Graphics" parameter is a class that has many functions to
    draw objects on the screen */
    public void paintComponent(Graphics g) {
        // Gives access to the parent class of this class
        // Which is JPanel because GamePanel is a subclass of JPanel
        super.paintComponent(g);

        /* Graphics2D class extends the Graphics class to provide
        better control over geometry, coordinate transformations,
        color management and text layout*/
        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        player.draw(g2);

        // Saves memory by releasing system resources after use
        g2.dispose();
    }
}
