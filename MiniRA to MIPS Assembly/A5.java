import syntaxtree.*;
import visitor.*;

public class A5 {
    public static void main(String [] args) {
        try {
            Node root = new MiniRAParser(System.in).Goal();
            root.accept(new Pass(), null);
        }
        catch (ParseException e) {
            System.out.println(e.toString());
        }
    }
} 