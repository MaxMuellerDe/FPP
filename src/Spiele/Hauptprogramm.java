package Spiele;
import java.util.Scanner;
public class Hauptprogramm {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        VierGewinnt vierGewinntSpiel = new VierGewinnt();
        Chomp chompSpiel = new Chomp();


        System.out.println("Welches Spiel moechtest du spielen?");
        System.out.println("Vier Gewinnt: [1], Chomp: [0]");
        System.out.println("Spiel beenden: [beliebige Taste]");
        int playgame = scan.nextInt();
        if(playgame==1){ //VierGewinnt
            vierGewinntSpiel.start(); //das gleiche für chomp, und fehlerabfragung.
        }
        else if(playgame==0){ //Chomp
            chompSpiel.start();
        }
        else {
            System.exit(0);
        }
        //System.out.println(Protokollierbar.protokoll);

    }//end main
}//end class
