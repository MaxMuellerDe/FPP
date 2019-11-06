//generischer Stack
class Stack<T> { //damit nur Zahlen sind
    private class Node<T>{ //T als Typ wird speater zugewiesen
        Node<T> next;
        T value;
        Node(T value){
            this.value = value;
        }
    }//end Node
    Node<T> top; //Top of Stack; Stackpointer
    public void push(T getSignatur){
        Node<T> n = new Node<T>(getSignatur);
        n.next = this.top;
        this.top = n;
    }
    public T pop(){
        T out = top.value;
        top = top.next;
        return out;
    }

}//end Stack