package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Erfan on 25/06/2016.
 */
public class Zombies {

    public int health;
    public int Damage;
    public int mode;
    public BufferedImage BI;
    int frame = 0;
    public String MusicUrl;

    boolean slow = false;
    boolean jump = true;
    public int varX , varY;

    int temp[] = {295 , 395 , 505 , 585 , 685};

    /**
     * Make Zombies
     */
    public Zombies()
    {
        Random modeRan = new Random();
        mode = modeRan.nextInt(4) + 1;
//        mode = 3;
        SetZombie();
        varX = 1187;
        Random ran = new Random();
        varY = temp[ran.nextInt(5)] - 180;

    }

    /**
     * Set The Zombies
     */
    public void SetZombie() {
        String Url = "";
        if (mode == 1) {
            health = 100;
            Damage = 50;
            Url = "Resource/SuitZ.png";
        } else if (mode == 2) {
            health = 50;
            Damage = 50;
            Url = "Resource/ConeheadZ.png";
        } else if (mode == 3) {
            health = 50;
            Damage = 50;
            Url = "Resource/SportZ.png";
        } else if (mode == 4) {
            health = 50;
            Damage = 50;
            Url = "Resource/BaseballZ.png";
        } else {
            System.err.print("There is no Zombie in the Resource :| ! ");
        }

        try {
            BI = ImageIO.read(new File(Url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update The state of Zombie
     */
    public void Update()
        {
            varX -= 1;
        }

    /**
     * Stop Zombie
     */
    public void Downgrade()
        {
            varX += 1;
        }

    /**
     * Zombie Jumps
     */
    public void Jump() {
            if (jump) {
                varY -= 70;
                jump = false;
            }
            frame++;
            if (frame > 70) {
                if (!jump) {
                    varY += 70;

                    jump = true;
                }
            }
            if(frame == 200)
                frame = 0;

        }

    /**
     * Zombie move slower
     */
    public void Slow()
        {
            if(!slow) {
                varX += 1;
                slow = true;
            } else {
                varX += 0;
            }
        }
    }




