package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Erfan on 30/06/2016.
 */
public class Light {

    public int varX , varY  ;

    public static int sum = 1000;

    int temp[] = { 111 , 211 , 311 , 411 , 511 , 611 , 711 , 811 , 911 };

    public boolean start ;
    public boolean chosen = false;


    public BufferedImage SunBI;
    public BufferedImage LightBI;
    String SunUrl ;
    String LightUrl;


    /**
     * Make SunLights
     */
    public Light()
    {
        Random ran = new Random();
        varX = temp[ran.nextInt(9)];
        varY = 0 ;
        start = true;
        SunUrl = "Resource/Sun.png";
        LightUrl = "Resource/Light.png";
        try {
            SunBI = ImageIO.read(new File(SunUrl));
            LightBI = ImageIO.read(new File(LightUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update The State of SunLights
     * @param state
     */
    public void Update(GameState state)
    {
        int n = 84;

        if(state.locX > varX   && state.locX < varX + n  && state.locY > varY && state.locY < varY + n && state.locY > 117) {

            chosen = true;
        }

        if(start && !chosen)
            varY += 1 ;

        if(start && chosen) {

            int shib = (varY - 60 ) / ( varX - 100);

            varX -= 10;
            varY -= 10*shib;
        }

        if(varX < 100 && chosen )
        {
            varX = -1;
            varY = -1;
            start = false;
        }

        if(varX == -1 && varY == -1 && chosen)
        {
            sum += 50;
            chosen = false;
        }

        if(varY >= 720 || varY < -1) {
            start = false;
            chosen = false;
        }



    }
}
