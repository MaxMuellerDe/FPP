import java.util.Scanner;
public class Hauptprogramm {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);

        VierGewinnt vierGewinntSpiel = new VierGewinnt();

        System.out.println("Welches Spiel moechtest du spielen?");
        System.out.println("Vier Gewinnt: [1], Chomp: [0]");
        System.out.println("Spiel beenden: [beliebige Taste");
        int playgame = scan.nextInt();
        if(playgame==1){
            vierGewinntSpiel.start(); //das gleiche für chomp, und fehlerabfragung.
        }



        System.out.println("Willkommen bei 4 Gewinnt!");
        System.out.println();
        System.out.println("Spiel beginnen: [1]");
        System.out.println("Spiel beenden:  [beliebige Eingabe]");


    }//end main
}//end class
