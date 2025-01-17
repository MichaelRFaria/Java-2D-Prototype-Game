package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Entity superclass
public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_up2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_down2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_right2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/mage_left2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // if statement prevents animation while no key is pressed
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // Player movement
            if (keyH.upPressed) {
                y -= speed;
                direction = "up";
            } else if (keyH.downPressed) {
                y += speed;
                direction = "down";
            } else if (keyH.leftPressed) {
                x -= speed;
                direction = "left";
            } else if (keyH.rightPressed) {
                x += speed;
                direction = "right";
            }

            spriteCounter++;

            if (spriteCounter > 15) { // update() called at 60fps, therefore sprite changes 4 times a sec
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }


    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                } break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                } break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                } break;

            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                } break;
        }

        // Draws an image to the screen
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
