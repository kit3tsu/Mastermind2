package be.bruFormation.exo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Character> SOLUCE = new ArrayList<>();
    private static String hint = "";
    private static final char [][] BOARD = new char[10][4];
    private static final ArrayList<Character> COLORS = new ArrayList<>(Arrays.asList('W', 'U', 'B', 'R', 'G')); //,'P','O','J'
    private static boolean solutionFound = false;
    private static final Scanner INPUT = new Scanner(System.in);
    public static void main(String[] args) {
        start();
        int attemptTried = 0;
        while(!solutionFound && attemptTried< BOARD.length){
            play(attemptTried);
            attemptTried ++;
        }
        displayEndGame();
    }
    private static void start(){
        setSoluceTab();
        displayRules();
    }

    private static void setSoluceTab() {
        char color;
        for (int i = 0; i < BOARD[0].length; i++) {
            color = COLORS.get((int)Math.floor(Math.random() * COLORS.size()));
            SOLUCE.add(i, color);
        }
    }
    private static void play(int cpt) {
        String trial;
        askForAnAttempt();
        trial = INPUT.next();
        fillGrid(cpt,trial);
        checkSoluce(trial);
        displayBoard();
        displayHint();
    }

    private static void checkSoluce(String trial) {
        char[] attempt = trial.toCharArray();
        int tmp = 0;
        int cptGood=0;
        ArrayList<Integer> positionFound = new ArrayList<>();
        ArrayList<Character> colorTried = new ArrayList<>();
        for (char cell: attempt) {
            if(!colorTried.contains(cell)){
            colorTried.add(cell);
            }
            if (SOLUCE.get(tmp) == cell) {
                positionFound.add(tmp);
                cptGood++;
            }
            tmp++;
        }
        int missPosition = getMissPosition(tmp, positionFound, colorTried);
        solutionFound = cptGood == BOARD[0].length;
        hint = String.format("Il y a %d couleurs a la bonne place et %d couleur a la mauvaise place et %d mauvaise couleurs",
                cptGood,missPosition,(4-cptGood)-missPosition);
    }

    private static int getMissPosition(int tmp, ArrayList<Integer> positionFound, ArrayList<Character> colorTried) {
        int missPosition= 0;
        for (char color: colorTried) {
            if (SOLUCE.contains(color)) {
                for (int i = 0; i < tmp; i++) {
                    if(!positionFound.contains(i) && SOLUCE.get(i) == color){
                        missPosition++;
                    }
                }
            }
        }
        return missPosition;
    }

    private static void fillGrid(int cpt,String trial) {
        BOARD[cpt] = trial.toCharArray();
    }
    private static void askForAnAttempt() {
        System.out.println("Witch combinaison of color will you try");
    }
    private static void displayRules() {
        System.out.println("""
                You will be given 10 attempt to find a combinaison of 4 colors.
                There are 8 differents color Whithe(W) Blue(U) Black(B) Red(R) Green(G) Purple(P) Orange(O) Jeaune(J).
                The same color can't appear multiple times.
                You will get some hint after each attempt
                V if the color is place where it should be
                O if the color is present in the solution but not in this position
                X if the color is not in the solution
                When you make an attempt you just have to whirte the for letter
                For exemple : URBR""");
    }
    private static void displayEndGame() {
        if(solutionFound){
            System.out.println("Good job");
        }else{
            System.out.println("the solution was" + SOLUCE);
        }
    }
    private static void displayBoard() {
        for (char[] chars : BOARD) {
            for (int c = 0; c < BOARD[0].length; c++) {
                System.out.printf("'%c'", chars[c]);
            }
            System.out.print("\n");
        }
    }
    private static void displayHint() {
        System.out.println(hint);
    }
}