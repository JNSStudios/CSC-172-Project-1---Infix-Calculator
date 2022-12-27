public class URStack<E> {
    
    private URNode<E> bottom = new URNode<E>(null, null,null);
    private URNode<E> top = new URNode<E>(null, bottom,null);

    URStack(){
        bottom = new URNode<E>(null, null,top);
        top = new URNode<E>(null, bottom,null);
    }

    public void push(E obj){
        URNode<E> newNode = null;
        if(top.prev() == bottom){
            // System.out.println("ADDING FIRST ELEMENT");
            newNode = new URNode<E>(obj, bottom, top);
            bottom.setNext(newNode);
        } else {
            newNode = new URNode<E>(obj, top.prev(), top);
            top.prev().setNext(newNode);
        }
        top.setPrev(newNode);
    }

    public E peek(){
        if(top.prev() == bottom)
            return null;
        else 
            return top.prev().element();
    }

    public E pop(){
        if(top.prev() == bottom)
            return null;
        else{
            E elem = top.prev().element();
            top.prev().prev().setNext(top);
            top.setPrev(top.prev().prev());
            return elem;
        }
        
        
        // if(top.prev() == bottom && top.element() == null && bottom.element() == null){
        //     return null;
        // } else if(top.prev() == bottom && top.element() == null){
        //     E botEl = bottom.element();
        //     bottom.setElement(null);
        //     return botEl;
        // } else if(top.prev() == bottom){
        //     E topEl = top.element();
        //     top.setElement(null);
        //     return topEl;
        // } else {
        //     E el = top.element();
        //     top = top.prev();
        //     top.setNext(null);
        //     return el;
        // }
    }

    public void clear(){
        bottom = new URNode<E>(null, null,null);
        top = new URNode<E>(null, bottom,null);
        bottom = new URNode<E>(null, null,top);
    }

    public boolean isEmpty(){
        return bottom.next().element() == null;
    }

    @Override
    public String toString(){
        URNode<E> current = bottom.next();
        // System.out.println(current.element() != null);
        int count = 0;
        while(current != null){
            // System.out.println("Current != null in count phase ((" + current.element());
            count++;
            current = current.next();
        }
        String arr = "[";
        current = bottom.next();
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
