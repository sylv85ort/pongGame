public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;
    public Text leftScoreText, rightScoreText;
    public static int AIScore;
    public static int plScore;

    private double vy = 10.0;
    private double vx = -100.0;
    public static boolean isWinner;

    // velocity x, y

//Constructor
    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle, Text leftScoreText, Text rightScoreText) {
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.leftScoreText = leftScoreText;
        this.rightScoreText = rightScoreText;
    }

    public double calculateNewVelocityAngle(Rect paddle){
        double relativeIntersectY = (paddle.y + (paddle.height / 2.0)) - (this.rect.y + (this.rect.height/ 2.0));
        double normalIntersectY = relativeIntersectY / (paddle.height / 2.0);
        double theta = normalIntersectY * Constants.MAX_ANGLE;

        return Math.toRadians(theta);
    }


    public void update(double dt) {
        //Checks for collision on top and bottom of screens, bounces ball back
        if (vy >= 0.0) {
            if (this.rect.y + (vy * dt) + this.rect.height > Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM) {
                vy *= -1.0;
            }
        } else if (vy < 0.0) {
            if (this.rect.y + (vy * dt) < Constants.TOOLBAR_HEIGHT) {
                vy *= -1.0;
            }
        }
        //If the left side of our ball is less than right side of our paddle & the right side of our ball plus the width is greater than the left side of our paddle
        // & the bottom of our ball is greater than the top of our paddle (Vice versa)
        //If all true, we want to flip the movement of the ball.
        if (vx < 0.0) {
            if (this.rect.x + (vx * dt) < leftPaddle.x + leftPaddle.width && this.rect.x >= this.leftPaddle.x) {
                if (this.rect.y + (vy * dt) > leftPaddle.y &&
                        this.rect.y + (vy * dt) + this.rect.height < leftPaddle.y + leftPaddle.height + 20) {
                    double theta = calculateNewVelocityAngle(leftPaddle);
                    double newVx = Math.abs((Math.cos(theta))  * Constants.BALL_SPEED);
                    double newVy = (-Math.sin(theta)) * Constants.BALL_SPEED;

                    double oldSign = Math.signum(vx);
                    this.vx = newVx * (-1.0 * oldSign);
                    this.vy = newVy;
                }
            }
            ////If we are to right of  paddle && we are within y range of paddle
        } else if (vx >= 0.0) {
            if (this.rect.x + (vx * dt) + this.rect.width > rightPaddle.x) {
                if (this.rect.y + (vy * dt) > rightPaddle.y &&
                        this.rect.y + (vy * dt) + this.rect.height < rightPaddle.y + rightPaddle.height) {
                    double theta = calculateNewVelocityAngle(rightPaddle);
                    double newVx = Math.abs(Math.cos(theta)) * Constants.BALL_SPEED;
                    double newVy = (-Math.sin(theta)) * Constants.BALL_SPEED;

                    double oldSign = Math.signum(vx);
                    this.vx = newVx * (-1.0 * oldSign);
                    this.vy = newVy;
                }
            }
        }
        //position = position + velocity.
        //velocity = velocity + acceleration
        this.rect.x += vx * dt;
        this.rect.y += vy * dt;

        //Score checker
        //Checks if pong ball has reached goal (if = Ai score, else if = Player score)

        if (this.rect.x + this.rect.width < leftPaddle.x) {
            int rightScore = Integer.parseInt(rightScoreText.text);
            rightScore++;
            rightScoreText.text = "" + rightScore;
            this.rect.x = Constants.SCREEN_WIDTH / 2.0;
            this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
            this.vx = -150.0;
            this.vy = 10.0;
            rightScore = AIScore;
            if (rightScore >= Constants.WIN_SCORE) {
                isWinner = false;
                Main.changeState(4);
            }
        } else if (this.rect.x > rightPaddle.x + rightPaddle.width) {
            int leftScore = Integer.parseInt(leftScoreText.text);
            leftScore++;
            leftScoreText.text = "" + leftScore;
            this.rect.x = Constants.SCREEN_WIDTH / 2.0;
            this.rect.y = Constants.SCREEN_HEIGHT / 2.0;
            this.vx = 150.0;
            this.vy = 10.0;
            plScore = leftScore;
            System.out.println("the pl score is " + plScore);
            if (leftScore >= Constants.WIN_SCORE) {
                isWinner = true;
                Main.changeState(4);
            }
        }
    }
}
