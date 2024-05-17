import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Bullet {
    private int x;
    private int y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);
    private int elapsedTime = 0;
    private int spentBullets = 0;
    private BufferedImage image;

    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    private int bulletdirY = 1;
    private int ballX = 0;
    private int balldirX = 2;
    private int spaceShipX = 0;
    private int dirSpaceX = 20;

    public boolean control(){
        for(Bullet bullet : bullets){
            if(new Rectangle(bullet.getX(), bullet.getY(),10, 20).intersects(new Rectangle(ballX, 0,20,20))){
                return true;
            }
        }
        return false;
    }


    public Game() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("SpaceShip.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.BLACK);
        timer.start();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        elapsedTime += 5;
        g.setColor(Color.red);
        g.fillOval(ballX, 0, 20, 20);

        g.drawImage(image, spaceShipX, 490, image.getWidth() /50, image.getHeight() /50, this);

        for(Bullet bullet : bullets){
            if(bullet.getY() < 0){
                bullets.remove(bullet);
            }
        }
        g.setColor(Color.BLUE);
        for(Bullet bullet : bullets){
            g.fillRect(bullet.getX(), bullet.getY(), 10, 20);
        }
        if(control()){
            timer.stop();
            String message = "You won...\n" +
                    "Spent bullets : " + spentBullets +
                    "\nElapsed time : " + elapsedTime / 1000.0 + " second";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        if (c == KeyEvent.VK_LEFT) {
            if(spaceShipX <= 0){
                spaceShipX = 0;
            }
            else{
                spaceShipX -= dirSpaceX;
            }
        }
        else if (c == KeyEvent.VK_RIGHT) {
            if(spaceShipX >= 740){
                spaceShipX = 740;
            }
            else{
                spaceShipX += dirSpaceX;
            }
        }
        else if (c == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(spaceShipX + 15,490));
            spentBullets++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Bullet bullet : bullets){
            bullet.setY(bullet.getY() - bulletdirY);
        }

        ballX += balldirX;
        if (ballX >= 760) {
            balldirX = -balldirX;
        }
        if(ballX <= 0){
            balldirX = - balldirX;
        }
        repaint();
    }
}