package umleditor.utils;

public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second){
        super();
        this.first = first;
        this.second = second;
    }

    public A getFirst(){
        return first;
    }

    public B getSecond(){
        return second;
    }

    public String toString(){
        return "(" + first + ", " + second + ")";
    }
}
