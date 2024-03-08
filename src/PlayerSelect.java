import javax.swing.JFrame;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.KeyEvent;

public class PlayerSelect extends JFrame implements Runnable {

    public Graphics2D g2;
    public KL keyListener = new KL();
    public ML mouseListener = new ML();
    public Text pongTitle, onePlayerGame, twoPlayerGame, gameRules;
    public boolean isRunning = true;



    //Settings for the rendering window
    public PlayerSelect(){
        //Initializes window
        this.setSize(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.onePlayerGame = new Text("Player V. AI", new Font("Times New Roman", Font.PLAIN, 40), Constants.SCREEN_WIDTH / 2.0 - 150, Constants.SCREEN_HEIGHT / 2.0, Color.WHITE);
        this.twoPlayerGame = new Text("Player V. Player", new Font("Times New Roman", Font.PLAIN, 40), Constants.SCREEN_WIDTH / 2.0 - 150, Constants.SCREEN_HEIGHT / 2.0 + 60, Color.WHITE);
        this.pongTitle = new Text("Player Select", new Font("Times New Roman", Font.PLAIN, 100), Constants.SCREEN_WIDTH / 2.0 - 250.0, 200.0, Color.WHITE);
        this.gameRules = new Text("Rules: First player to 7 wins.", new Font("Times New Roman", Font.PLAIN, 25), Constants.SCREEN_WIDTH / 2.0 - 250, Constants.SCREEN_HEIGHT / 2.0 + 180, Color.WHITE);


        g2 = (Graphics2D)this.getGraphics();

    }

    public void update(double dt){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        //Checks if mouse is hovering over start button
        //Similar syntax to PlayerController collision with ball

        if (mouseListener.getMouseX() > onePlayerGame.x && mouseListener.getMouseX() < onePlayerGame.x + onePlayerGame.width &&
                mouseListener.getMouseY() > onePlayerGame.y - onePlayerGame.height / 2.0  && mouseListener.getMouseY() < onePlayerGame.y + onePlayerGame.height / 2.0) {
            onePlayerGame.color = new Color(98, 250, 255);
            if (mouseListener.isMousePressed()){
                Window.isMultiplayer = false;
                Main.changeState(1); //change state to 1
            }
        } else {
            onePlayerGame.color = Color.WHITE;
        }

        if (mouseListener.getMouseX() > twoPlayerGame.x && mouseListener.getMouseX() < twoPlayerGame.x + twoPlayerGame.width &&
                mouseListener.getMouseY() > twoPlayerGame.y - twoPlayerGame.height / 2.0  && mouseListener.getMouseY() < twoPlayerGame.y + twoPlayerGame.height / 2.0) {
            twoPlayerGame.color = new Color(98, 250, 255);
            //Sets isMultiplayer to true, activating the 2nd Player Controller.
            if (mouseListener.isMousePressed()) {
                Window.isMultiplayer = true;
                Main.changeState(1);
            }
        } else {
            twoPlayerGame.color = Color.WHITE;
        }


    }

    //Used for double buffer; Smooths out and reduces jitter
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        onePlayerGame.draw(g2);
        twoPlayerGame.draw(g2);
        pongTitle.draw(g2);
        gameRules.draw(g2);

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
        System.out.println("nigge3");
        this.dispose();
        return;
    }
}