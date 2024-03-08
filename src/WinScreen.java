import javax.swing.JFrame;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.KeyEvent;

public class WinScreen extends JFrame implements Runnable {

    public Graphics2D g2;
    public KL keyListener = new KL();
    public ML mouseListener = new ML();
    public Text WinTitle, exitGame;
    public boolean isRunning = true;
    public boolean isWinner = true;



    //Settings for the rendering window
    public WinScreen(){
        //Initializes window
        this.setSize(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.exitGame = new Text("Exit Game", new Font("Times New Roman", Font.PLAIN, 40), Constants.SCREEN_WIDTH / 2.0 - 150, Constants.SCREEN_HEIGHT / 2.0, Color.WHITE);
        this.WinTitle = new Text("You won! Congratulations! ", new Font("Times New Roman", Font.PLAIN, 100), Constants.SCREEN_WIDTH / 2.0 - 250.0, 200.0, Color.WHITE);
        if (Ball.isWinner) {
            exitGame.text = "You Winner!";
        } else {
            exitGame.text = "You Loser! hahahha";
        }
        g2 = (Graphics2D)this.getGraphics();

    }

    public void update(double dt){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        //Checks if mouse is hovering over start button
        //Similar syntax to PlayerController collision with ball

        if (mouseListener.getMouseX() > exitGame.x && mouseListener.getMouseX() < exitGame.x + exitGame.width &&
                mouseListener.getMouseY() > exitGame.y - exitGame.height / 2.0  && mouseListener.getMouseY() < exitGame.y + exitGame.height / 2.0) {
            exitGame.color = new Color(98, 250, 255);
            if (mouseListener.isMousePressed()){
                Window.isMultiplayer = false;
                Main.changeState(1); //change state to 1
            }
        } else {
            exitGame.color = Color.WHITE;
        }

    }

    //Used for double buffer; Smooths out and reduces jitter
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        exitGame.draw(g2);
        WinTitle.draw(g2);
    }
    public void stop() {
        isRunning = false;
    }
    public void run() {
        double lastFrameTime = 0.0;
        while (isRunning) {
            //Counts frame rate, gets more accurate after 2nd frame
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);
            /*Limits framerate around 30-32 fps, depending on system running Program
            try {
                Thread.sleep(60);
            } catch (Exception e) {
            } */
        }
        this.dispose();
        return;
    }
}