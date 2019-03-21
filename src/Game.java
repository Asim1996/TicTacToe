import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final int[][] board;
    private final int n;//size of board
    //showBoard and showInstruction function are defined for board of size 3
    //can be modified accordingly
    private final int[] rowSum;
    private final int[] colSum;
    private int diagSum;
    private int revDiagSum;
    private int winner;
    private boolean gameOver=false;

    private static final int Computer = 0;
    private static final int Human = 1;

    //Using -1 and 1 instead of 'O' and 'X'
    private static final int ComputerMove=-1;
    private static final int HumanMove=1;

    //Initialization
    public Game(final int n) {
        this.n = n;
        board = new int[n][n];
        this.rowSum = new int[n];
        this.colSum = new int[n];
    }

    // A function to show the current board status
    public void showBoard() {
        System.out.printf("\n");

        System.out.printf("\t\t\t %d | %d | %d \n", board[0][0],
                board[0][1], board[0][2]);
        System.out.printf("\t\t\t--------------\n");
        System.out.printf("\t\t\t %d | %d | %d \n", board[1][0],
                board[1][1], board[1][2]);
        System.out.printf("\t\t\t--------------\n");
        System.out.printf("\t\t\t %d | %d | %d \n\n",board[2][0],
                board[2][1], board[2][2]);

    }

    // A function to show the instructions
    public void showInstructions() {
        System.out.print("\t\t\t Tic-Tac-Toe (Human v/s Computer)\n\n");
        System.out.print("Choose a cell numbered from 1 to 9 as below and play\n\n");

        System.out.print("\t\t\t 1 | 2 | 3 \n");
        System.out.print("\t\t\t--------------\n");
        System.out.print("\t\t\t 4 | 5 | 6 \n");
        System.out.print("\t\t\t--------------\n");
        System.out.print("\t\t\t 7 | 8 | 9 \n\n");

        System.out.print("-\t-\t-\t-\t-\t-\t-\t-\t-\t-\n\n");

    }
    /**
     For a 2d board char array spaces can be used for initialization
     Used an Integer array so default is 0
        */
//  ------  A function to initialise the game  -------
//    public void initialise()
//    {
//        // Initially the board is empty
//        for (int i=0; i<n; i++)
//        {
//            for (int j=0; j<n; j++)
//                board[i][j] = 0;
//        }
//         return;
//    }

    /**
     * Player {player} makes a move at ({row}, {col}).
     *
     * @param row    The row of the board.
     * @param col    The column of the board.
     * @param player The player, can be either 0 or 1.
     * @return The current winning condition, can be either:
     * 0: No one wins.
     * +1: Human wins.
     * -1: Computer wins.
     * @throws IllegalArgumentException if the move is an illegal move
     Gives the winner in O(1) times
     Precomputes scores for every row,column and diagonal
     and onces it hits board size
     The respective player wins
     */
    public int move(int row, int col, int player) throws IllegalArgumentException {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IllegalArgumentException("Move out of board boundary");
        }
        if (board[row][col] != 0) {
            throw new IllegalArgumentException("Square is already occupied");
        }
        if (player != 0 && player != 1) {
            throw new IllegalArgumentException("Invalid Player!");
        }
        if (getWinner() != 0) {
            throw new IllegalArgumentException("Board is decided!");
        }
        player = player == 0 ? -1 : +1;
        board[row][col] = player;
        rowSum[row] += player;
        colSum[col] += player;

        if (row == col) {
            diagSum += player;
        }
        if (row == n - 1 - col) {
            revDiagSum += player;
        }

        if (Math.abs(rowSum[row]) == n
                || Math.abs(colSum[col]) == n
                || Math.abs(diagSum) == n
                || Math.abs(revDiagSum) == n) {
            winner = player;
            gameOver=true;
        }

        return getWinner();
    }

    public int getWinner() {
        return winner;
    }

    // A function to play Tic-Tac-Toe
    public void playTicTacToe(int whoseTurn) {
//        initialise();
        showInstructions();
        int moveIndex = 0, x, y;
        int min=1;
        int max=(n*n);
        Random randomno = new Random();
        //Used a HashSet to avoid exceptions
        HashSet<Integer> set=new HashSet<>();
        Scanner scan=new Scanner(System.in);
        while (gameOver == false && moveIndex != n*n)
        {
            if (whoseTurn == Computer)
            {
                System.out.println("Computer Chance: ");
                //Choose a random number between 1 and n
                int computerInput=randomno.nextInt(max - min + 1) + min;
                if(!set.contains(computerInput)) {
                    //Resolving that number as row and column
                    x = (computerInput-1) / n;
                    y = (computerInput-1) % n;
                    move(x,y,Computer);
                    set.add(computerInput);
                    System.out.printf("Computer has put a %d in cell %d\n",ComputerMove,computerInput);
                    showBoard();
                }else{
                    System.out.println("Square can not be selected. Retrying");
                    continue;
                }
                moveIndex ++;
                whoseTurn = Human;
            } else if (whoseTurn == Human)
            {
                System.out.println("Your Chance: ");
                //Type a number between 1 and max
                int humanInput=scan.nextInt();
                if(humanInput<min || humanInput>max ){
                    System.out.println("Invalid input! Re-enter in correct range");
                    continue;
                }
                if(!set.contains(humanInput)) {
                    //Resolving that number as row and column
                    x = (humanInput-1) / n;
                    y = (humanInput-1) % n;
                    move(x,y,Human);
                    set.add(humanInput);
                    System.out.printf("Human has put a %d in cell %d\n",HumanMove,humanInput);
                    showBoard();
                }else{
                    System.out.println("Square can not be selected. Enter a different value");
                    continue;
                }
                moveIndex ++;
                whoseTurn = Computer;
            }
            }
        // If the game has drawn
        if (gameOver == false && moveIndex == n * n)
            System.out.println("It's a draw");
        else
        {
            // Printing the winner
            if (winner == -1)
                System.out.println("Computer wins");
            else if (winner == 1)
                System.out.println("Human wins");
        }
        return;

    }
}
