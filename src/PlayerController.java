import java.awt.event.KeyEvent;

public class PlayerController {
    public Rect rect;
    public KL keyListener;
    public KL keyListener2;
    public boolean isAI;
    public double rv = 1;

    public PlayerController(Rect rect, KL keyListener) {
        this.rect = rect;
        this.keyListener = keyListener;
    }

    public PlayerController(Rect rect){
        this.rect = rect;
        this.keyListener = null;
    }

    //Controls input
    //First checks if corresponding key is pressed
    //Secondly checks if player is in window border, to keep player from going out of bounds.
    public void update(double dt) {
        if (keyListener != null) {
            if (keyListener.isKeyPressed(KeyEvent.VK_S)) {
                moveDown(dt);
            } else if (keyListener.isKeyPressed(KeyEvent.VK_W)) {
                moveUp(dt);
            }
        }
        if (isAI){
            rv = AIController.randomMultiplier;
        } else {
            rv = 1;
        }
    }

    public void moveUp(double dt) {
        if (rect.y - Constants.PADDLE_SPEED * dt > Constants.TOOLBAR_HEIGHT)
            this.rect.y -= (Constants.PADDLE_SPEED * rv) * dt;
    }

    public void moveDown(double dt) {
        if ((rect.y + Constants.PADDLE_SPEED * dt) + rect.height < Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM)
            this.rect.y += (Constants.PADDLE_SPEED * rv) * dt;
    }
}