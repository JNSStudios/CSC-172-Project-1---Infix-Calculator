public class URQueue<E> {

    private URNode<E> front = new URNode<E>(null, null, null);
    private URNode<E> end = new URNode<E>(null, front, null);

    URQueue(){
        front = new URNode<E>(null, null, end);
        end = new URNode<E>(null, front, null);
    }

    public void enqueue(E obj){
        URNode<E> newNode = new URNode<E>(obj, end.prev(), end);
        if(end.prev().equals(front))
            front.setNext(newNode);
        else
            end.prev().setNext(newNode);
        end.setPrev(newNode);
    }

    public E dequeue(){
        E obj = front.next().element();
        // System.out.println(obj);
        front.setNext(front.next().next());
        return obj;
    }

    public E peek(){
        return front.next().element();
    }

    public boolean isEmpty(){
        return front.next() == end;
    }

    public void clear(){
        front = new URNode<E>(null, null, end);
        end = new URNode<E>(null, front, null);
    }

    public int getLength(){
        URNode<E> current = front.next();
        int len = 0;
        while(current.next() != null){
            len++;
            current = current.next();
        }
        return len;
    }

    @Override
    public String toString(){
        URNode<E> current = front.next();
        // System.out.println(current.element() != null);
        int count = 0;
        while(current != null){
            // System.out.println("Current != null in count phase ((" + current.element());
            count++;
            current = current.next();
        }
        String arr = "[";
        current = front.next();
        for(int i = 0; i <= count; i++){
            if(current != null){
                // System.out.println("Current != null ((" + current.element());
                if(current.element() != null){
                    // System.out.println("Current element != null, appending... ((" + current.element());
                    arr += current.element() + ", ";
                }
                    
                current = current.next();
            } else break;
        }
        if(arr.length() >= 2)
            arr = arr.substring(0, arr.length()-2);
        arr += "]";
        return arr;
    }

}