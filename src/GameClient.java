import java.util.Random;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter the size of board: ");
        int size=scan.nextInt();//Size of board
        Game game=new Game(size);
        int Computer = 0;
        int Human = 1;
        Random randomno = new Random();
        // get a random boolean value
        boolean value = randomno.nextBoolean();
        //Decides whom will make the first move
        int first = value ? Computer : Human;
        game.playTicTacToe(first);
    }
}
