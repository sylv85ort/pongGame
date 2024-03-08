import java.util.Random;

public class AIController {
    public PlayerController playerController;
    public Rect ball;
    public static double minRange = 0.2;
    public static double maxRange = 1.0;
    public  static final double RANGE_SHRINK_FACTOR_MIN = 1.5;
    public  static final double RANGE_SHRINK_FACTOR_MAX = 1.05;
    public static double randomMultiplier;
    //private boolean rangeAdjusted = false;
    private int previousPlayerScore = 0;

    public AIController(PlayerController plrController, Rect ball){
        this.playerController = plrController;
        this.ball = ball;
    }

    private double getRandomMultiplier() {
        // Generate a random multiplier within the specified range
        return minRange + (maxRange - minRange) * new Random().nextDouble();
    }

    private void adjustRange(int playerScore) {
        // Check if the player's score is even, greater than 0, and different from the previous score
        if ((playerScore % 2 == 0) && (playerScore > 0) && (playerScore != previousPlayerScore)) {
            // Update the previous score to prevent continuous adjustments
            previousPlayerScore = playerScore;

            // Decrease the range by multiplying both minRange and maxRange
            minRange *= RANGE_SHRINK_FACTOR_MIN;
            maxRange *= RANGE_SHRINK_FACTOR_MAX;

            System.out.println("The Min Range: " + minRange + " The Max Range: " + maxRange);
        }
    }

    public void update(int playerScore, double dt){
        adjustRange(playerScore);
        randomMultiplier = getRandomMultiplier();
        playerController.isAI = true;
        playerController.update(dt);

        if (ball.y < playerController.rect.y){
            playerController.moveUp(randomMultiplier * dt);
        } else if (ball.y + ball.height > playerController.rect.y + playerController.rect.height){
            playerController.moveDown(randomMultiplier * dt);
        }
    }
}
