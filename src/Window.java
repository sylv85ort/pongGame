import javax.swing.JFrame;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable {

     public Graphics2D g2;
     public KL keyListener = new KL();
     public Rect playerOne, ai, ballRect;
     public Ball ball;
     public PlayerController playerController;
     public PlayerController2 playerController2;
     public AIController aiController;
     public Text leftScoreText, rightScoreText;
     public boolean isRunning = true;
     public static boolean isMultiplayer = false;


     //Settings for the rendering window
    public Window(){
        //Initializes window
        this.setSize(Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        Constants.TOOLBAR_HEIGHT = this.getInsets().top;
        Constants.INSETS_BOTTOM = this.getInsets().bottom;

        leftScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.TEXT_X_POS,Constants.TEXT_Y_POS);
        rightScoreText = new Text(0,
                new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE),
                Constants.SCREEN_WIDTH - Constants.TEXT_X_POS - Constants.TEXT_SIZE,Constants.TEXT_Y_POS);

        g2 = (Graphics2D)this.getGraphics();

    //Draws all on-screen objects and places/sizes them accordingly.
        //Activates Controllers as well
        playerOne = new Rect(Constants.HZ_PADDING + 25,40 ,Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        playerController = new PlayerController(playerOne, keyListener);

        ai = new Rect(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH - Constants.HZ_PADDING-25, 40,Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ballRect = new Rect(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT /2, Constants.BALL_SIZE,Constants.BALL_SIZE,Constants.BALL_COLOR);
        ball = new Ball(ballRect, playerOne, ai, leftScoreText, rightScoreText);
        //If isMultiplayer = true, the right paddle will be player Controlled. Else, the right paddle will be AI controlled
        if (isMultiplayer == true) {
            playerController.isAI = false;
            playerController2 = new PlayerController2(new PlayerController(ai), keyListener);
            System.out.println("It worked.");
        } else {
            aiController = new AIController(new PlayerController(ai), ballRect);
            System.out.println("what");

        }
    }

    public void update(double dt){
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);
        //System.out.println(" " + dt + " seconds have passed since last frame");
        //System.out.println(1 / dt + " fps");
        playerController.update(dt);
        if (isMultiplayer) {
            playerController2.update(dt);
        } else {
            aiController.update(Ball.plScore, dt);
        }
        ball.update(dt);

    }

    //Used for double buffer; Smooths out and reduces jitter
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        leftScoreText.draw(g2);
        rightScoreText.draw(g2);
        playerOne.draw(g2);
        ai.draw(g2);
        ballRect.draw(g2);
    }

    public void stop(){
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
        //Limits framerate around 130 fps, depending on system running Program
            try {
                Thread.sleep(6);
            } catch (Exception e) {
            }

        }
        this.dispose();
        return;
    }
}