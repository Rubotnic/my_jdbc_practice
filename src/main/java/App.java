
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;


public class App {
    public static void main(String[] args) {


        HashSet<String> customer = new LinkedHashSet<>();

        customer.add("Kalle");
        customer.add("Stefan");
        customer.add("Marre");

        Iterator<String> iter = customer.iterator();
        while (iter.hasNext()){
            System.out.println(iter.hasNext()+ "");


        }
    }
}