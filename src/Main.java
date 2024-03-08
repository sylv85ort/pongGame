//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static int state = 0;
    public static Thread mainThread;
    public static MainMenu menu;
    public static PlayerSelect ps;
    public static Window window;
    public static WinScreen ws;

    public static void main(String[] args) {
        menu = new MainMenu();
        mainThread = new Thread(menu);
        mainThread.start();

    }

    //State 0 = Main Menu
    //State 1 = Game
    //State 2 = Player Select
    //State 3 = Quit Close Game
    //State 4 = Win Game
    public static void changeState(int newState){
        if (newState == 2 && state == 0) {
            System.out.println("player select");
            menu.stop();
            ps = new PlayerSelect();
            mainThread = new Thread(ps);
            mainThread.start();
        } else if (newState == 1 && state == 2) {
            System.out.println("Game start");
            ps.stop();
            window = new Window();
            mainThread = new Thread(window);
            mainThread.start();
        } else if (newState == 0 && state == 1) {
                window.stop();
                menu = new MainMenu();
                mainThread = new Thread(menu);
                mainThread.start();
        } else if (newState == 3) {
            if (window != null)
                window.stop();
            if (menu != null)
                menu.stop();
        } else if (newState == 4) {
            window.stop();
            ws = new WinScreen();
            mainThread = new Thread(ws);
            mainThread.start();
        }

        state = newState;

    }
}
