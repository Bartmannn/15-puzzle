import java.util.Scanner;

class Main {

    public static void main(String[] args) {

        // int choice;
        // Scanner scan = new Scanner(System.in);
        // do {
        //     System.out.println("1 - easy | 2 - medium | 3 - hard | 4 - impossible");
        //     System.out.print("Choose dificulty: ");
        //     choice = scan.nextInt();
        // } while (choice < 1 || choice > 4);


        Game game = new Game(4);
        game.start();

        // scan.close();

    }

}
