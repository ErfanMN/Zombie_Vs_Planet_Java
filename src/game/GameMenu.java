package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Erfan on 24/06/2016.
 */
public class GameMenu extends JFrame {

    public static JFrame frame = new JFrame();
    public static final int GAME_HEIGHT = 720;                  // 720p game resolution
    public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio


    public GameMenu(String Title) {
        super(Title);
        frame.setResizable(false);
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


    public void ShowMenu() {
        ImagePanel MainPanel = new ImagePanel("Resource/Wallpaper.jpg");
        MainPanel.setLayout(null);

        final JButton Play = new JButton("شروع بازی");
        Play.setFont(new Font("tahoma", Font.PLAIN, 14));
        Play.setBackground(new Color(0x2dce98));
        Play.setForeground(Color.white);
        // customize the button with your own look
        Play.setUI(new StyledButtonUI());
        Play.setBounds(540, 100, 200, 50);
        Play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // Initialize the global thread-pool
            EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(false);
                GameFrame frame = new GameFrame("Plants Vs Zombies");
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init();

                ThreadPool.execute(game);
                // and the game starts ...

                 }
            });
            }
        });


        final JButton Option = new JButton("تنظیمات");
        Option.setFont(new Font("tahoma", Font.PLAIN, 14));
        Option.setBackground(new Color(0x2dce98));
        Option.setForeground(Color.white);
        // customize the button with your own look
        Option.setUI(new StyledButtonUI());
        Option.setBounds(540, 200, 200, 50);

        final JButton Exit = new JButton("خروج");
        Exit.setFont(new Font("tahoma", Font.PLAIN, 14));
        Exit.setBackground(new Color(0x2dce98));
        Exit.setForeground(Color.white);
        // customize the button with your own look
        Exit.setUI(new StyledButtonUI());
        Exit.setBounds(540, 300, 200, 50);
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //frame.setLayout(new BorderLayout());

        MainPanel.add(Play);
        MainPanel.add(Option);
        MainPanel.add(Exit);


        frame.add(MainPanel);
        frame.setVisible(true);
        Option.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        MusicPlayer MP = new MusicPlayer();
        MP.music("Resource/PP.wav");
    }



}



