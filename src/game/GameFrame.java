/*** In The Name of Allah ***/
package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,  For more information on BufferStrategy check out:
 * actually it performs triple-buffering!
 *
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {
	
	public static final int GAME_HEIGHT = 720;                  // 720p game resolution
	public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio
	public int counter = 0 ;
	String flowerUrl = "";
	private long lastRender;
	private ArrayList<Float> fpsHistory;
	private BufferStrategy bufferStrategy;
	Light light = new Light();
	Ground ground = new Ground();
//	Flowers flowers[] = new Flowers[45];
//	int FlwCounter = 0;
	int mode = 0 ;
	int CurrentLevel = 1;
	boolean FlwSelected ;

	public GameFrame(String title) {
		super(title);
		setResizable(false);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		lastRender = -1;
		fpsHistory = new ArrayList<>(100);
		setBackground(Color.black);

	}
	
	/**
	 * This must be called once after the JFrame is shown:
	 *    frame.setVisible(true);
	 * and before any rendering is started.
	 */
	public void initBufferStrategy() {
		// Triple-buffering
		createBufferStrategy(3);
		bufferStrategy = getBufferStrategy();

	}

	
	/**
	 * Game rendering with triple-buffering using BufferStrategy.
	 */
	public void render(GameState state) {

		// Render single frame
		do {
			// The following loop ensures that the contents of the drawing buffer
			// are consistent in case the underlying surface was recreated
			do {
				// Get a new graphics context every time through the loop
				// to make sure the strategy is validated
				Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
				try {

					doRendering(graphics, state);
				} finally {
					// Dispose the graphics
					graphics.dispose();
				}
				// Repeat the rendering if the drawing buffer contents were restored
			} while (bufferStrategy.contentsRestored());

			// Display the buffer
			bufferStrategy.show();
			// Tell the system to do the drawing NOW;
			// otherwise it can take a few extra ms and will feel jerky!
			Toolkit.getDefaultToolkit().sync();

		// Repeat the rendering if the drawing buffer was lost
		} while (bufferStrategy.contentsLost());
	}
	
	/**
	 * Rendering all game elements based on the game state.
	 */
	private void doRendering(Graphics2D g2d, GameState state) {

		setVisible(true);

//		g2d.setColor(Color.GRAY);
//		g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		// Draw ball
		BufferedImage image = null;
        try {
            image = ImageIO.read(new File("Resource/Ground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2d.drawImage(image ,0 , 120 , null );




		flowerUrl = "Resource/FlowersMenu.jpg";

		if (state.locX >= 498 && state.locX < 570 && state.locY > 29 && state.locY < 107)
		{
			flowerUrl = "Resource/PeaSelected.jpg";
			mode = 1;
			FlwSelected = true;
		}
		else if (state.locX >= 570 && state.locX < 643 && state.locY > 29 && state.locY < 107)
		{
			flowerUrl = "Resource/SnowSelected.jpg";
			mode = 2;
			FlwSelected = true;
		}
		else if (state.locX >= 643 && state.locX < 717 && state.locY > 29 && state.locY < 107)
		{
			flowerUrl = "Resource/SunSelected.jpg";
			mode = 3;
			FlwSelected = true;
		}
		else if (state.locX >= 717 && state.locX < 790 && state.locY > 29 && state.locY < 107)
		{
			flowerUrl = "Resource/DeffSelected.jpg";
			mode = 4;
			FlwSelected = true;
		}
		else if (state.locX >= 790 && state.locX < 863 && state.locY > 29 && state.locY < 107)
		{
			flowerUrl = "Resource/CherrySelected.jpg";
			mode = 5;
			FlwSelected = true;
		} else
		{
			mode = 0;
		}

		if(FlwSelected) {
			FlwSelected = false;
		}
		ground.GetFlowers( light , mode , state);
		ground.DrawFlowers( g2d);
		ground.DrawZombies(g2d);
		ground.CheckIfZombieDie();
		ground.CheckIfFlowerDie();


		g2d.drawImage(light.LightBI , light.varX , light.varY , null);
		g2d.drawImage(light.SunBI , light.varX + 42 , light.varY + 42 , null);
		counter++;
		if(counter == 1000) {
			counter = 0;
			light = new Light();
		}

		light.Update(state);


		BufferedImage Scores = null;
		try {
			Scores = ImageIO.read(new File("Resource/Scoreboard.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2d.drawImage(Scores ,0 , 0 , null );





		BufferedImage Flowersmenu = null;
		try {
			Flowersmenu = ImageIO.read(new File(flowerUrl));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.print(flowerUrl);
		}
		g2d.drawImage(Flowersmenu , 500 , 30 , null );






		g2d.setColor(Color.white);
		g2d.setFont(new Font("Arial", Font.PLAIN, 20));
		g2d.fillOval(state.locX , state.locY , state.diam , state.diam);
		g2d.drawString("X : " + state.locX + " Y :" + state.locY , 400 , 400);
		g2d.drawString("X : " + light.varX + " Y :" + light.varY + " mode : " + mode , 400 , 500);
		g2d.drawString("انرژی خورشیدی : " , 250 , 72 );
		g2d.drawString( String.valueOf(light.sum) , 190 , 72 );
		g2d.drawString("مرحله ی " + CurrentLevel, 1000 , 70);

		// Print FPS info
		final long currentRender = System.currentTimeMillis();
		if (lastRender > 0) {
			fpsHistory.add(1000.0f / (currentRender - lastRender));
			if (fpsHistory.size() > 100) {
				fpsHistory.remove(0); // remove oldest
			}
			float avg = 0.0f;
			for (float fps : fpsHistory) {
				avg += fps;
			}
			avg /= fpsHistory.size();
			String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
					avg, (currentRender - lastRender));
		}
		lastRender = currentRender;
		// Draw GAME OVER
		if (ground.GameOver()) {
			String str = "GAME OVER";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
			state.gameOver = true;
		}
		if (ground.Win()) {
			String str = "You Win";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
			ground = new Ground();
			light = new Light();
			CurrentLevel++;

			if(CurrentLevel == 6)
				state.gameOver = true;

		}

	}
	
}
