import java.util.Arrays;
import java.util.Collection;

public class URArrayList<E> {
    
    private Object[] arr;
    private Object[] newArr;
    private int alloc;
    private int tail;


    URArrayList(int l, int a){
        this.alloc = a;
        this.arr = new Object[l];
        this.newArr = null;
        this.tail = 0;
    }
    URArrayList(int l){
        this.alloc = l;
        this.arr = new Object[l];
        this.newArr = null;
        this.tail = 0;
    }

    public void realloc(){
        realloc(alloc);
    }

    public void realloc(int reall){
        int newLength = this.arr.length + reall;
        // System.out.println("NEW LENGTH: " + newLength);
        this.newArr = new Object[newLength];
        // System.out.println("NEWARR INIT: " + toArrString(this.newArr));
        for(int i = 0; i < this.arr.length; i++){
            this.newArr[i] = this.arr[i];
        }
        // System.out.println("NEWARR: " + toArrString(this.newArr));
        // System.out.println("OLDLEN: " + this.arr.length + "\nNEWLEN: " + this.newArr.length);
        this.arr = new Object[newLength];
        // System.out.println(this.arr.length + "  " + toArrString(this.arr));
        for(int i = 0; i < this.newArr.length; i++){
            this.arr[i] = this.newArr[i];
            // System.out.println(toArrString(this.arr));
        }
    }

    public void append(Object o){
        if(tail == arr.length-1)
            realloc();
        if(tail == 0 && arr[tail] == null) tail--;
        // System.out.println("ITEM APPENDED: " + o);
        arr[++tail] = o;
        // System.out.println(toArrString(arr));
    }

    public void insert(Object o, int ind){
        if(tail+1 >= arr.length){
            // System.out.println("REALLOCATING FOR ARRAY!");
            realloc();
        }
        // System.out.println("B4: " + toArrString(arr));
        for(int i = tail+1; i >= ind; i--){
            arr[i+1] = arr[i];
            // System.out.println(i + ": " + toArrString(arr));
        }
            
        arr[ind] = o; 
        tail++;
        // System.out.println("AFTER:    " + toArrString(arr));
    }

    public Object remove(int rmInd){
        Object rm = arr[rmInd];
        for(int i = rmInd; i < arr.length; i++){
            Object val;
            if(i+1 >= arr.length)
                val = null;
            else 
                val = arr[i+1];
            arr[i] = val;
        }
        tail--;
        return rm;
    }

    public Object remove(Object rmObj){
        boolean found = false;
        int rmInd = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == rmObj){
                found = true;
                rmInd = i;
            }
        }
        if(!found)
            return null;
        return remove(rmInd);
    }

    public Object get(int i){
        if(i >= arr.length)
            return null;
        else
            return arr[i];
        
    }

    public boolean set(int i, Object o){
        if(i < arr.length){
            arr[i] = o;
            return true;
        } else
            return false;
    }

    public int length(){
        return tail+1;
    }

    public E[] toArray(){
        Object[] retArr = new Object[tail+1];
        for(int i = 0; i < retArr.length; i++){
            retArr[i] = arr[i];
        }
            
        return (E[]) retArr;
    }

    public String toArrString(Object[] array){
        String strarr = "[";
        for(int i = 0; i <= tail; i++){
            if(array[i]==null)
                strarr += "null, ";
            else
                strarr += array[i].toString() + ", ";

        }
        if(strarr.length() >= 2)
            strarr = strarr.substring(0, strarr.length()-2);
        strarr += "]";
        return strarr;
    }
}
