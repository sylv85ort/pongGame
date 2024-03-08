public class Time {
    //Converts nanoSeconds into seconds for FPS Count
    public static double timeStarted = System.nanoTime();

    public static double getTime() { return(System.nanoTime() - timeStarted) * 1E-9;}
}
