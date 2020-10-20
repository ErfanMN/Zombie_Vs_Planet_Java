package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Erfan on 25/06/2016.
 */
public class Flowers {

    public int mode;

    public BufferedImage BI;

    public int Cost ;

    public int health;

    public int Damage;

    public String FlowerMusic;

    public BufferedImage Shoot;


    /**
     * Make Flowers
     * @param Mode
     */
    public Flowers(int Mode)
    {
        mode = Mode;
        SetFlower();
    }

    /**
     * Set the Flowers
     */
    public void SetFlower() {
        String Url = "";
        if (mode == 1) {
            Url = "Resource/Peashooter.png";
            health = 100;
            Damage = 50;

            try {
                Shoot = ImageIO.read(new File("Resource/PeaShoot.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cost = 100;
        } else if (mode == 2) {
            Url = "Resource/Snowshooter.png";
            health = 100;
            Damage = 100;

            try {
                Shoot = ImageIO.read(new File("Resource/SnowShoot.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cost = 175;
        } else if (mode == 3) {
            Url = "Resource/Sunflower.png";
            health = 50;
            Damage = 0;
            try {
                Shoot = ImageIO.read(new File("Resource/SunShoot.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cost = 50;
        } else if (mode == 4) {
            health = 300;
            Damage = 0;
            Url = "Resource/Deff.png";
            Cost = 50;
        } else if (mode == 5) {
            Url = "Resource/Cherry.png";
            health = 50;
            Damage = 200;
            try {
                Shoot = ImageIO.read(new File("Resource/CherryBomb.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Cost = 150;
        }


        if(mode != 0) {
            try {
                BI = ImageIO.read(new File(Url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
