package game;

import java.awt.*;

/**
 * Created by Erfan on 01/07/2016.
 */
public class Ground {

    public int[][][] MyGround = new int[9][5][2];
    public boolean[][] Fill = new boolean[9][5] ;
    public int[][] FlowerMode = new int[9][5];
//    public Flowers flowers[][] = new Flowers[9][5];
    int TempMode;
    int counter[][] = new int[9][5]  ;
    int How;
    boolean hasSun[][] = new boolean[9][5];
    boolean GoneSun = false;
    boolean DrawAble[][] = new boolean[9][5];
    boolean Fillzombies[] = new boolean[5];
    int frame = 0  , frame2 = 0;
    Zombies AllZombies[] = new Zombies[5];
    Flowers AllFlowers[][] = new Flowers[9][5];
    boolean Dead[] = new boolean[5];
    boolean ShowShoot[][] = new boolean[9][5];
    boolean Define[][] = new boolean[9][5];


    /**
     * Make a Ground Consist of Zombies And Flowers
     */
    public Ground() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++) {
                MyGround[i][j][0] = 251 + i*80; // X of Each Piece
                MyGround[i][j][1] = 198 + j*97; // Y of Each Piece
                Fill[i][j] = false;
                counter[i][j] = 0;
                hasSun[i][j] = false;
                DrawAble[i][j] = true;
                ShowShoot[i][j] = true;
                Define[i][j] = false;
            }




                for(int i = 0 ; i < 5 ; i++) {
                    AllZombies[i] = new Zombies();
                    Fillzombies[i] = false;
                    Dead[i]= false;
                }
    }

    /**
     * Tell us Which Flowers Are Where !
     * @param light
     * @param mode
     * @param state
     */
    public void GetFlowers(Light light  ,int mode , GameState state)
    {
        if(mode != 0)
            TempMode = mode;

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 5; j++) {
                if(state.locX > 248 + i*80 && state.locX < 248 + (i+1)*80 && state.locY > 195+ (j*97) && state.locY < 195 + (j+1)*97)
                {
                    Flowers flw = new Flowers(TempMode);
                    if( Fill[i][j] &&  hasSun[i][j])
                    {
                        light.sum += 50;
                        hasSun[i][j] = false;
                        GoneSun = true;
                    }
                    if(light.sum >= flw.Cost && !Fill[i][j] && TempMode != 0) {
                        Fill[i][j] = true;
                        FlowerMode[i][j] = TempMode;
                        light.sum -= flw.Cost;
                        TempMode = 0;
                    }

                }
            }
        }
    }

    /**
     * Draw Flowers In it's Place
     * @param g2d
     */
    public void DrawFlowers(  Graphics2D g2d)
    {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                if (Fill[i][j] ) {

                    Flowers flw = new Flowers(FlowerMode[i][j]);
                    if((FlowerMode[i][j] == 1 || FlowerMode[i][j] == 2)  )
                    {
                        if(ShowShoot[i][j] )
                            g2d.drawImage(flw.Shoot , MyGround[i][j][0] + counter[i][j] + 70 , MyGround[i][j][1]+20 , null );
                        counter[i][j] += 2;
                        if(counter[i][j] == 1000) {
                            counter[i][j] = 0;
                            ShowShoot[i][j] = true;
                        }
                    }
                    if(DrawAble[i][j]) {
                        g2d.drawImage(flw.BI, MyGround[i][j][0], MyGround[i][j][1], null);
                    }
                    if(FlowerMode[i][j] == 3 && !hasSun[i][j]) {
                        counter[i][j] += 2;
                        if(counter[i][j] == 1000)
                            counter[i][j] = 0;
                    }

                    if(FlowerMode[i][j] == 3 && counter[i][j] >= 500) {
                        hasSun[i][j] = true;
                        counter[i][j] = 0;
                    }

                    if(FlowerMode[i][j] == 3   && hasSun[i][j]) {
                        g2d.drawImage(flw.Shoot,  MyGround[i][j][0] + 20 ,MyGround[i][j][1] -20  , null);

                    }

                    if(FlowerMode[i][j] == 5 )
                    {
                        counter[i][j]++;
                        if(counter[i][j] > 100)
                        {
                            g2d.drawImage(flw.Shoot,  MyGround[i][j][0]  ,MyGround[i][j][1]  , null);
                            DrawAble[i][j] = false;
                            ShowShoot[i][j] = false;
                            if(counter[i][j] > 200) {
                                counter[i][j] = 0;
                                Fill[i][j] = false;
                                ShowShoot[i][j] = true;
                            }
                        }
                    }
                    if(!Define[i][j]) {
                        AllFlowers[i][j] = flw;
                        Define[i][j] = true;
                    }
                }
            }
        }
    }

    /**
     * Draw Zombies In it's Place
     * @param g2d
     */
    public void DrawZombies( Graphics2D g2d)
    {
        for (int i = 0; i < 5; i++) {
                if(Fillzombies[i] && !Dead[i] ) {
                    if(AllZombies[i].mode == 3)
                        g2d.drawImage(AllZombies[i].BI, AllZombies[i].varX, AllZombies[i].varY + 40, null);
                    else
                        g2d.drawImage(AllZombies[i].BI, AllZombies[i].varX, AllZombies[i].varY, null);
                    AllZombies[i].Update();
                }
        }
        frame++;
        for(int i = 0 ; i < 5 ; i++)
        {
            if(frame > 200*(i+1) && !Dead[i])
                Fillzombies[i] = true;
        }


        if(frame >= 3000)
            frame = 0 ;
    }

    /**
     * Check if Zombie Die And Hiden it
     */
    public void CheckIfZombieDie()
    {
        for(int i = 0 ; i < 9 ; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (Fill[i][j]) {
                        if (AllFlowers[i][j].mode == 1 || AllFlowers[i][j].mode == 2) {
                            if (MyGround[i][j][0] + counter[i][j] >= AllZombies[k].varX && AllZombies[k].varX > MyGround[i][j][0] && MyGround[i][j][1] > AllZombies[k].varY && MyGround[i][j][1] < AllZombies[k].varY + 120 && ShowShoot[i][j] ) {
                                AllZombies[k].health -= AllFlowers[i][j].Damage;
                                if (AllZombies[k].health <= 0) {
                                    Fillzombies[k] = false;
                                    Dead[k]= true;
                                    ShowShoot[i][j] = false;
                                }
                                if(AllFlowers[i][j].mode == 2 && AllZombies[k].health > 0)
                                    AllZombies[k].Slow();
                            }
                        }
                        if(AllFlowers[i][j].mode == 5)
                        {
                            double fasele = Math.sqrt((MyGround[i][j][0]-AllZombies[k].varX -40)*(MyGround[i][j][0]-AllZombies[k].varX - 40) + (MyGround[i][j][1] -AllZombies[k].varY -90)*(MyGround[i][j][1] -AllZombies[k].varY - 90));
                            if( fasele < 100 && !ShowShoot[i][j] )
                            {
                                AllZombies[k].health -= AllFlowers[i][j].Damage;
                                if (AllZombies[k].health <= 0) {
                                    Fillzombies[k] = false;
                                    Dead[k]= true;
                                    ShowShoot[i][j] = false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if Flower die and Hiden it
     */
    public void CheckIfFlowerDie()
    {
        for(int i = 0 ; i < 9 ; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if (Fill[i][j]) {
                        if ( MyGround[i][j][0] >= AllZombies[k].varX  &&  MyGround[i][j][0] - AllZombies[k].varX <= 70 && ((MyGround[i][j][1] > AllZombies[k].varY && MyGround[i][j][1] < AllZombies[k].varY + 120 ) || AllZombies[k].varY < 120)  ) {
                            frame2++;
                            if(frame2%20 == 0)
                                AllFlowers[i][j].health -= AllZombies[k].Damage;
                            if(frame2 == 1000)
                                frame2 = 0;

                            if(AllFlowers[i][j].health <= 0)
                            {
                                Fill[i][j] = false;
                                DrawAble[i][j] = false;

                            }
                            else if(AllFlowers[i][j].health > 0 && AllZombies[k].mode != 3)
                            {
                                AllZombies[k].Downgrade();
//                                System.err.print(AllFlowers[i][j].health + " - " + i + " - " + j + " - " + AllZombies[k].Damage + " - ");

                            }
                            else if(AllFlowers[i][j].health > 0 && AllZombies[k].mode == 3 )
                            {
                                AllZombies[k].Jump();
                                AllFlowers[i][j].health += AllZombies[k].Damage;
                            }
                        }
                        if(MyGround[i][j][0] - AllZombies[k].varX >= 50 )
                        {
                            DrawAble[i][j] = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Tell When player Game over
     * @return
     */
    public boolean GameOver()
    {
        for(int i = 0 ; i < 5 ; i++)
        {
            if(AllZombies[i].varX < 220)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Tell when Player Win The Game
     * @return
     */
    public boolean Win()
    {
        int k = 0;
        for(int i = 0 ; i < 5 ; i++)
        {
            if(Dead[i])
            {
                k++;
                if(k == 5)
                    return true;
            }
        }
        return false;
    }
}
