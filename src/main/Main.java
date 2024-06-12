package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        // Creating a window
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Prototype");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setLocation(200,200);

        // pack() adjusts window size based on size and layout of subcomponents (panels/GamePanel)
        window.pack();

        gamePanel.startGameThread();
    }
}
