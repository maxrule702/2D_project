package com.gaming;

import entity.Player;
import com.gaming.Main;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements  Runnable {
    //Screen settings
    final int oringinalTileSize = 16; // 16*16 tile
    final int scale = 3; //scaling character

    public final int tileSize = oringinalTileSize * scale;
    final int maxScreenCol = (int) 40;
    final float maxScreenRow = 22.5f;
    final int screenwidth = tileSize * maxScreenCol; //1080 pixels
    final float screenHeight = tileSize * maxScreenRow; //1920 pixels

    //FPS Limter
    int FPS = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);



    //set player default location
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;




    public GamePanel(){
        this.setPreferredSize((new Dimension(screenwidth, (int) screenHeight)));
        this.setBackground(new Color(255,255,255));
        this.setDoubleBuffered(true); //performance increase
        this.addKeyListener(keyH);
        this.setFocusable(true);



    }
public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

}

    @Override
    public void run() {
//This loop runs constantly until it the game is closed

        while(gameThread !=  null){

            double drawInterval = 1000000000/FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            while(gameThread != null){
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if (delta >= 1){
                    update();
                    repaint();
                    delta--;
                }
            }


update();

repaint();


        }
    }
//movement
    public void update(){

        player.update();


if(keyH.upPressed ==true){
    playerY -= playerSpeed;
}
else if (keyH.downPressed == true){
    playerY += playerSpeed;
}

else if (keyH.leftPressed == true){
    playerX -= playerSpeed;
}

else if (keyH.rightPressed == true){
    playerX += playerSpeed;
}
    }



    public void paintComponent(Graphics g){
        //repaint()
        //update graphics
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.fillRect(playerX,playerY,tileSize,tileSize);
        g2.dispose();



    }

}