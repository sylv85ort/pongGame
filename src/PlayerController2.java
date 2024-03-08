import java.awt.event.KeyEvent;

public class PlayerController2 {
    public Rect rect;
    public PlayerController playerController;
    public KL keyListener;
    public Rect ball;

    public PlayerController2(PlayerController plrController, KL keyListener) {
        this.playerController = plrController;
        this.keyListener = keyListener;
    }

    /* public PlayerController2(PlayerController plrController, Rect ball){
        this.playerController = plrController;
        this.ball = ball;
    } */

    public void update(double dt){

        if (keyListener != null) {
            if (keyListener.isKeyPressed(KeyEvent.VK_UP)) {
                playerController.moveUp(dt);
            } else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
                playerController.moveDown(dt);
            }
        }
    }
}
