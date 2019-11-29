import java.util.Stack;
public interface Protokollierbar<T> {


    Stack<Object> protokoll = new Stack<Object>();

    //abstrakte Methoden
    void addSpielzug();
    void removeSpielzug();



}//end interface

