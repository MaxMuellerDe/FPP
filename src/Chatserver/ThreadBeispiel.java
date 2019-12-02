package Chatserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Beispiel fuer Threads
//nicht relevant fuer Server




public class ThreadBeispiel {

    static class Zaehlen implements Runnable{

        @Override
        public void run() {

            for(int i=0; i<20; i++){
                System.out.println(Thread.currentThread().getName() + " zählt: " + i); //getName sinnlos durch Executor

                //Exception Handling
                try {

                    Thread.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        //Threads mit Executor Pool zusammengefasst - sinnvoll wenn man viele Threads hat



//        Thread thread = new Thread(new Zaehlen()); //Langform
//        thread.start();
//        new Thread(new Zaehlen()).start(); //Kurzform

        System.out.println("--------------Start--------------");

        ExecutorService executor = Executors.newFixedThreadPool(2); //Anzahl an Threads - koennen wir in diesem Pool parallel laufen lassen

        Thread th1 = new Thread(new Zaehlen());
        Thread th2 = new Thread(new Zaehlen());

        //Threads einen Namen zuweisen
        th1.setName("Erster Thread");
        th2.setName("Zweiter Thread");

        //Threads starten/ Executor hinzufuegen
//        th1.start();
//        th2.start(); //vorher so, nach Executor nicht mehr
        executor.execute(th1);
        executor.execute(th2);
        executor.shutdown();//Pool abschliessen; da wir keine weiteren Threads hinzufuegen wollen


        //auf Threads warten
        try {

//            th1.join();//wartet bis der Thread tot ist - somit wird Main angehalten, bis Thread 1 fertig ist
//            th2.join();//soll auf beide Threads warten, nicht nur bis der erste fertig ist
            executor.awaitTermination(1, TimeUnit.SECONDS); //schaut jede Sekunde, ob Executor noch laeuft - alle gleichzeitig ueberprueft

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Threads werden hier nacheinander ausgeführt, wir wollen aber gleichzeitig und Meldung bekommen, wenn alle fertig sind
        //Stichwort synchronized

        System.out.println("--------------Ende--------------");


        //Funfact: Hier funktionierts parallel - warum?
//        Thread thread = new Thread(new Zaehlen());
//        thread.start();
//        new Thread(new Zaehlen()).start();
    }
}
